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

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * Setups the ddb instance.
 */
public class AWSLocalStackExtension extends TestInjectedSetterExtension {

  @Override
  protected void installInstances(final ClassInstanceManager instanceManager) {
    instanceManager.put(DynamoDbClient.class, TestHelper.dynamoDbClient());
    instanceManager.put(S3Client.class, TestHelper.s3Client());
  }

}
