package com.vicariusapi.repository.impl.quota;

import com.vicariusapi.model.User;
import com.vicariusapi.model.UserQuota;
import com.vicariusapi.repository.interfaces.quota.QuotaRepositoryElastic;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class QuotaRepositoryElasticImpl implements QuotaRepositoryElastic {
    @Override
    public List<UserQuota> findAll() {
        return null;
    }

    @Override
    public Optional<UserQuota> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<UserQuota> findByUser(User user) {
        return Optional.empty();
    }

    @Override
    public UserQuota save(UserQuota userQuota) {
        return null;
    }
}
