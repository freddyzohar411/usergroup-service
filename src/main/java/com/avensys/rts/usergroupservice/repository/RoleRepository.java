package com.avensys.rts.usergroupservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avensys.rts.usergroupservice.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
	Optional<RoleEntity> findByRoleName(String roleName);
}