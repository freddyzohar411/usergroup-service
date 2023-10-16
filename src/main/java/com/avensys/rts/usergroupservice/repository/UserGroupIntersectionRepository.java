package com.avensys.rts.usergroupservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avensys.rts.usergroupservice.entity.UserGroupIntersectionEntity;

public interface UserGroupIntersectionRepository extends JpaRepository<UserGroupIntersectionEntity,Integer> {
}
