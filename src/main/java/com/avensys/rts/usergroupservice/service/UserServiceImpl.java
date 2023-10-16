package com.avensys.rts.usergroupservice.service;

import com.avensys.rts.usergroupservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.avensys.rts.usergroupservice.entity.UserEntity;
import com.avensys.rts.usergroupservice.payloadrequesst.UserRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    public UserRepository userRepository;

    @Override
    public UserEntity createUser(UserRequestDTO userRequestDTO) {
        LOG.info("createUser request processing");
        UserEntity userEntity= mapRequestToEntity(userRequestDTO);
        UserEntity save = userRepository.save(userEntity);
        return save;
    }

    @Override
    public List<UserEntity> getUserDataList() {
        LOG.info("getUserDataList request processing");
        List<UserEntity> userEntityList = userRepository.findAllAndIsDeleted(false);
        return userEntityList;
    }

    @Override
    public UserEntity getUserData(Integer id) {
        LOG.info("getUserData request processing");
        UserEntity userEntity=userRepository.findByIdAndIsDeleted(id,false).orElseThrow(
                () -> new EntityNotFoundException("userGroup with %s not found".formatted(id)));
        return userEntity;
    }

    @Override
    public UserEntity updateUser(UserRequestDTO userRequestDTO, Integer id) {
        LOG.info("updateUser request processing");
        UserEntity userEntity = userRepository.findByIdAndIsDeleted(id,false).orElseThrow(
                () -> new EntityNotFoundException("UserGroup with %s not found".formatted(id)));
        userEntity = mapRequestToEntity(userRequestDTO);

        return userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(Integer id) {
        LOG.info("deleteUser request processing");
        UserEntity userEntity =userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("userGroup with %s not found".formatted(id)));
        userEntity.setDeleted(true);
        userRepository.save(userEntity);

    }
    private UserEntity mapRequestToEntity(UserRequestDTO userRequestDTO ){
        UserEntity entity = new UserEntity();
        entity.setUsername(userRequestDTO.getUsername());
        entity.setId(userRequestDTO.getId());
        return entity;
    }
}
