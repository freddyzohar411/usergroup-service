package com.avensys.rts.usergroupservice.service;

import com.avensys.rts.usergroupservice.repository.UserGroupRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avensys.rts.usergroupservice.entity.UserGroupEntity;
import com.avensys.rts.usergroupservice.entity.UserGroupIntersectionEntity;
import com.avensys.rts.usergroupservice.payloadrequesst.UserDTO;
import com.avensys.rts.usergroupservice.payloadrequesst.UserGroupRequestDTO;
import com.avensys.rts.usergroupservice.repository.UserGroupIntersectionRepository;

import java.util.List;
@Service
public class UserGroupServiceImpl implements UserGroupService{
    private static final Logger LOG = LoggerFactory.getLogger(UserGroupServiceImpl.class);
    @Autowired
    public UserGroupRepository userGroupRepository;
    @Autowired
    public UserGroupIntersectionRepository userGroupIntersectionRepository;

    @Override
    @Transactional
    public UserGroupEntity createUserGroup(UserGroupRequestDTO userGroupRequestDTO) {
        LOG.info("createUserGroup request processing");
        UserGroupEntity userGroupEntity= mapRequestToEntity(userGroupRequestDTO);
        UserGroupEntity save = userGroupRepository.save(userGroupEntity);
        List<UserDTO>userDTOList=userGroupRequestDTO.getUserDTOList();
        int totalUsers=userDTOList.size();
        for(int i= 0;i<totalUsers;i++){
            UserGroupIntersectionEntity intersectionEntity = new UserGroupIntersectionEntity();
            UserDTO userDTO = userDTOList.get(i);
            intersectionEntity.setUserGroupId(save.getId());
            intersectionEntity.setUserId(userDTO.getId());
            userGroupIntersectionRepository.save(intersectionEntity);
        }
        return save;
    }

    @Override
    public UserGroupEntity updateUserGroup(UserGroupRequestDTO userGroupRequestDTO, Integer id) {
        LOG.info("updateUserGroup request processing");
        UserGroupEntity userGroupEntity = userGroupRepository.findByIdAndIsDeleted(id,false).orElseThrow(
                () -> new EntityNotFoundException("UserGroup with %s not found".formatted(id)));
        userGroupEntity = mapRequestToEntity(userGroupRequestDTO);

        return userGroupRepository.save(userGroupEntity);
    }

    @Override
    public void deleteUserGroup(Integer id) {
        LOG.info("deleteUserGroup request processing");
        UserGroupEntity userGroupEntity=userGroupRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("userGroup with %s not found".formatted(id)));
        userGroupEntity.setDeleted(true);
        userGroupRepository.save(userGroupEntity);

    }

    @Override
    public UserGroupEntity getUserGroupData( Integer id) {
        LOG.info("getUserGroupData request processing");
        UserGroupEntity userGroupEntity=userGroupRepository.findByIdAndIsDeleted(id,false).orElseThrow(
                () -> new EntityNotFoundException("userGroup with %s not found".formatted(id)));
        return userGroupEntity;
    }

    @Override
    public List<UserGroupEntity> getUserGroupDataList() {
        LOG.info("getUserGroupDataList request processing");
        List<UserGroupEntity> userGroupEntityList = userGroupRepository.findAllAndIsDeleted(false);
        return userGroupEntityList;
    }

    private UserGroupEntity mapRequestToEntity(UserGroupRequestDTO userGroupRequestDTO ){
        UserGroupEntity entity = new UserGroupEntity();
        entity.setUserGroupName(userGroupRequestDTO.getUserGroupName());
        entity.setDescription(userGroupRequestDTO.getDescription());
       return entity;
    }
}
