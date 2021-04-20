package com.adminportal.Service;

import com.adminportal.Domain.BalanceRequest;
import com.adminportal.Domain.User;

import java.util.List;
import java.util.Optional;

public interface BalanceService {

    List<BalanceRequest> findAll();

    Optional<BalanceRequest> findById(Long id);

    BalanceRequest save(BalanceRequest book);

    BalanceRequest addBalance(User user, BalanceRequest balanceRequest);
}
