package com.avensys.rts.usergroupservice.service;

import com.avensys.rts.usergroupservice.entity.UserGroupEntity;
import com.avensys.rts.usergroupservice.payloadrequesst.UserGroupRequestDTO;
import java.util.List;

public interface UserGroupService {
    /**
     *This method is used to create UserGroup data.
     * @param userGroupRequestDTO
     * @return
     */
    public UserGroupEntity createUserGroup(UserGroupRequestDTO userGroupRequestDTO);

    /**
     *This method is used to update UserGroup data by id.
     * @param userGroupRequestDTO
     * @param id
     * @return
     */
    public UserGroupEntity updateUserGroup( UserGroupRequestDTO userGroupRequestDTO,Integer id);

    /**
     *This method is used to delete UserGroup data by id.
     * @param id
     */
    public void deleteUserGroup(Integer id);

    /**
     *This method is used to retrieve UserGroup data.
     * @param id
     * @return
     */
    public UserGroupEntity getUserGroupData(Integer id);

    /**
     * This method is used to get all UserGroup list data.
     * @return
     */
    public List<UserGroupEntity> getUserGroupDataList();

}
