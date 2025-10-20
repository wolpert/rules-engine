package com.codeheadsystems.rules.manager;

import static com.codeheadsystems.rules.test.TestHelper.INTEG;
import static org.assertj.core.api.Assertions.assertThat;

import com.codeheadsystems.rules.test.AWSLocalStackExtension;
import com.codeheadsystems.rules.test.AWSObject;
import com.codeheadsystems.rules.test.TestHelper;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Tag(INTEG)
@ExtendWith(AWSLocalStackExtension.class)
class S3ManagerIntegTest {

  @AWSObject private S3Client s3Client;
  private S3Manager s3Manager;

  private static final String BUCKET = "test-bucket";
  private static final String KEY = "dir/file.txt";
  private static final String CONTENT = "hello world";

  // Force Path Style from here for localstack: https://github.com/localstack/localstack/issues/8341
  @BeforeEach
  void setup() {
    s3Manager = new S3Manager(s3Client);

    s3Client.createBucket(CreateBucketRequest.builder().bucket(BUCKET).build());
    s3Client.putObject(
        PutObjectRequest.builder().bucket(BUCKET).key(KEY).build(),
        RequestBody.fromString(CONTENT));
  }

  @Test
  void listFiles_returnsKeys() {
    List<String> files = s3Manager.getFiles(BUCKET, "dir/");
    assertThat(files).contains(KEY);
  }

  @Test
  void getInputStream_readsContent() throws Exception {
    try (InputStream is = s3Manager.getInputStream(BUCKET, KEY)) {
      byte[] bytes = is.readAllBytes();
      assertThat(new String(bytes, StandardCharsets.UTF_8)).isEqualTo(CONTENT);
    }
  }
}
