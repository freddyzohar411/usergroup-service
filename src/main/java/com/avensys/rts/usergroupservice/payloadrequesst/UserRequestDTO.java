package com.avensys.rts.usergroupservice.payloadrequesst;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    private String username;
    private Integer id;
   // private LocalDateTime lastLoginDatetime;
   // private Integer contactId;
   // private Integer entityId;
}
