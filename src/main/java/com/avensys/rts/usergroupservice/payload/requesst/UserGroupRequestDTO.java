package com.avensys.rts.usergroupservice.payload.requesst;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupRequestDTO {

	private Long id;
	private String userGroupName;
	private String description;
	private List<Long> users;
	private List<Long> roles;
}
