package com.avensys.rts.usergroupservice.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.avensys.rts.usergroupservice.entity.RoleEntity;
import com.avensys.rts.usergroupservice.entity.UserEntity;
import com.avensys.rts.usergroupservice.entity.UserGroupEntity;
import com.avensys.rts.usergroupservice.exception.ServiceException;
import com.avensys.rts.usergroupservice.payload.requesst.UserGroupRequestDTO;
import com.avensys.rts.usergroupservice.repository.RoleRepository;
import com.avensys.rts.usergroupservice.repository.UserGroupRepository;
import com.avensys.rts.usergroupservice.repository.UserRepository;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
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
		if (userGroupRequestDTO.getId() != null) {
			entity.setId(userGroupRequestDTO.getId());
		}
		entity.setUserGroupName(userGroupRequestDTO.getUserGroupName());
		entity.setUserGroupDescription(userGroupRequestDTO.getDescription());
		entity.setIsActive(true);
		entity.setIsDeleted(false);
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
		userGroupEntity.setCreatedBy(userGroupRequestDTO.getCreatedBy());
		userGroupEntity.setUpdatedBy(userGroupRequestDTO.getUpdatedBy());
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

		UserGroupEntity userGroupEntity = getById(userGroupRequestDTO.getId());
		userGroupEntity.setUserGroupName(userGroupRequestDTO.getUserGroupName());
		userGroupEntity.setUserGroupDescription(userGroupRequestDTO.getDescription());

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
		userGroupEntity.setUpdatedBy(userGroupRequestDTO.getUpdatedBy());
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

	public Page<UserGroupEntity> getUserGroupListingPage(Integer page, Integer size, String sortBy,
			String sortDirection) {
		Sort sort = null;
		if (sortBy != null) {
			// Get direction based on sort direction
			Sort.Direction direction = Sort.DEFAULT_DIRECTION;
			if (sortDirection != null) {
				direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
			}
			sort = Sort.by(direction, sortBy);
		} else {
			sort = Sort.by(Sort.Direction.DESC, "updatedAt");
		}
		Pageable pageable = null;
		if (page == null && size == null) {
			pageable = PageRequest.of(0, Integer.MAX_VALUE, sort);
		} else {
			pageable = PageRequest.of(page, size, sort);
		}
		Page<UserGroupEntity> usersPage = userGroupRepository.findAllByPaginationAndSort(false, true, pageable);
		return usersPage;
	}

	public Page<UserGroupEntity> getUserGroupListingPageWithSearch(Integer page, Integer size, String sortBy,
			String sortDirection, String searchTerm) {
		Sort sort = null;
		if (sortBy != null) {
			// Get direction based on sort direction
			Sort.Direction direction = Sort.DEFAULT_DIRECTION;
			if (sortDirection != null) {
				direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
			}
			sort = Sort.by(direction, sortBy);
		} else {
			sort = Sort.by(Sort.Direction.DESC, "updatedAt");
		}

		Pageable pageable = null;
		if (page == null && size == null) {
			pageable = PageRequest.of(0, Integer.MAX_VALUE, sort);
		} else {
			pageable = PageRequest.of(page, size, sort);
		}
		// Dynamic search based on custom view (future feature)
		List<String> customView = List.of("userGroupName", "userGroupDescription");
		Page<UserGroupEntity> usersPage = userGroupRepository
				.findAll(getSpecification(searchTerm, customView, false, true), pageable);
		return usersPage;
	}

	private Specification<UserGroupEntity> getSpecification(String searchTerm, List<String> customView,
			Boolean isDeleted, Boolean isActive) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			// Custom fields you want to search in
			for (String field : customView) {
				Path<Object> fieldPath = root.get(field);
				if (fieldPath.getJavaType() == Integer.class) {
					try {
						Integer id = Integer.parseInt(searchTerm);
						predicates.add(criteriaBuilder.equal(fieldPath, id));
					} catch (NumberFormatException e) {
						// Ignore if it's not a valid integer
					}
				} else {
					predicates.add(criteriaBuilder.like(criteriaBuilder.lower(fieldPath.as(String.class)),
							"%" + searchTerm.toLowerCase() + "%"));
				}
			}

			Predicate searchOrPredicates = criteriaBuilder.or(predicates.toArray(new Predicate[0]));

			List<Predicate> fixPredicates = new ArrayList<>();
			// Add conditions for isDeleted and isActive
			fixPredicates.add(criteriaBuilder.equal(root.get("isDeleted"), isDeleted));
			fixPredicates.add(criteriaBuilder.equal(root.get("isActive"), isActive));

			// Combine all predicates with AND
			Predicate finalPredicate = criteriaBuilder.and(searchOrPredicates,
					criteriaBuilder.and(fixPredicates.toArray(new Predicate[0])));

			return finalPredicate;
		};
	}
}
