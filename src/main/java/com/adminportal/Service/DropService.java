package com.adminportal.Service;

import com.adminportal.Domain.DropItem;

import java.util.List;
import java.util.Optional;

public interface DropService {

    List<DropItem> findAll();

    Optional<DropItem> findById(Long id);

    DropItem save(DropItem dropItem);

    void removeOne(Long id);
}
