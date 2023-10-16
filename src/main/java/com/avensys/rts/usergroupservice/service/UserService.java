package com.avensys.rts.usergroupservice.service;

import com.avensys.rts.usergroupservice.entity.UserEntity;
import com.avensys.rts.usergroupservice.payloadrequesst.UserRequestDTO;
import java.util.List;

public interface UserService {
    public UserEntity createUser(UserRequestDTO userRequestDTO);
    public List<UserEntity> getUserDataList();
    public UserEntity getUserData(Integer id);
    public UserEntity updateUser(UserRequestDTO userRequestDTO,Integer id);
    public void deleteUser(Integer id);
}
