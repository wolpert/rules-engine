package com.codeheadsystems.rules.converter;

import com.codeheadsystems.rules.model.TableDetails;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TenantConverter {

  private final TableDetails tableDetails;

  @Inject
  TenantConverter(final TableDetails tableDetails) {
    this.tableDetails = tableDetails;
  }

}
