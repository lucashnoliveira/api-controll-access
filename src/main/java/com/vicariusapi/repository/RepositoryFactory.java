package com.vicariusapi.repository;

import com.vicariusapi.repository.impl.accesshistory.AccessHistoryRepositoryElasticImpl;
import com.vicariusapi.repository.impl.quota.QuotaRepositoryElasticImpl;
import com.vicariusapi.repository.impl.user.UserRepositoryElasticImpl;
import com.vicariusapi.repository.interfaces.accesshistory.AccessHistoryRepositoryMySQL;
import com.vicariusapi.repository.interfaces.accesshistory.AccessHistoryRepositoryStrategy;
import com.vicariusapi.repository.interfaces.quota.QuotaRepositoryMySQL;
import com.vicariusapi.repository.interfaces.quota.QuotaRepositoryStrategy;
import com.vicariusapi.repository.interfaces.user.UserRepositoryMySQL;
import com.vicariusapi.repository.interfaces.user.UserRepositoryStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class RepositoryFactory {

    // User repositories
    private final UserRepositoryMySQL userRepositoryMySQL;
    private final UserRepositoryElasticImpl userRepositoryElastic;

    // Quota repositories
    private final QuotaRepositoryMySQL quotaRepositoryMySQL;
    private final QuotaRepositoryElasticImpl quotaRepositoryElastic;

    // Access History repositories
    private final AccessHistoryRepositoryMySQL accessHistoryRepositoryMySQL;
    private final AccessHistoryRepositoryElasticImpl accessHistoryRepositoryElastic;


    @Autowired
    public  RepositoryFactory(UserRepositoryMySQL userRepositoryMySQL,
                              UserRepositoryElasticImpl userRepositoryElastic,
                              QuotaRepositoryMySQL quotaRepositoryMySQL,
                              QuotaRepositoryElasticImpl quotaRepositoryElastic,
                              AccessHistoryRepositoryMySQL accessHistoryRepositoryMySQL,
                              AccessHistoryRepositoryElasticImpl accessHistoryRepositoryElastic) {
        this.userRepositoryMySQL = userRepositoryMySQL;
        this.userRepositoryElastic = userRepositoryElastic;
        this.quotaRepositoryMySQL = quotaRepositoryMySQL;
        this.quotaRepositoryElastic = quotaRepositoryElastic;
        this.accessHistoryRepositoryMySQL = accessHistoryRepositoryMySQL;
        this.accessHistoryRepositoryElastic = accessHistoryRepositoryElastic;
    }

    public UserRepositoryStrategy getUserRepository() {
        return isWithinMySQLHours() ? userRepositoryMySQL : userRepositoryElastic;
    }

    public QuotaRepositoryStrategy getQuotaRepository() {
        return isWithinMySQLHours() ? quotaRepositoryMySQL : quotaRepositoryElastic;

    }

    public AccessHistoryRepositoryStrategy getAccessHistoryRepository() {
        return isWithinMySQLHours() ? accessHistoryRepositoryMySQL : accessHistoryRepositoryElastic;
    }



    private boolean isWithinMySQLHours() {
        LocalDateTime currentTime = LocalDateTime.now();
        int hour = currentTime.getHour();
        return hour >= 9 && hour < 17;
    }

}
