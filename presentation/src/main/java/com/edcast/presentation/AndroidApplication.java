package com.edcast.presentation;

import android.app.Application;
import com.edcast.presentation.samplefeature.di.components.ApplicationComponent;
import com.edcast.presentation.samplefeature.di.components.DaggerApplicationComponent;
import com.edcast.presentation.samplefeature.di.modules.ApplicationModule;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application {

  private ApplicationComponent applicationComponent;

  @Override public void onCreate() {
    super.onCreate();
    this.initializeInjector();
  }

  private void initializeInjector() {
    this.applicationComponent = DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this))
        .build();
  }

  public ApplicationComponent getApplicationComponent() {
    return this.applicationComponent;
  }
}
