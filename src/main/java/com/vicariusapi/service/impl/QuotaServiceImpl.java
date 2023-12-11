package com.vicariusapi.service.impl;

import com.vicariusapi.exceptions.UserBlockedException;
import com.vicariusapi.exceptions.UserNotFoundException;
import com.vicariusapi.model.AccessHistory;
import com.vicariusapi.model.User;
import com.vicariusapi.model.UserQuota;
import com.vicariusapi.repository.RepositoryFactory;
import com.vicariusapi.repository.interfaces.accesshistory.AccessHistoryRepositoryStrategy;
import com.vicariusapi.repository.interfaces.quota.QuotaRepositoryStrategy;
import com.vicariusapi.repository.interfaces.user.UserRepositoryStrategy;
import com.vicariusapi.service.interfaces.QuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class QuotaServiceImpl implements QuotaService {

    private final AccessHistoryRepositoryStrategy accessHistoryRepositoryStrategy;

    private final QuotaRepositoryStrategy quotaRepositoryStrategy;

    private final UserRepositoryStrategy userRepositoryStrategy;

    private final Map<Long, Lock> userLocks = new ConcurrentHashMap<>();

    private final Integer MAX_CONSUME_ATTEMPTS = 5;

    @Autowired
    public QuotaServiceImpl(RepositoryFactory repositoryFactory){
        this.accessHistoryRepositoryStrategy = repositoryFactory.getAccessHistoryRepository();
        this.quotaRepositoryStrategy = repositoryFactory.getQuotaRepository();
        this.userRepositoryStrategy = repositoryFactory.getUserRepository();
    }

    @Override
    public void consumeQuota(Long userId) throws Exception {
        User user = getUser(userId);

        checkUserBlocked(user);

        LocalDateTime currentUTCTime = LocalDateTime.now(ZoneId.of("UTC"));

        AccessHistory accessHistory = new AccessHistory();
        accessHistory.setUser(user);
        accessHistory.setAccessTimeUtc(currentUTCTime);
        accessHistoryRepositoryStrategy.save(accessHistory);

        // This lock solution works well in a single instance running, but if  we have multiple instances we should use
        // a distributed lock system like redis.
        Lock userLock = userLocks.computeIfAbsent(userId, k -> new ReentrantLock());
        userLock.lock();
        try {
            UserQuota userQuota = quotaRepositoryStrategy.findByUser(user)
                    .orElseGet(() -> {
                        UserQuota newUserQuota = new UserQuota();
                        newUserQuota.setUser(user);
                        return newUserQuota;
                    });

            userQuota.setAccessCount(userQuota.getAccessCount() + 1);
            quotaRepositoryStrategy.save(userQuota);

            user.setLastLoginTimeUtc(currentUTCTime);
            updateUser(user);
        } finally {
            userLock.unlock();
        }
    }

    private void checkUserBlocked(User user) throws UserBlockedException {
        if(user.getBlocked()){
            throw new UserBlockedException("User is blocked!");
        }

        Optional<UserQuota> userQuota = quotaRepositoryStrategy.findByUser(user);

        if(userQuota.isPresent()){
            if(userQuota.get().getAccessCount() >= MAX_CONSUME_ATTEMPTS){
                user.setBlocked(true);
                updateUser(user);

                throw new UserBlockedException("User is Blocked!");
            }
        }
    }


    private User getUser(Long userId) throws UserNotFoundException {
        return userRepositoryStrategy.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public List<UserQuota> getUsersQuota() {
        return quotaRepositoryStrategy.findAll();
    }

    public void updateUser(User user) {
        userRepositoryStrategy.save(user);
    }

}
