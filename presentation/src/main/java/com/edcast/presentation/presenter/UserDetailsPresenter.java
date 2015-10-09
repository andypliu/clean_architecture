package com.edcast.presentation.presenter;

import android.support.annotation.NonNull;
import com.edcast.domain.User;
import com.edcast.domain.exception.DefaultErrorBundle;
import com.edcast.domain.exception.ErrorBundle;
import com.edcast.domain.interactor.DefaultSubscriber;
import com.edcast.domain.interactor.UseCase;
import com.edcast.presentation.exception.ErrorMessageFactory;
import com.edcast.presentation.di.PerActivity;
import com.edcast.presentation.mapper.UserModelDataMapper;
import com.edcast.presentation.model.UserModel;
import com.edcast.presentation.view.UserDetailsView;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class UserDetailsPresenter implements Presenter {

  /** id used to retrieve user details */
  private int userId;

  private UserDetailsView viewDetailsView;

  private final UseCase getUserDetailsUseCase;
  private final UserModelDataMapper userModelDataMapper;

  @Inject
  public UserDetailsPresenter(@Named("userDetails") UseCase getUserDetailsUseCase,
      UserModelDataMapper userModelDataMapper) {
    this.getUserDetailsUseCase = getUserDetailsUseCase;
    this.userModelDataMapper = userModelDataMapper;
  }

  public void setView(@NonNull UserDetailsView view) {
    this.viewDetailsView = view;
  }

  @Override public void resume() {}

  @Override public void pause() {}

  @Override public void destroy() {
    this.getUserDetailsUseCase.unsubscribe();
  }

  /**
   * Initializes the presenter by start retrieving user details.
   */
  public void initialize(int userId) {
    this.userId = userId;
    this.loadUserDetails();
  }

  /**
   * Loads user details.
   */
  private void loadUserDetails() {
    this.hideViewRetry();
    this.showViewLoading();
    this.getUserDetails();
  }

  private void showViewLoading() {
    this.viewDetailsView.showLoading();
  }

  private void hideViewLoading() {
    this.viewDetailsView.hideLoading();
  }

  private void showViewRetry() {
    this.viewDetailsView.showRetry();
  }

  private void hideViewRetry() {
    this.viewDetailsView.hideRetry();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.viewDetailsView.getContext(),
        errorBundle.getException());
    this.viewDetailsView.showError(errorMessage);
  }

  private void showUserDetailsInView(User user) {
    final UserModel userModel = this.userModelDataMapper.transform(user);
    this.viewDetailsView.renderUser(userModel);
  }

  private void getUserDetails() {
    this.getUserDetailsUseCase.execute(new UserDetailsSubscriber());
  }

  private final class UserDetailsSubscriber extends DefaultSubscriber<User> {

    @Override public void onCompleted() {
      UserDetailsPresenter.this.hideViewLoading();
    }

    @Override public void onError(Throwable e) {
      UserDetailsPresenter.this.hideViewLoading();
      UserDetailsPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
      UserDetailsPresenter.this.showViewRetry();
    }

    @Override public void onNext(User user) {
      UserDetailsPresenter.this.showUserDetailsInView(user);
    }
  }
}
