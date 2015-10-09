package com.edcast.presentation.di.components;

import android.content.Context;
import com.edcast.domain.executor.PostExecutionThread;
import com.edcast.domain.executor.ThreadExecutor;
import com.edcast.domain.repository.UserRepository;
import com.edcast.presentation.di.modules.ApplicationModule;
import com.edcast.presentation.view.activity.BaseActivity;
import dagger.Component;
import javax.inject.Singleton;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
  void inject(BaseActivity baseActivity);

  //Exposed to sub-graphs.
  Context context();
  ThreadExecutor threadExecutor();
  PostExecutionThread postExecutionThread();
  UserRepository userRepository();
}
