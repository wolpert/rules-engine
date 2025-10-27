package com.codeheadsystems.rules.module;

import com.codeheadsystems.rules.accessor.FileAccessor;
import com.codeheadsystems.rules.accessor.S3FileAccessor;
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

}
