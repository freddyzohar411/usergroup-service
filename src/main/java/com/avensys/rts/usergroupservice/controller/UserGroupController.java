package com.avensys.rts.usergroupservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avensys.rts.usergroupservice.constant.MessageConstants;
import com.avensys.rts.usergroupservice.entity.UserGroupEntity;
import com.avensys.rts.usergroupservice.exception.ServiceException;
import com.avensys.rts.usergroupservice.payload.requesst.UserGroupListingRequestDTO;
import com.avensys.rts.usergroupservice.payload.requesst.UserGroupRequestDTO;
import com.avensys.rts.usergroupservice.service.UserGroupService;
import com.avensys.rts.usergroupservice.util.JwtUtil;
import com.avensys.rts.usergroupservice.util.ResponseUtil;

@CrossOrigin
@RestController
@RequestMapping("/api/usergroup")
public class UserGroupController {

	private static final Logger LOG = LoggerFactory.getLogger(UserGroupController.class);

	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * This method is used to create a module.
	 * 
	 * @param userGroupRequestDTO
	 * @return
	 */
	@PostMapping("/add")
	public ResponseEntity<?> create(@RequestBody UserGroupRequestDTO userGroupRequestDTO,
			@RequestHeader(name = "Authorization") String token) {
		LOG.info("create module request received");
		try {
			Long userId = jwtUtil.getUserId(token);
			userGroupRequestDTO.setCreatedBy(userId);
			userGroupRequestDTO.setUpdatedBy(userId);
			userGroupService.save(userGroupRequestDTO);
			return ResponseUtil.generateSuccessResponse(null, HttpStatus.CREATED,
					messageSource.getMessage(MessageConstants.MESSAGE_CREATED, null, LocaleContextHolder.getLocale()));
		} catch (ServiceException e) {
			return ResponseUtil.generateSuccessResponse(null, HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	/**
	 * This method is used to update module information
	 * 
	 * @param userGroupRequestDTO
	 * @return
	 */
	@PutMapping("/edit")
	public ResponseEntity<?> update(@RequestBody UserGroupRequestDTO userGroupRequestDTO,
			@RequestHeader(name = "Authorization") String token) {
		LOG.info("update module request received");
		try {
			Long userId = jwtUtil.getUserId(token);
			userGroupRequestDTO.setUpdatedBy(userId);
			userGroupService.update(userGroupRequestDTO);
			return ResponseUtil.generateSuccessResponse(null, HttpStatus.OK,
					messageSource.getMessage(MessageConstants.MESSAGE_UPDATED, null, LocaleContextHolder.getLocale()));
		} catch (ServiceException e) {
			return ResponseUtil.generateSuccessResponse(null, HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@DeleteMapping("/{id}/delete")
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

	@GetMapping("/name/{name}")
	public ResponseEntity<?> findByName(@PathVariable("name") String name) {
		try {
			UserGroupEntity module = userGroupService.getByName(name);
			return ResponseUtil.generateSuccessResponse(module, HttpStatus.OK, null);
		} catch (ServiceException e) {
			return ResponseUtil.generateSuccessResponse(null, HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<?> findAll() {
		List<UserGroupEntity> permissions = userGroupService.fetchList();
		return ResponseUtil.generateSuccessResponse(permissions, HttpStatus.OK, null);
	}

	@PostMapping("listing")
	public ResponseEntity<Object> getUserGroupListing(
			@RequestBody UserGroupListingRequestDTO userGroupListingRequestDTO) {
		Integer page = userGroupListingRequestDTO.getPage();
		Integer pageSize = userGroupListingRequestDTO.getPageSize();
		String sortBy = userGroupListingRequestDTO.getSortBy();
		String sortDirection = userGroupListingRequestDTO.getSortDirection();
		String searchTerm = userGroupListingRequestDTO.getSearchTerm();
		if (searchTerm == null || searchTerm.isEmpty()) {
			return ResponseUtil.generateSuccessResponse(
					ResponseUtil.mapUserGroupEntityPageToUserGroupListingResponse(
							userGroupService.getUserGroupListingPage(page, pageSize, sortBy, sortDirection)),
					HttpStatus.OK,
					messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
		}
		return ResponseUtil.generateSuccessResponse(
				ResponseUtil.mapUserGroupEntityPageToUserGroupListingResponse(userGroupService
						.getUserGroupListingPageWithSearch(page, pageSize, sortBy, sortDirection, searchTerm)),
				HttpStatus.OK,
				messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
	}
}
