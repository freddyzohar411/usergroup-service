package com.avensys.rts.usergroupservice.payloadrequesst;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupRequestDTO {
    @NotEmpty(message = "userGroupName cannot be empty")
    private String userGroupName;
    @NotEmpty(message = "description cannot be empty")
    private String description;
    List<UserDTO> userDTOList;
}
