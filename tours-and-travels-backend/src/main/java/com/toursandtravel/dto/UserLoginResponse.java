package com.toursandtravel.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)

@Data

public class UserLoginResponse extends CommonApiResponse {

	private UserDto user;
	
	private String jwtToken;

}
