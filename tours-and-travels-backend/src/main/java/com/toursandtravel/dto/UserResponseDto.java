package com.toursandtravel.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Data
public class UserResponseDto extends CommonApiResponse {
	
	private List<UserDto> users = new ArrayList<>();

}
