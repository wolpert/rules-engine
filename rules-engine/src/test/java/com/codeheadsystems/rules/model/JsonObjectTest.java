package com.codeheadsystems.rules.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.codeheadsystems.rules.test.FixtureHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonObjectTest {

  private JsonObject jsonObject;

  @BeforeEach
  void setUp() {
    jsonObject = FixtureHelper.jsonObject("fixture/jsonobject.json");
  }

  @Test
  void testeItExists() {
    assertThat(jsonObject).isNotNull();
  }

  @Test
  void testPath() {
    assertThat(jsonObject.asString("/table")).isEqualTo("stakes");
    assertThat(jsonObject.asString("/level1/something")).isEqualTo("else");
    assertThat(jsonObject.asInteger("/level1/first")).isEqualTo(1);
    assertThat(jsonObject.asString("/level1/first")).isNull();
    assertThat(jsonObject.asDouble("/level1/second")).isEqualTo(2.5d);

    assertThat(jsonObject.asBigInteger("/level1/bigInt")).isEqualTo(new java.math.BigInteger("1234567890123456789123"));
    assertThat(jsonObject.asBigDecimal("/level1/bigDec")).isEqualTo("123456784390.12346");
    assertThat(jsonObject.asBoolean("/level1/booleanTrue")).isEqualTo(true);
    assertThat(jsonObject.asBoolean("/level1/booleanFalse")).isEqualTo(false);
  }

  @Test
  void testArray() {
    assertThat(jsonObject.asArray("/level1/array")[0].asString("/itemKey")).isEqualTo("itemValue1");
    assertThat(jsonObject.asString("/level1/array/1/itemKey")).isEqualTo("itemValue2");
  }

}