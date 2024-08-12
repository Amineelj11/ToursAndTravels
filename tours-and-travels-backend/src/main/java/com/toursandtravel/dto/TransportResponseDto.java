package com.toursandtravel.dto;

import java.util.ArrayList;
import java.util.List;

import com.toursandtravel.entity.Transport;

import lombok.Data;

@Data
public class TransportResponseDto extends CommonApiResponse {

	private List<Transport> transports = new ArrayList<>();

}
