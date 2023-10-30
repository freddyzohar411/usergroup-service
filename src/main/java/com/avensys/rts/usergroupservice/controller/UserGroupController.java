package com.avensys.rts.usergroupservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avensys.rts.usergroupservice.constant.MessageConstants;
import com.avensys.rts.usergroupservice.entity.UserGroupEntity;
import com.avensys.rts.usergroupservice.exception.ServiceException;
import com.avensys.rts.usergroupservice.service.UserGroupService;
import com.avensys.rts.usergroupservice.util.ResponseUtil;

@RestController
@RequestMapping("/api/usergroup")
public class UserGroupController {

	private static final Logger LOG = LoggerFactory.getLogger(UserGroupController.class);

	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private MessageSource messageSource;

	/**
	 * This method is used to create a module.
	 * 
	 * @param userGroupEntity
	 * @return
	 */
	@PostMapping
	public ResponseEntity<?> create(@RequestBody UserGroupEntity userGroupEntity) {
		LOG.info("create module request received");
		try {
			userGroupService.save(userGroupEntity);
			return ResponseUtil.generateSuccessResponse(null, HttpStatus.CREATED,
					messageSource.getMessage(MessageConstants.MESSAGE_CREATED, null, LocaleContextHolder.getLocale()));
		} catch (ServiceException e) {
			return ResponseUtil.generateSuccessResponse(null, HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	/**
	 * This method is used to update module information
	 * 
	 * @param userGroupEntity
	 * @return
	 */
	@PutMapping
	public ResponseEntity<?> update(@RequestBody UserGroupEntity userGroupEntity) {
		LOG.info("update module request received");
		try {
			userGroupService.update(userGroupEntity);
			return ResponseUtil.generateSuccessResponse(null, HttpStatus.OK,
					messageSource.getMessage(MessageConstants.MESSAGE_UPDATED, null, LocaleContextHolder.getLocale()));
		} catch (ServiceException e) {
			return ResponseUtil.generateSuccessResponse(null, HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		try {
			userGroupService.delete(id);
			return ResponseUtil.generateSuccessResponse(null, HttpStatus.OK,
					messageSource.getMessage(MessageConstants.MESSAGE_DELETED, null, LocaleContextHolder.getLocale()));
		} catch (ServiceException e) {
			return ResponseUtil.generateSuccessResponse(null, HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable("id") Long id) {
		try {
			UserGroupEntity module = userGroupService.getById(id);
			return ResponseUtil.generateSuccessResponse(module, HttpStatus.OK, null);
		} catch (ServiceException e) {
			return ResponseUtil.generateSuccessResponse(null, HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping()
	public ResponseEntity<?> findAll() {
		List<UserGroupEntity> permissions = userGroupService.fetchList();
		return ResponseUtil.generateSuccessResponse(permissions, HttpStatus.OK, null);
	}

}
