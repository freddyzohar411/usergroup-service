package com.avensys.rts.usergroupservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.avensys.rts.usergroupservice.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity> findByIdAndIsDeleted(Integer id, boolean isDeleted);
    @Query(value = "SELECT * FROM UserEntity a WHERE a.isDeleted = ?1",nativeQuery = true)
    List<UserEntity> findAllAndIsDeleted(boolean isDeleted);

}
