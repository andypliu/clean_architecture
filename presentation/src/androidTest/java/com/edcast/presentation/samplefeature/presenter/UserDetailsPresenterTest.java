package com.edcast.presentation.samplefeature.presenter;

import android.content.Context;
import android.test.AndroidTestCase;
import com.edcast.domain.interactor.GetUserDetails;
import com.edcast.presentation.samplefeature.mapper.UserModelDataMapper;
import com.edcast.presentation.samplefeature.view.UserDetailsView;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Subscriber;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class UserDetailsPresenterTest extends AndroidTestCase {

  private static final int FAKE_USER_ID = 123;

  private UserDetailsPresenter userDetailsPresenter;

  @Mock
  private Context mockContext;
  @Mock
  private UserDetailsView mockUserDetailsView;
  @Mock
  private GetUserDetails mockGetUserDetails;
  @Mock
  private UserModelDataMapper mockUserModelDataMapper;

  @Override protected void setUp() throws Exception {
    super.setUp();
    MockitoAnnotations.initMocks(this);
    userDetailsPresenter = new UserDetailsPresenter(mockGetUserDetails,
        mockUserModelDataMapper);
    userDetailsPresenter.setView(mockUserDetailsView);
  }

  public void testUserDetailsPresenterInitialize() {
    given(mockUserDetailsView.getContext()).willReturn(mockContext);

    userDetailsPresenter.initialize(FAKE_USER_ID);

    verify(mockUserDetailsView).hideRetry();
    verify(mockUserDetailsView).showLoading();
    verify(mockGetUserDetails).execute(any(Subscriber.class));
  }
}
