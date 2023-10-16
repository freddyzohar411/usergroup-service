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
import com.avensys.rts.usergroupservice.entity.UserEntity;
import com.avensys.rts.usergroupservice.payloadrequesst.UserRequestDTO;
import com.avensys.rts.usergroupservice.service.UserService;
import com.avensys.rts.usergroupservice.util.ResponseUtil;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;

    /**
     * This method is used to create User data.
     * @param userRequestDTO
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody UserRequestDTO userRequestDTO){
        LOG.info("createUser request started");
        UserEntity userEntity = userService.createUser(userRequestDTO);
        return ResponseUtil.generateSuccessResponse(userEntity, HttpStatus.CREATED,
                messageSource.getMessage(MessageConstants.MESSAGE_FOR_CREATED, null, LocaleContextHolder.getLocale()));
    }

    /**
     * This method is used to update User data by id.
     * @param userRequestDTO
     * @param id
     * @return
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Object>updateUser(@RequestBody UserRequestDTO userRequestDTO,@PathVariable Integer id){
        LOG.info("updateUser request started");
        UserEntity userEntity= userService.updateUser(userRequestDTO,id);
        return ResponseUtil.generateSuccessResponse(userEntity, HttpStatus.OK,
                messageSource.getMessage(MessageConstants.MESSAGE_FOR_UPDATED, null, LocaleContextHolder.getLocale()));
    }

    /**
     * This method is used to delete User data by id.
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>deleteUser(@PathVariable Integer id){
        LOG.info("deleteUser request started");
        userService.deleteUser(id);
        return ResponseUtil.generateSuccessResponse(null, HttpStatus.OK,
                messageSource.getMessage(MessageConstants.MESSAGE_FOR_DELETED, null, LocaleContextHolder.getLocale()));
    }

    /**
     *  This method is used to retrieve UserGroup data.
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<?>getUserData(@PathVariable Integer id){
        LOG.info("getUserData request started");
        UserEntity userEntity = userService.getUserData(id);
        return ResponseUtil.generateSuccessResponse(userEntity, HttpStatus.OK,
                messageSource.getMessage(MessageConstants.MESSAGE_FOR_SUCCESS, null, LocaleContextHolder.getLocale()));
    }

    /**
     * This method is used to get all UserGroup list data.
     * @return
     */
    @GetMapping
    public ResponseEntity<?>getUserDataList(){
        LOG.info("getUserDataList request started");
        List<UserEntity>userEntityList = userService.getUserDataList();
        return ResponseUtil.generateSuccessResponse(userEntityList, HttpStatus.OK,
                messageSource.getMessage(MessageConstants.MESSAGE_FOR_SUCCESS, null, LocaleContextHolder.getLocale()));
    }



}
