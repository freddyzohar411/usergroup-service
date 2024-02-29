package com.avensys.rts.usergroupservice.payload.requesst;

import java.util.List;

import com.avensys.rts.usergroupservice.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAddUserGroupsRequestDTO {
	private Long userId;
	private List<Long> userGroupIds;
}
