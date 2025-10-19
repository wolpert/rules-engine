package com.codeheadsystems.rules.accessor.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.codeheadsystems.rules.RulesEngineConfiguration;
import com.codeheadsystems.rules.manager.S3Manager;
import com.codeheadsystems.rules.model.AwsConfiguration;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

// SLOP ALERT! Initially created with Github copilot, GPT-5.mini.
// It has been updated since then, but fair warning.
@ExtendWith(MockitoExtension.class)
class S3FileAccessorTest {

  private static final String BUCKET = "test-bucket";
  private static final String PREFIX = "rules/";
  private static final String PATH = "path/to/file.txt";

  @Mock private S3Manager s3Manager;
  @Mock private RulesEngineConfiguration configuration;
  @Mock private AwsConfiguration awsConfiguration;
  @Mock private InputStream stream;

  @Captor private ArgumentCaptor<String> pathCaptor;

  private S3FileAccessor accessor;

  @BeforeEach
  void createAccessor() {
    when(configuration.getS3RulePrefix()).thenReturn(PREFIX);
    when(configuration.getAwsConfiguration()).thenReturn(awsConfiguration);
    when(awsConfiguration.rulesS3Bucket()).thenReturn(BUCKET);
    accessor = new S3FileAccessor(s3Manager, configuration);
  }

  @Test
  void listFiles_withoutPrefix_addsPrefixAndDelegates() {
    List<String> expected = Arrays.asList("a", "b");
    String input = PATH;
    String expectedKey = PREFIX + input;

    when(s3Manager.getFiles(eq(BUCKET), eq(expectedKey))).thenReturn(expected);

    List<String> result = accessor.listFiles(input);

    assertThat(result).containsExactlyElementsOf(expected);
    verify(s3Manager).getFiles(eq(BUCKET), pathCaptor.capture());
    assertThat(pathCaptor.getValue()).isEqualTo(expectedKey);
  }

  @Test
  void listFiles_withPrefix_doesNotDoublePrefix() {
    List<String> expected = Collections.singletonList("only");
    String input = PREFIX + PATH;

    when(s3Manager.getFiles(eq(BUCKET), eq(input))).thenReturn(expected);

    List<String> result = accessor.listFiles(input);

    assertThat(result).containsExactlyElementsOf(expected);
    verify(s3Manager).getFiles(eq(BUCKET), pathCaptor.capture());
    assertThat(pathCaptor.getValue()).isEqualTo(input);
  }

  @Test
  void getFile_success_withoutPrefix_returnsStream() {
    String input = PATH;
    String expectedKey = PREFIX + input;

    when(s3Manager.getInputStream(eq(BUCKET), eq(expectedKey))).thenReturn(stream);

    Optional<InputStream> result = accessor.getFile(input);

    assertThat(result).isPresent();
    assertThat(result.get()).isSameAs(stream);
    verify(s3Manager).getInputStream(eq(BUCKET), pathCaptor.capture());
    assertThat(pathCaptor.getValue()).isEqualTo(expectedKey);
  }

  @Test
  void getFile_success_withPrefix_returnsStream() {
    String input = PREFIX + PATH;

    when(s3Manager.getInputStream(eq(BUCKET), eq(input))).thenReturn(stream);

    Optional<InputStream> result = accessor.getFile(input);

    assertThat(result).isPresent();
    assertThat(result.get()).isSameAs(stream);
    verify(s3Manager).getInputStream(eq(BUCKET), pathCaptor.capture());
    assertThat(pathCaptor.getValue()).isEqualTo(input);
  }

  @Test
  void getFile_noSuchKey_returnsEmptyOptional() {
    String input = PATH;
    String expectedKey = PREFIX + input;

    when(s3Manager.getInputStream(eq(BUCKET), eq(expectedKey)))
        .thenThrow(NoSuchKeyException.builder().message("not found").build());

    Optional<InputStream> result = accessor.getFile(input);

    assertThat(result).isEmpty();
    verify(s3Manager).getInputStream(eq(BUCKET), pathCaptor.capture());
    assertThat(pathCaptor.getValue()).isEqualTo(expectedKey);
  }

  @Test
  void getFile_otherException_propagates() {
    String input = PATH;
    String expectedKey = PREFIX + input;

    when(s3Manager.getInputStream(eq(BUCKET), eq(expectedKey)))
        .thenThrow(new RuntimeException("boom"));

    assertThatThrownBy(() -> accessor.getFile(input)).isInstanceOf(RuntimeException.class);
    verify(s3Manager).getInputStream(eq(BUCKET), pathCaptor.capture());
    assertThat(pathCaptor.getValue()).isEqualTo(expectedKey);
  }
}
