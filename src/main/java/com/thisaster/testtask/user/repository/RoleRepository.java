package com.thisaster.testtask.user.repository;

import com.thisaster.testtask.user.entity.Role;
import com.thisaster.testtask.user.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
}
