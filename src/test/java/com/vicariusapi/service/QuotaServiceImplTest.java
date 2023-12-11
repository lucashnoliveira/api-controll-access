package com.vicariusapi.service;

import com.vicariusapi.exceptions.UserBlockedException;
import com.vicariusapi.exceptions.UserNotFoundException;
import com.vicariusapi.model.AccessHistory;
import com.vicariusapi.model.User;
import com.vicariusapi.model.UserQuota;
import com.vicariusapi.repository.RepositoryFactory;
import com.vicariusapi.repository.interfaces.accesshistory.AccessHistoryRepositoryStrategy;
import com.vicariusapi.repository.interfaces.quota.QuotaRepositoryStrategy;
import com.vicariusapi.repository.interfaces.user.UserRepositoryStrategy;
import com.vicariusapi.service.impl.QuotaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.concurrent.locks.Lock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class QuotaServiceImplTest {
    @Mock
    private UserRepositoryStrategy userRepositoryStrategy;

    @Mock
    private AccessHistoryRepositoryStrategy accessHistoryRepositoryStrategy;

    @Mock
    private QuotaRepositoryStrategy quotaRepositoryStrategy;

    @Mock
    private Lock mockLock;

    @Mock
    private RepositoryFactory repositoryFactory;


    @InjectMocks
    private QuotaServiceImpl quotaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(repositoryFactory.getAccessHistoryRepository()).thenReturn(accessHistoryRepositoryStrategy);
        when(repositoryFactory.getQuotaRepository()).thenReturn(quotaRepositoryStrategy);
        when(repositoryFactory.getUserRepository()).thenReturn(userRepositoryStrategy);

        quotaService = new QuotaServiceImpl(repositoryFactory);
    }

    @Test
    void testConsumeQuotaCorrectly() throws Exception {
        // GIVEN THE USER INFORMATIONS
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setBlocked(false);

        when(userRepositoryStrategy.findById(userId)).thenReturn(Optional.of(user));
        when(quotaRepositoryStrategy.findByUser(user)).thenReturn(Optional.of(new UserQuota()));
        when(mockLock.tryLock()).thenReturn(true);

        // WHEN THE CONSUME QUOTA METHOD IS CALLED
        quotaService.consumeQuota(userId);

        // VERIFY THAT THE REQUIRED METHODS WERE CALLED WITH THE CORRECT PARAMETERS
        verify(accessHistoryRepositoryStrategy, times(1)).save(any(AccessHistory.class));
        verify(quotaRepositoryStrategy, times(1)).save(any(UserQuota.class));
        verify(userRepositoryStrategy, times(1)).save(any(User.class));
    }

    @Test
    void testConsumeQuotaThrowsUserBlockedException() {
        // GIVEN THE USER INFORMATIONS
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setBlocked(true);

        when(userRepositoryStrategy.findById(userId)).thenReturn(Optional.of(user));
        when(quotaRepositoryStrategy.findByUser(user)).thenReturn(Optional.of(new UserQuota()));
        when(mockLock.tryLock()).thenReturn(true);

        // WHEN THE CONSUME QUOTA METHOD IS CALLED AN EXCEPTION IS THROWN
        assertThrows(UserBlockedException.class, () -> quotaService.consumeQuota(userId));


        // VERIFY THAT THE REQUIRED METHODS ARE NOT CALLED
        verify(accessHistoryRepositoryStrategy, never()).save(any());
        verify(quotaRepositoryStrategy, never()).save(any());
        verify(userRepositoryStrategy, never()).save(any());
    }


    @Test
    void testConsumeQuotaThrowsUserNotFounddException() {
        // GIVEN THE USER INFORMATIONS
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setBlocked(false);

        when(userRepositoryStrategy.findById(userId)).thenReturn(Optional.empty());

        // WHEN THE CONSUME QUOTA METHOD IS CALLED AN EXCEPTION IS THROWN
        assertThrows(UserNotFoundException.class, () -> quotaService.consumeQuota(userId));


        // VERIFY THAT THE REQUIRED METHODS ARE NOT CALLED
        verify(accessHistoryRepositoryStrategy, never()).save(any());
        verify(quotaRepositoryStrategy, never()).save(any());
        verify(userRepositoryStrategy, never()).save(any());
    }

}
