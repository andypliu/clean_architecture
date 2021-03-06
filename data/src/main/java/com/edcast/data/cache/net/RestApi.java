package com.edcast.data.cache.net;

import com.edcast.data.cache.entity.UserEntity;
import java.util.List;
import rx.Observable;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {
  String API_BASE_URL = "http://www.android10.org/myapi/";

  /** Api url for getting all users */
  String API_URL_GET_USER_LIST = API_BASE_URL + "users.json";
  /** Api url for getting a user profile: Remember to concatenate id + 'json' */
  String API_URL_GET_USER_DETAILS = API_BASE_URL + "user_";

  /**
   * Retrieves an {@link rx.Observable} which will emit a List of {@link UserEntity}.
   */
  Observable<List<UserEntity>> userEntityList();

  /**
   * Retrieves an {@link rx.Observable} which will emit a {@link UserEntity}.
   *
   * @param userId The user id used to get user data.
   */
  Observable<UserEntity> userEntityById(final int userId);
}
