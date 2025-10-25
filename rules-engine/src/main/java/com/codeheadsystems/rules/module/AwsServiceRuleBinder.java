package com.codeheadsystems.rules.module;

import com.codeheadsystems.rules.accessor.FileAccessor;
import com.codeheadsystems.rules.accessor.S3FileAccessor;
import com.codeheadsystems.rules.dao.DynamoDbRulesDao;
import com.codeheadsystems.rules.dao.RulesDao;
import dagger.Binds;
import dagger.Module;

/**
 * This contains injected components that may need to be swapped out for testing purposes.
 */
@Module
public interface AwsServiceRuleBinder {

  /**
   * File accessor file accessor.
   *
   * @param s3FileAccessor the s 3 file accessor
   * @return the file accessor
   */
  @Binds
  FileAccessor fileAccessor(S3FileAccessor s3FileAccessor);

  /**
   * Rules dao rules dao.
   *
   * @param dynamoDbRulesDao the dynamo db rules dao
   * @return the rules dao
   */
  @Binds
  RulesDao rulesDao(DynamoDbRulesDao dynamoDbRulesDao);

}
