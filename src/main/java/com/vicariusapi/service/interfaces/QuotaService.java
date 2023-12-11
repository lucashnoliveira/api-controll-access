package com.vicariusapi.service.interfaces;

import com.vicariusapi.exceptions.UserBlockedException;
import com.vicariusapi.model.User;
import com.vicariusapi.model.UserQuota;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface QuotaService {

    /**
     * Get all quotas information from that user
     * @return list of users
     */
    public List<UserQuota> getUsersQuota();


    /**
     * Do some process consuming one available quota for the user
     * @return
     */
    public void consumeQuota(Long userId) throws Exception;

}
