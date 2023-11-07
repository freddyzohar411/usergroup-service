package com.avensys.rts.usergroupservice.payload.response;

import com.avensys.rts.usergroupservice.entity.UserGroupEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupListingResponseDTO {
	private Integer totalPages;
	private Long totalElements;
	private Integer page;
	private Integer pageSize;
	private List<UserGroupEntity> userGroups;
}
