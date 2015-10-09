package com.edcast.presentation.samplefeature.presenter;

import android.content.Context;
import android.test.AndroidTestCase;
import com.edcast.domain.interactor.GetUserList;
import com.edcast.presentation.samplefeature.mapper.UserModelDataMapper;
import com.edcast.presentation.samplefeature.view.UserListView;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Subscriber;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class UserListPresenterTest extends AndroidTestCase {

  private UserListPresenter userListPresenter;

  @Mock
  private Context mockContext;
  @Mock
  private UserListView mockUserListView;
  @Mock
  private GetUserList mockGetUserList;
  @Mock
  private UserModelDataMapper mockUserModelDataMapper;

  @Override protected void setUp() throws Exception {
    super.setUp();
    MockitoAnnotations.initMocks(this);
    userListPresenter = new UserListPresenter(mockGetUserList, mockUserModelDataMapper);
    userListPresenter.setView(mockUserListView);
  }

  public void testUserListPresenterInitialize() {
    given(mockUserListView.getContext()).willReturn(mockContext);

    userListPresenter.initialize();

    verify(mockUserListView).hideRetry();
    verify(mockUserListView).showLoading();
    verify(mockGetUserList).execute(any(Subscriber.class));
  }
}
