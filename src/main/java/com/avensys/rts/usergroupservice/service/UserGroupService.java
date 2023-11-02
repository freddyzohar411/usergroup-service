package com.avensys.rts.usergroupservice.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.avensys.rts.usergroupservice.entity.RoleEntity;
import com.avensys.rts.usergroupservice.entity.UserEntity;
import com.avensys.rts.usergroupservice.entity.UserGroupEntity;
import com.avensys.rts.usergroupservice.exception.ServiceException;
import com.avensys.rts.usergroupservice.payload.requesst.UserGroupRequestDTO;
import com.avensys.rts.usergroupservice.repository.RoleRepository;
import com.avensys.rts.usergroupservice.repository.UserGroupRepository;
import com.avensys.rts.usergroupservice.repository.UserRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class UserGroupService {

	@Autowired
	private UserGroupRepository userGroupRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private MessageSource messageSource;

	private UserGroupEntity mapRequestToEntity(UserGroupRequestDTO userGroupRequestDTO) {
		UserGroupEntity entity = new UserGroupEntity();
		entity.setUserGroupName(userGroupRequestDTO.getUserGroupName());
		entity.setUserGroupDescription(userGroupRequestDTO.getDescription());
		return entity;
	}

	public void save(UserGroupRequestDTO userGroupRequestDTO) throws ServiceException {

		// add check for name exists in a DB
		if (userGroupRepository.existsByUserGroupName(userGroupRequestDTO.getUserGroupName())) {
			throw new ServiceException(
					messageSource.getMessage("error.groupnametaken", null, LocaleContextHolder.getLocale()));
		}

		UserGroupEntity userGroupEntity = mapRequestToEntity(userGroupRequestDTO);
		Set<UserEntity> users = new HashSet<UserEntity>();
		Set<RoleEntity> roles = new HashSet<RoleEntity>();

		if (userGroupRequestDTO.getUsers() != null && userGroupRequestDTO.getUsers().size() > 0) {
			userGroupRequestDTO.getUsers().forEach(id -> {
				Optional<UserEntity> userEntity = userRepository.findById(id);
				if (userEntity.isPresent()) {
					users.add(userEntity.get());
				}
			});
		}

		if (userGroupRequestDTO.getRoles() != null && userGroupRequestDTO.getRoles().size() > 0) {
			userGroupRequestDTO.getRoles().forEach(id -> {
				Optional<RoleEntity> roleEntity = roleRepository.findById(id);
				if (roleEntity.isPresent()) {
					roles.add(roleEntity.get());
				}
			});
		}

		userGroupEntity.setUsers(users);
		userGroupEntity.setRoleEntities(roles);

		userGroupRepository.save(userGroupEntity);
	}

	public void update(UserGroupRequestDTO userGroupRequestDTO) throws ServiceException {

		Optional<UserGroupEntity> dbUser = userGroupRepository
				.findByUserGroupName(userGroupRequestDTO.getUserGroupName());

		// add check for groupname exists in a DB
		if (dbUser.isPresent() && dbUser.get().getId() != userGroupRequestDTO.getId()) {
			throw new ServiceException(
					messageSource.getMessage("error.groupnametaken", null, LocaleContextHolder.getLocale()));
		}

		getById(userGroupRequestDTO.getId());

		UserGroupEntity userGroupEntity = mapRequestToEntity(userGroupRequestDTO);
		Set<UserEntity> users = new HashSet<UserEntity>();
		Set<RoleEntity> roles = new HashSet<RoleEntity>();

		if (userGroupRequestDTO.getUsers() != null && userGroupRequestDTO.getUsers().size() > 0) {
			userGroupRequestDTO.getUsers().forEach(id -> {
				Optional<UserEntity> userEntity = userRepository.findById(id);
				if (userEntity.isPresent()) {
					users.add(userEntity.get());
				}
			});
		}

		if (userGroupRequestDTO.getRoles() != null && userGroupRequestDTO.getRoles().size() > 0) {
			userGroupRequestDTO.getRoles().forEach(id -> {
				Optional<RoleEntity> roleEntity = roleRepository.findById(id);
				if (roleEntity.isPresent()) {
					roles.add(roleEntity.get());
				}
			});
		}

		userGroupEntity.setUsers(users);
		userGroupEntity.setRoleEntities(roles);

		userGroupRepository.save(userGroupEntity);
	}

	public void delete(Long id) throws ServiceException {
		UserGroupEntity dbPermission = getById(id);
		dbPermission.setIsDeleted(true);
		userGroupRepository.save(dbPermission);
	}

	public UserGroupEntity getById(Long id) throws ServiceException {
		if (id == null) {
			throw new ServiceException(
					messageSource.getMessage("error.provide.id", new Object[] { id }, LocaleContextHolder.getLocale()));
		}

		Optional<UserGroupEntity> permission = userGroupRepository.findById(id);
		if (permission.isPresent() && !permission.get().getIsDeleted()) {
			return permission.get();
		} else {
			throw new ServiceException(messageSource.getMessage("error.groupnotfound", new Object[] { id },
					LocaleContextHolder.getLocale()));
		}
	}

	public List<UserGroupEntity> fetchList() {
		return (List<UserGroupEntity>) userGroupRepository.findAllAndIsDeleted(false);
	}

}
