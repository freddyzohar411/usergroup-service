package com.avensys.rts.usergroupservice.util;

import com.avensys.rts.usergroupservice.entity.UserGroupEntity;
import com.avensys.rts.usergroupservice.payload.response.UserGroupListingResponseDTO;
import org.springframework.data.domain.Page;
import
        org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



public class ResponseUtil {
    public static ResponseEntity<Object> generateSuccessResponse(Object dataObject, HttpStatus httpStatus, String message) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setData(dataObject);
        httpResponse.setCode(httpStatus.value());
        httpResponse.setMessage(message);
        return new ResponseEntity<>(httpResponse,httpStatus);
    }

    public static ResponseEntity<Object> generateErrorResponse(HttpStatus httpStatus, String message) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setCode(httpStatus.value());
        httpResponse.setError(true);
        httpResponse.setMessage(message);
        return new ResponseEntity<>(httpResponse,httpStatus);
    }

	public static UserGroupListingResponseDTO mapUserGroupEntityPageToUserGroupListingResponse(Page<UserGroupEntity> userGroupEntityPage) {
        UserGroupListingResponseDTO userGroupListingResponseDTO = new UserGroupListingResponseDTO();
        userGroupListingResponseDTO.setTotalPages(userGroupEntityPage.getTotalPages());
        userGroupListingResponseDTO.setTotalElements(userGroupEntityPage.getTotalElements());
        userGroupListingResponseDTO.setPage(userGroupEntityPage.getNumber());
        userGroupListingResponseDTO.setPageSize(userGroupEntityPage.getSize());
        userGroupListingResponseDTO.setUserGroups(userGroupEntityPage.getContent());
        return userGroupListingResponseDTO;
    }



}
