package com.vicariusapi.repository.impl.accesshistory;

import com.vicariusapi.model.AccessHistory;
import com.vicariusapi.model.UserQuota;
import com.vicariusapi.repository.interfaces.accesshistory.AccessHistoryRepositoryElastic;
import com.vicariusapi.repository.interfaces.quota.QuotaRepositoryElastic;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AccessHistoryRepositoryElasticImpl implements AccessHistoryRepositoryElastic {
    @Override
    public List<AccessHistory> findAll() {
        return null;
    }

    @Override
    public Optional<AccessHistory> findByUserId(Long id) {
        return Optional.empty();
    }

    @Override
    public AccessHistory save(AccessHistory accessHistory) {
        return null;
    }
}
