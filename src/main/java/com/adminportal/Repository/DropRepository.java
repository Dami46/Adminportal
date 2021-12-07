package com.adminportal.Repository;

import com.adminportal.Domain.DropItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DropRepository extends CrudRepository<DropItem, Long> {
}
