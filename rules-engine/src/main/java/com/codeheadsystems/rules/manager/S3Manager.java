package com.codeheadsystems.rules.manager;

import java.io.InputStream;
import javax.inject.Inject;
import javax.inject.Singleton;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Singleton
public class S3Manager {

  private final S3Client s3Client;

  @Inject
  public S3Manager(final S3Client s3Client) {
    this.s3Client = s3Client;
  }

  public InputStream getInputStream(String bucket, String key) throws S3Exception {
    return s3Client.getObject(builder -> builder.bucket(bucket).key(key));
  }

}
