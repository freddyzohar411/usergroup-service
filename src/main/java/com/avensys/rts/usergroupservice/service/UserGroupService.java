package com.avensys.rts.usergroupservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.avensys.rts.usergroupservice.entity.UserGroupEntity;
import com.avensys.rts.usergroupservice.exception.ServiceException;
import com.avensys.rts.usergroupservice.repository.UserGroupRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class UserGroupService {

	@Autowired
	private UserGroupRepository userGroupRepository;

	@Autowired
	private MessageSource messageSource;

	public void save(UserGroupEntity userGroupEntity) throws ServiceException {

		// add check for name exists in a DB
		if (userGroupRepository.existsByUserGroupName(userGroupEntity.getUserGroupName())) {
			throw new ServiceException(
					messageSource.getMessage("error.modulenametaken", null, LocaleContextHolder.getLocale()));
		}

		userGroupRepository.save(userGroupEntity);
	}

	public void update(UserGroupEntity userGroupEntity) throws ServiceException {
		getById(userGroupEntity.getId());

		// add check for name exists in a DB
		if (userGroupRepository.existsByUserGroupName(userGroupEntity.getUserGroupName())) {
			throw new ServiceException(
					messageSource.getMessage("error.modulenametaken", null, LocaleContextHolder.getLocale()));
		}

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
			throw new ServiceException(messageSource.getMessage("error.modulenotfound", new Object[] { id },
					LocaleContextHolder.getLocale()));
		}
	}

	public List<UserGroupEntity> fetchList() {
		return (List<UserGroupEntity>) userGroupRepository.findAll();
	}

}
