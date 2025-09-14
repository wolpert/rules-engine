package com.codeheadsystems.basic.converter;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

import com.codeheadsystems.api.basic.v1.Key;
import com.codeheadsystems.basic.model.ImmutableRawKey;
import com.codeheadsystems.basic.model.RawKey;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KeyConverterTest {

  private static final byte[] KEY = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08};
  private static final UUID UUID = randomUUID();
  private static final String KEYSTRING = "0102030405060708";

  private KeyConverter keyConverter;

  @BeforeEach
  void setUp() {
    keyConverter = new KeyConverter();
  }

  @Test
  void from() {
    final RawKey rawKey = ImmutableRawKey.builder().uuid(UUID.toString()).key(KEYSTRING).build();
    final Key key = keyConverter.from(rawKey);
    assertThat(key)
        .isNotNull()
        .hasFieldOrPropertyWithValue("key", KEYSTRING);
  }

}