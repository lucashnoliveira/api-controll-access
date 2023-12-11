package com.vicariusapi.repository.interfaces.accesshistory;

import com.vicariusapi.model.AccessHistory;

import java.util.List;
import java.util.Optional;

public interface AccessHistoryRepositoryStrategy {

    List<AccessHistory> findAll();
    Optional<AccessHistory> findByUserId(Long id);
    AccessHistory save(AccessHistory accessHistory);

}
