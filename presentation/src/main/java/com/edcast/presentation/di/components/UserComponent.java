package com.edcast.presentation.di.components;

import com.edcast.presentation.di.PerActivity;
import com.edcast.presentation.di.modules.ActivityModule;
import com.edcast.presentation.di.modules.UserModule;
import com.edcast.presentation.view.fragment.UserDetailsFragment;
import com.edcast.presentation.view.fragment.UserListFragment;
import dagger.Component;

/**
 * A scope {@link com.edcast.presentation.di.PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, UserModule.class})
public interface UserComponent extends ActivityComponent {
  void inject(UserListFragment userListFragment);
  void inject(UserDetailsFragment userDetailsFragment);
}
