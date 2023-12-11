package com.vicariusapi.repository.interfaces.accesshistory;

import com.vicariusapi.model.AccessHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessHistoryRepositoryMySQL extends JpaRepository<AccessHistory, Long>, AccessHistoryRepositoryStrategy {
}
