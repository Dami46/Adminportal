package com.adminportal.Repository;

import com.adminportal.Domain.BalanceRequest;
import org.springframework.data.repository.CrudRepository;

public interface BalanceRepository extends CrudRepository<BalanceRequest, Long> {
}
