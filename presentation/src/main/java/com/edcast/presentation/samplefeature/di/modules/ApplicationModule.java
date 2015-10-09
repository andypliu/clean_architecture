package com.edcast.presentation.samplefeature.di.modules;

import android.content.Context;
import com.edcast.data.cache.UserCache;
import com.edcast.data.cache.UserCacheImpl;
import com.edcast.data.cache.executor.JobExecutor;
import com.edcast.data.cache.repository.UserDataRepository;
import com.edcast.domain.executor.PostExecutionThread;
import com.edcast.domain.executor.ThreadExecutor;
import com.edcast.domain.samplefeature.repository.UserRepository;
import com.edcast.presentation.AndroidApplication;
import com.edcast.presentation.UIThread;
import com.edcast.presentation.samplefeature.navigation.Navigator;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
  private final AndroidApplication application;

  public ApplicationModule(AndroidApplication application) {
    this.application = application;
  }

  @Provides @Singleton Context provideApplicationContext() {
    return this.application;
  }

  @Provides @Singleton Navigator provideNavigator() {
    return new Navigator();
  }

  @Provides @Singleton ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides @Singleton PostExecutionThread providePostExecutionThread(UIThread uiThread) {
    return uiThread;
  }

  @Provides @Singleton UserCache provideUserCache(UserCacheImpl userCache) {
    return userCache;
  }

  @Provides @Singleton UserRepository provideUserRepository(UserDataRepository userDataRepository) {
    return userDataRepository;
  }
}
