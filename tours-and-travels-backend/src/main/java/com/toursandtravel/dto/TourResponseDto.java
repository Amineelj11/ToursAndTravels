package com.toursandtravel.dto;

import java.util.ArrayList;
import java.util.List;

import com.toursandtravel.entity.Tour;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Data
public class TourResponseDto extends CommonApiResponse {
	
	private List<Tour> tours = new ArrayList<>();

}
