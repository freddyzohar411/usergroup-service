package com.avensys.rts.usergroupservice.payload.response;

import java.util.List;

import com.avensys.rts.usergroupservice.entity.UserGroupEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupListingResponseDTO {
	private Integer totalPages;
	private Long totalElements;
	private Integer page;
	private Integer pageSize;
	private List<UserGroupEntity> userGroups;
}
