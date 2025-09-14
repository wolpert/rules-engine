package com.codeheadsystems.basic.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.codeheadsystems.api.basic.v1.Key;
import com.codeheadsystems.basic.converter.KeyConverter;
import com.codeheadsystems.basic.manager.KeyManager;
import com.codeheadsystems.basic.model.RawKey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KeysResourceTest {

  @Mock private KeyManager keyManager;
  @Mock private KeyConverter keyConverter;
  @Mock private RawKey rawKey;
  @Mock private Key key;

  @InjectMocks private KeysResource keysResource;

  @Test
  void read() {
    when(keyManager.getRawKey("UUID")).thenReturn(rawKey);
    when(keyConverter.from(rawKey)).thenReturn(key);
    assertThat(keysResource.read("UUID"))
        .isNotNull();
  }

}