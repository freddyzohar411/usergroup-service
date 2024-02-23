package com.avensys.rts.usergroupservice.payload.requesst;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupListingRequestDTO {
	private Integer page = 0;
	private Integer pageSize = 5;
	private String sortBy;
	private String sortDirection;
	private String searchTerm;
}
