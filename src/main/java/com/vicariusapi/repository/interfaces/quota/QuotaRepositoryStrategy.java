package com.vicariusapi.repository.interfaces.quota;

import com.vicariusapi.model.User;
import com.vicariusapi.model.UserQuota;

import java.util.List;
import java.util.Optional;

public interface QuotaRepositoryStrategy {

    List<UserQuota> findAll();
    Optional<UserQuota> findById(Long id);
    Optional<UserQuota> findByUser(User user);
    UserQuota save(UserQuota userQuota);

}
