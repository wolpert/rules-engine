package com.codeheadsystems.rules.module;

import com.codeheadsystems.rules.accessor.FileAccessor;
import com.codeheadsystems.rules.accessor.impl.S3FileAccessor;
import com.codeheadsystems.rules.dao.DynamoDbRulesDao;
import com.codeheadsystems.rules.dao.RulesDao;
import dagger.Binds;
import dagger.Module;

/**
 * This contains injected components that may need to be swapped out for testing purposes.
 */
@Module
public interface AwsServiceRuleBinder {

  @Binds
  FileAccessor fileAccessor(S3FileAccessor s3FileAccessor);

  @Binds
  RulesDao rulesDao(DynamoDbRulesDao dynamoDbRulesDao);

}
