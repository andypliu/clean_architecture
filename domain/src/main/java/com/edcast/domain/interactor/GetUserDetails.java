package com.edcast.domain.interactor;

import com.edcast.domain.User;
import com.edcast.domain.executor.PostExecutionThread;
import com.edcast.domain.executor.ThreadExecutor;
import com.edcast.domain.repository.UserRepository;
import javax.inject.Inject;
import rx.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving data related to an specific {@link User}.
 */
public class GetUserDetails extends UseCase {

  private final int userId;
  private final UserRepository userRepository;

  @Inject
  public GetUserDetails(int userId, UserRepository userRepository,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.userId = userId;
    this.userRepository = userRepository;
  }

  @Override protected Observable buildUseCaseObservable() {
    return this.userRepository.user(this.userId);
  }
}
