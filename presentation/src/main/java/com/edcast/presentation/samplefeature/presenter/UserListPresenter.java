package com.edcast.presentation.samplefeature.presenter;

import android.support.annotation.NonNull;
import com.edcast.domain.User;
import com.edcast.domain.samplefeature.exception.DefaultErrorBundle;
import com.edcast.domain.samplefeature.exception.ErrorBundle;
import com.edcast.domain.samplefeature.interactor.DefaultSubscriber;
import com.edcast.domain.samplefeature.interactor.UseCase;
import com.edcast.presentation.samplefeature.exception.ErrorMessageFactory;
import com.edcast.presentation.samplefeature.di.PerActivity;
import com.edcast.presentation.samplefeature.mapper.UserModelDataMapper;
import com.edcast.presentation.samplefeature.model.UserModel;
import com.edcast.presentation.samplefeature.view.UserListView;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class UserListPresenter extends DefaultSubscriber<List<User>> implements Presenter {

  private UserListView viewListView;

  private final UseCase getUserListUseCase;
  private final UserModelDataMapper userModelDataMapper;

  @Inject
  public UserListPresenter(@Named("userList") UseCase getUserListUserCase, UserModelDataMapper userModelDataMapper) {
    this.getUserListUseCase = getUserListUserCase;
    this.userModelDataMapper = userModelDataMapper;
  }

  public void setView(@NonNull UserListView view) {
    this.viewListView = view;
  }

  @Override public void resume() {}

  @Override public void pause() {}

  @Override public void destroy() {
    this.getUserListUseCase.unsubscribe();
  }

  /**
   * Initializes the presenter by start retrieving the user list.
   */
  public void initialize() {
    this.loadUserList();
  }

  /**
   * Loads all users.
   */
  private void loadUserList() {
    this.hideViewRetry();
    this.showViewLoading();
    this.getUserList();
  }

  public void onUserClicked(UserModel userModel) {
    this.viewListView.viewUser(userModel);
  }

  private void showViewLoading() {
    this.viewListView.showLoading();
  }

  private void hideViewLoading() {
    this.viewListView.hideLoading();
  }

  private void showViewRetry() {
    this.viewListView.showRetry();
  }

  private void hideViewRetry() {
    this.viewListView.hideRetry();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.viewListView.getContext(),
        errorBundle.getException());
    this.viewListView.showError(errorMessage);
  }

  private void showUsersCollectionInView(Collection<User> usersCollection) {
    final Collection<UserModel> userModelsCollection =
        this.userModelDataMapper.transform(usersCollection);
    this.viewListView.renderUserList(userModelsCollection);
  }

  private void getUserList() {
    this.getUserListUseCase.execute(new UserListSubscriber());
  }

  private final class UserListSubscriber extends DefaultSubscriber<List<User>> {

    @Override public void onCompleted() {
      UserListPresenter.this.hideViewLoading();
    }

    @Override public void onError(Throwable e) {
      UserListPresenter.this.hideViewLoading();
      UserListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
      UserListPresenter.this.showViewRetry();
    }

    @Override public void onNext(List<User> users) {
      UserListPresenter.this.showUsersCollectionInView(users);
    }
  }
}
