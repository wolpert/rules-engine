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

public abstract class TestObjectSetterExtension implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, ParameterResolver {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestObjectSetterExtension.class);
  private static final ExtensionContext.Namespace namespace = ExtensionContext.Namespace.create(TestObjectSetterExtension.class);

  protected ExtensionContext.Namespace namespace() {
    return namespace;
  }

  protected abstract void installInstances(ClassInstanceManager instanceManager);

  private ClassInstanceManager classInstanceManager(final ExtensionContext context) {
    return context.getStore(namespace())
        .getOrComputeIfAbsent(ClassInstanceManager.class,
            k -> new ClassInstanceManager(),
            ClassInstanceManager.class);
  }

  @Override
  public void beforeAll(final ExtensionContext context) throws Exception {
    LOGGER.info("Setting in memory testobject setter instance");
    final ClassInstanceManager classInstanceManager = classInstanceManager(context);
    installInstances(classInstanceManager);
  }

  @Override
  public void afterAll(final ExtensionContext context) {
    LOGGER.info("Tearing down in memory testobject setter instance");
    final ClassInstanceManager classInstanceManager = context.getStore(namespace()).remove(ClassInstanceManager.class, ClassInstanceManager.class);
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
          .filter(f -> f.isAnnotationPresent(TestObject.class))
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
    return parameterContext.isAnnotated(TestObject.class) && classInstanceManager.hasInstance(type);
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
        .orElseThrow(() -> new IllegalArgumentException("Unable to find aws testobject setter extension value of type " + field.getType())); // Check the store to see we have this type.
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