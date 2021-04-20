package com.adminportal.Service.Impl;

import com.adminportal.Domain.BalanceRequest;
import com.adminportal.Domain.User;
import com.adminportal.Repository.BalanceRepository;
import com.adminportal.Service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BalanceServiceImpl implements BalanceService {

    @Autowired
    private BalanceRepository balanceRepository;

    @Override
    public List<BalanceRequest> findAll() {
         return (List<BalanceRequest>) balanceRepository.findAll();
    }

    @Override
    public Optional<BalanceRequest> findById(Long id) {
         return balanceRepository.findById(id);
    }

    @Override
    public BalanceRequest save(BalanceRequest balanceRequest) {
        return balanceRepository.save(balanceRequest);
    }

    @Override
    public BalanceRequest addBalance(User user, BalanceRequest balanceRequest) {

        BalanceRequest balanceRequest1 = new BalanceRequest();
        balanceRequest1.setUser(user);
        balanceRequest1.setSumToAdd(balanceRequest.getSumToAdd());

        balanceRepository.save(balanceRequest1);
        return balanceRequest1;
    }
}
