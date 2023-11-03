package com.avensys.rts.usergroupservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.avensys.rts.usergroupservice.entity.UserGroupEntity;

public interface UserGroupRepository extends JpaRepository<UserGroupEntity, Long> {
	Boolean existsByUserGroupName(String userGroupName);

	Optional<UserGroupEntity> findByUserGroupName(String userGroupName);

	@Query(value = "SELECT group FROM UserGroupEntity group WHERE group.isDeleted = ?1")
	List<UserGroupEntity> findAllAndIsDeleted(boolean isDeleted);
}
