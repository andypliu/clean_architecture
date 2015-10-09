package com.edcast.presentation.samplefeature.di.modules;

import com.edcast.domain.executor.PostExecutionThread;
import com.edcast.domain.executor.ThreadExecutor;
import com.edcast.domain.samplefeature.interactor.GetUserDetails;
import com.edcast.domain.samplefeature.interactor.GetUserList;
import com.edcast.domain.samplefeature.interactor.UseCase;
import com.edcast.domain.samplefeature.repository.UserRepository;
import com.edcast.presentation.samplefeature.di.PerActivity;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

/**
 * Dagger module that provides user related collaborators.
 */
@Module
public class UserModule {

  private int userId = -1;

  public UserModule() {}

  public UserModule(int userId) {
    this.userId = userId;
  }

  @Provides @PerActivity @Named("userList") UseCase provideGetUserListUseCase(
      GetUserList getUserList) {
    return getUserList;
  }

  @Provides @PerActivity @Named("userDetails") UseCase provideGetUserDetailsUseCase(
      UserRepository userRepository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new GetUserDetails(userId, userRepository, threadExecutor, postExecutionThread);
  }
}