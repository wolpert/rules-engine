package com.codeheadsystems.rules.accessor;

import java.io.InputStream;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;

/**
 * The type S 3 manager.
 */
@Singleton
public class S3Accessor {

  private final S3Client s3Client;

  /**
   * Instantiates a new S 3 manager.
   *
   * @param s3Client the s 3 client
   */
  @Inject
  public S3Accessor(final S3Client s3Client) {
    this.s3Client = s3Client;
  }

  /**
   * Gets input stream.
   *
   * @param bucket the bucket
   * @param key    the key
   * @return the input stream
   * @throws S3Exception the s 3 exception
   */
  public InputStream getInputStream(String bucket, String key) throws S3Exception {
    return s3Client.getObject(builder -> builder.bucket(bucket).key(key));
  }

  /**
   * Gets files.
   *
   * @param bucket the bucket
   * @param prefix the prefix
   * @return the files
   * @throws S3Exception the s 3 exception
   */
  public List<String> getFiles(String bucket, String prefix) throws S3Exception {
    return s3Client.listObjectsV2Paginator(builder -> builder.bucket(bucket).prefix(prefix))
        .contents()
        .stream()
        .map(S3Object::key)
        .toList();
  }

}
