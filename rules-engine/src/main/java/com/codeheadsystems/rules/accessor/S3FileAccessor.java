package com.codeheadsystems.rules.accessor;

import com.codeheadsystems.rules.RulesEngineConfiguration;
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

  private final S3Accessor s3Accessor;
  private final String prefix;
  private final String bucket;

  /**
   * Instantiates a new S 3 file accessor.
   *
   * @param s3Accessor    the s 3 manager
   * @param configuration the configuration
   */
  @Inject
  public S3FileAccessor(final S3Accessor s3Accessor,
                        final RulesEngineConfiguration configuration) {
    this.s3Accessor = s3Accessor;
    this.prefix = configuration.getS3RulePrefix().endsWith("/") ? configuration.getS3RulePrefix() : configuration.getS3RulePrefix() + "/";
    this.bucket = configuration.getAwsConfiguration().rulesS3Bucket();
    LOGGER.info("S3FileAccessor({}, {}, {})", s3Accessor, bucket, prefix);
  }

  @Override
  public List<String> listFiles(final String path) {
    return s3Accessor.getFiles(bucket, normalize(path));
  }

  @Override
  public Optional<InputStream> getFile(final String path) {
    try {
      final Optional<InputStream> inputStream = Optional.ofNullable(s3Accessor.getInputStream(bucket, normalize(path)));
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
