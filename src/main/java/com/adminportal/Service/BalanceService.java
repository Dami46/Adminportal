package com.adminportal.Service;

import com.adminportal.Domain.BalanceRequest;
import com.adminportal.Domain.User;

import java.util.List;

public interface BalanceService {

    List<BalanceRequest> findAll();

    BalanceRequest findById(Long id);

    BalanceRequest save(BalanceRequest book);

    BalanceRequest addBalance(User user, BalanceRequest balanceRequest);

    void removeOne(Long id);
}
