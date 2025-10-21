package com.codeheadsystems.rules.accessor.impl;

import com.codeheadsystems.rules.RulesEngineConfiguration;
import com.codeheadsystems.rules.accessor.FileAccessor;
import com.codeheadsystems.rules.manager.S3Manager;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

/**
 * The type S 3 file accessor.
 */
@Singleton
public class S3FileAccessor implements FileAccessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(S3FileAccessor.class);

  private final S3Manager s3Manager;
  private final String prefix;
  private final String bucket;

  /**
   * Instantiates a new S 3 file accessor.
   *
   * @param s3Manager     the s 3 manager
   * @param configuration the configuration
   */
  @Inject
  public S3FileAccessor(final S3Manager s3Manager,
                        final RulesEngineConfiguration configuration) {
    this.s3Manager = s3Manager;
    this.prefix = configuration.getS3RulePrefix();
    this.bucket = configuration.getAwsConfiguration().rulesS3Bucket();
    LOGGER.info("S3FileAccessor({}, {}, {})", s3Manager, bucket, prefix);
  }

  @Override
  public List<String> listFiles(final String path) {
    return s3Manager.getFiles(bucket, normalize(path));
  }

  @Override
  public Optional<InputStream> getFile(final String path) {
    try {
      final Optional<InputStream> inputStream = Optional.ofNullable(s3Manager.getInputStream(bucket, normalize(path)));
      LOGGER.info("getFile({}) - found", path);
      return inputStream;
    } catch (final NoSuchKeyException e) {
      LOGGER.warn("getFile({}) - no such key.", path);
      return Optional.empty();
    }
  }

  private String normalize(final String path) {
    if (path.startsWith(prefix)) {
      return path;
    }
    return prefix + path;
  }
}
