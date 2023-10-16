package com.avensys.rts.usergroupservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.avensys.rts.usergroupservice.constant.MessageConstants;
import com.avensys.rts.usergroupservice.entity.UserGroupEntity;
import com.avensys.rts.usergroupservice.payloadrequesst.UserGroupRequestDTO;
import com.avensys.rts.usergroupservice.service.UserGroupService;
import com.avensys.rts.usergroupservice.util.ResponseUtil;
import java.util.List;

@RestController
@RequestMapping("/userGroup")
public class UserGroupController {
    private static final Logger LOG = LoggerFactory.getLogger(UserGroupController .class);
    @Autowired
    private UserGroupService userGroupService;
    @Autowired
    private MessageSource messageSource;

    /**
     * This method is used to create UserGroup data.
     * @param userGroupRequestDTO
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<Object> createUserGroup(@RequestBody UserGroupRequestDTO userGroupRequestDTO){
        LOG.info("createUserGroup request started");
        UserGroupEntity userGroupEntity = userGroupService.createUserGroup(userGroupRequestDTO);
        return ResponseUtil.generateSuccessResponse(userGroupEntity, HttpStatus.CREATED,
                messageSource.getMessage(MessageConstants.MESSAGE_CREATED, null, LocaleContextHolder.getLocale()));
    }

    /**
     * This method is used to update UserGroup data by id.
     * @param userGroupRequestDTO
     * @param id
     * @return
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Object>updateUserGroup(@RequestBody UserGroupRequestDTO userGroupRequestDTO,@PathVariable Integer id){
        LOG.info("updateUserGroup request started");
        UserGroupEntity userGroupEntity= userGroupService.updateUserGroup(userGroupRequestDTO,id);
        return ResponseUtil.generateSuccessResponse(userGroupEntity, HttpStatus.OK,
                messageSource.getMessage(MessageConstants.MESSAGE_UPDATED, null, LocaleContextHolder.getLocale()));
    }

    /**
     * This method is used to delete UserGroup data by id.
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>deleteUserGroup(@PathVariable Integer id){
        LOG.info("deleteUserGroup request started");
        userGroupService.deleteUserGroup(id);
        return ResponseUtil.generateSuccessResponse(null, HttpStatus.OK,
                messageSource.getMessage(MessageConstants.MESSAGE_DELETED, null, LocaleContextHolder.getLocale()));
    }

    /**
     * This method is used to retrieve UserGroup data.
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<?>getUserGroupData(@PathVariable Integer id){
        LOG.info("getUserGroupData request started");
        UserGroupEntity userGroupEntity = userGroupService.getUserGroupData(id);
        return ResponseUtil.generateSuccessResponse(userGroupEntity, HttpStatus.OK,
                messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
    }

    /**
     * This method is used to get all UserGroup list data.
     * @return
     */
    @GetMapping
    public ResponseEntity<?>getUserGroupDataList(){
        LOG.info("getUserGroupDataList request started");
        List<UserGroupEntity>userGroupEntityList = userGroupService.getUserGroupDataList();
        return ResponseUtil.generateSuccessResponse(userGroupEntityList, HttpStatus.OK,
                messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
    }


}
