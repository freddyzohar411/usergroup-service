package com.avensys.rts.usergroupservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.avensys.rts.usergroupservice.entity.UserGroupEntity;
import java.util.List;
import java.util.Optional;

public interface UserGroupRepository extends JpaRepository<UserGroupEntity,Integer> {
    Optional<UserGroupEntity> findByIdAndIsDeleted(Integer id, boolean isDeleted);
    @Query(value = "SELECT a FROM UserGroupEntity a WHERE a.isDeleted = ?1")
    List<UserGroupEntity> findAllAndIsDeleted(boolean isDeleted);
}
