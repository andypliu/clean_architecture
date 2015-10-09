package com.edcast.presentation.samplefeature.di.components;

import com.edcast.presentation.samplefeature.di.PerActivity;
import com.edcast.presentation.samplefeature.di.modules.ActivityModule;
import com.edcast.presentation.samplefeature.di.modules.UserModule;
import com.edcast.presentation.samplefeature.view.fragment.UserDetailsFragment;
import com.edcast.presentation.samplefeature.view.fragment.UserListFragment;
import dagger.Component;

/**
 * A scope {@link com.edcast.presentation.samplefeature.di.PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, UserModule.class})
public interface UserComponent extends ActivityComponent {
  void inject(UserListFragment userListFragment);
  void inject(UserDetailsFragment userDetailsFragment);
}
