package com.edcast.data.cache.repository;

import com.edcast.data.cache.entity.mapper.UserEntityDataMapper;
import com.edcast.data.cache.repository.datasource.UserDataStore;
import com.edcast.data.cache.repository.datasource.UserDataStoreFactory;
import com.edcast.domain.User;
import com.edcast.domain.repository.UserRepository;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;

/**
 * {@link UserRepository} for retrieving user data.
 */
@Singleton
public class UserDataRepository implements UserRepository {

  private final UserDataStoreFactory userDataStoreFactory;
  private final UserEntityDataMapper userEntityDataMapper;

  /**
   * Constructs a {@link UserRepository}.
   *
   * @param dataStoreFactory A factory to construct different data source implementations.
   * @param userEntityDataMapper {@link UserEntityDataMapper}.
   */
  @Inject
  public UserDataRepository(UserDataStoreFactory dataStoreFactory,
      UserEntityDataMapper userEntityDataMapper) {
    this.userDataStoreFactory = dataStoreFactory;
    this.userEntityDataMapper = userEntityDataMapper;
  }

  @SuppressWarnings("Convert2MethodRef")
  @Override public Observable<List<User>> users() {
    //we always get all users from the cloud
    final UserDataStore userDataStore = this.userDataStoreFactory.createCloudDataStore();
    return userDataStore.userEntityList()
        .map(userEntities -> this.userEntityDataMapper.transform(userEntities));
  }

  @SuppressWarnings("Convert2MethodRef")
  @Override public Observable<User> user(int userId) {
    final UserDataStore userDataStore = this.userDataStoreFactory.create(userId);
    return userDataStore.userEntityDetails(userId)
        .map(userEntity -> this.userEntityDataMapper.transform(userEntity));
  }
}
