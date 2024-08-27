package com.toursandtravel.dto;

import java.util.ArrayList;
import java.util.List;

import com.toursandtravel.entity.Lodging;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Data
public class LodgingResponseDto extends CommonApiResponse {

	private List<Lodging> lodges = new ArrayList<>();

}
