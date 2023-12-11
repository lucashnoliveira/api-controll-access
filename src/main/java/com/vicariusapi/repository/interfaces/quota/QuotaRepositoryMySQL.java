package com.vicariusapi.repository.interfaces.quota;

import com.vicariusapi.model.UserQuota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuotaRepositoryMySQL extends JpaRepository<UserQuota, Long>, QuotaRepositoryStrategy {
}
