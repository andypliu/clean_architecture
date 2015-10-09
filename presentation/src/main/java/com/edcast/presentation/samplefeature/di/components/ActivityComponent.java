package com.edcast.presentation.samplefeature.di.components;

import android.app.Activity;
import com.edcast.presentation.samplefeature.di.PerActivity;
import com.edcast.presentation.samplefeature.di.modules.ActivityModule;
import dagger.Component;

/**
 * A base component upon which fragment's components may depend.
 * Activity-level components should extend this component.
 *
 * Subtypes of ActivityComponent should be decorated with annotation:
 * {@link com.edcast.presentation.samplefeature.di.PerActivity}
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
  //Exposed to sub-graphs.
  Activity activity();
}
