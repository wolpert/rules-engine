package com.codeheadsystems.rules.module;

import com.codeheadsystems.rules.accessor.FileAccessor;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class TestModule {

  private final FileAccessor fileAccessor;

  public TestModule(final FileAccessor fileAccessor) {
    this.fileAccessor = fileAccessor;
  }

  @Provides
  @Singleton
  public FileAccessor fileAccessor() {
    return fileAccessor;
  }

}
