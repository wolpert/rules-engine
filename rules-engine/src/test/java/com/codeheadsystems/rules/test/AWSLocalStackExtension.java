/*
 *    Copyright (c) 2022 Ned Wolpert <ned.wolpert@gmail.com>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.codeheadsystems.rules.test;

import java.lang.reflect.Field;
import java.util.Arrays;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * Setups the ddb instance.
 */
public class AWSLocalStackExtension implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, ParameterResolver {

  private static final Logger LOGGER = LoggerFactory.getLogger(AWSLocalStackExtension.class);
  private static final ExtensionContext.Namespace namespace = ExtensionContext.Namespace.create(AWSLocalStackExtension.class);

  private ClassInstanceManager classInstanceManager(final ExtensionContext context) {
    return context.getStore(namespace)
        .getOrComputeIfAbsent(ClassInstanceManager.class,
            k -> new ClassInstanceManager(),
            ClassInstanceManager.class);
  }

  @Override
  public void beforeAll(final ExtensionContext context) throws Exception {
    LOGGER.info("Setting in memory aws localstack instance");
    final ClassInstanceManager classInstanceManager = classInstanceManager(context);
    classInstanceManager.put(DynamoDbClient.class, TestHelper.dynamoDbClient());
    classInstanceManager.put(S3Client.class, TestHelper.s3Client());
  }

  @Override
  public void afterAll(final ExtensionContext context) {
    LOGGER.info("Tearing down in memory aws localstack instance");
    final ClassInstanceManager classInstanceManager = context.getStore(namespace).remove(ClassInstanceManager.class, ClassInstanceManager.class);
    if (classInstanceManager == null) {
      LOGGER.error("No class instance manager found");
      return;
    }
    classInstanceManager.clearAndClose();
  }

  @Override
  public void beforeEach(final ExtensionContext context) {
    final ClassInstanceManager classInstanceManager = classInstanceManager(context);
    context.getRequiredTestInstances().getAllInstances().forEach(instance -> {
      Arrays.stream(instance.getClass().getDeclaredFields())
          .filter(f -> f.isAnnotationPresent(AWSObject.class))
          .forEach(field -> {
            setValueForField(classInstanceManager, instance, field);
          });
    });
  }

  @Override
  public boolean supportsParameter(final ParameterContext parameterContext,
                                   final ExtensionContext extensionContext) throws ParameterResolutionException {
    final Class<?> type = parameterContext.getParameter().getType();
    final ClassInstanceManager classInstanceManager = classInstanceManager(extensionContext);
    return parameterContext.isAnnotated(AWSObject.class) && classInstanceManager.hasInstance(type);
  }

  @Override
  public Object resolveParameter(final ParameterContext parameterContext,
                                 final ExtensionContext extensionContext) throws ParameterResolutionException {
    final Class<?> type = parameterContext.getParameter().getType();
    final ClassInstanceManager classInstanceManager = classInstanceManager(extensionContext);
    return classInstanceManager.get(type).orElseGet(() -> {
      LOGGER.error("No instance found for type {}", type.getSimpleName());
      return null;
    });
  }

  private void setValueForField(final ClassInstanceManager classInstanceManager,
                                final Object o,
                                final Field field) {
    final Object value = classInstanceManager.get(field.getType())
        .orElseThrow(() -> new IllegalArgumentException("Unable to find aws localstack extension value of type " + field.getType())); // Check the store to see we have this type.
    LOGGER.info("Setting field {}:{}", field.getName(), field.getType().getSimpleName());
    enableSettingTheField(field);
    try {
      field.set(o, value);
    } catch (IllegalAccessException e) {
      LOGGER.error("Unable to set the field value for {}", field.getName(), e);
      LOGGER.error("Continuing, but expect nothing good will happen next.");
    }
  }

  /**
   * This allows us to set the field directly. It will fail if the security manager in play disallows it.
   * We can talk about justifications all we want, but really we know Java is not Smalltalk. Meta programming
   * is limited here. So... we try to do the right thing.
   *
   * @param field to change accessibility for.
   */
  protected void enableSettingTheField(final Field field) {
    try {
      field.setAccessible(true);
    } catch (RuntimeException re) {
      LOGGER.error("Unable to change accessibility for field due to private var or security manager: {}",
          field.getName());
      LOGGER.error("The setting will likely fail. Consider changing that type to protected.", re);
    }
  }
}
