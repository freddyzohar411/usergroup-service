package com.avensys.rts.usergroupservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avensys.rts.usergroupservice.entity.UserGroupEntity;

public interface UserGroupRepository extends JpaRepository<UserGroupEntity, Long> {
	Boolean existsByUserGroupName(String userGroupName);
}
