package com.toursandtravel.dto;

import java.util.ArrayList;
import java.util.List;

import com.toursandtravel.entity.Location;

import lombok.Data;

@Data
public class LocationResponseDto extends CommonApiResponse {

	private List<Location> locations = new ArrayList<>();
	
}
