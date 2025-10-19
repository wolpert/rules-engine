package com.codeheadsystems.rules.manager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@ExtendWith(MockitoExtension.class)
class S3ManagerTest {

  private static final String BUCKET = "bucket";
  private static final String KEY = "key";

  @Mock private S3Client s3Client;
  @Mock private ResponseInputStream<GetObjectRequest.Builder> responseInputStream;
  @Mock private S3Exception s3Exception;

  @SuppressWarnings("unchecked")
  @Test
  void getInputStream_success() {
    S3Manager s3Manager = new S3Manager(s3Client);

    when(s3Client.getObject(any(Consumer.class))).thenReturn(responseInputStream);

    InputStream result = s3Manager.getInputStream(BUCKET, KEY);

    assertThat(result).isNotNull().isSameAs(responseInputStream);
  }

  @SuppressWarnings("unchecked")
  @Test
  void getInputStream_failure() {
    S3Manager s3Manager = new S3Manager(s3Client);

    when(s3Client.getObject(any(Consumer.class))).thenThrow(s3Exception);

    assertThatThrownBy(() -> s3Manager.getInputStream(BUCKET, KEY))
        .isInstanceOf(S3Exception.class);
  }
}
