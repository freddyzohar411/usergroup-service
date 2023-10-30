package com.avensys.rts.usergroupservice.payload.requesst;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupRequestDTO {

	@NotEmpty(message = "User group name cannot be empty")
	private String userGroupName;
	@NotEmpty(message = "Description cannot be empty")
	private String description;

	private List<Long> users;
}
