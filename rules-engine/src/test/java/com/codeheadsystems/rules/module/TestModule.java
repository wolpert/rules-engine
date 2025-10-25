package com.codeheadsystems.rules.module;

import com.codeheadsystems.rules.accessor.FileAccessor;
import com.codeheadsystems.rules.dao.RulesDao;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class TestModule {

  private final FileAccessor fileAccessor;
  private final RulesDao rulesDao;

  public TestModule(final FileAccessor fileAccessor, final RulesDao rulesDao) {
    this.fileAccessor = fileAccessor;
    this.rulesDao = rulesDao;
  }

  @Provides
  @Singleton
  public FileAccessor fileAccessor() {
    return fileAccessor;
  }

  @Provides
  @Singleton
  public RulesDao rulesDao() {
    return rulesDao;
  }


}
