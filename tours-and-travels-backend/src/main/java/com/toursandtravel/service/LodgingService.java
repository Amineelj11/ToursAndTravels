package com.toursandtravel.service;

import java.util.List;

import com.toursandtravel.entity.Lodging;

public interface LodgingService {

	Lodging add(Lodging lodging);

	Lodging update(Lodging lodging);

	Lodging getById(int id);

	List<Lodging> getAllByStatus(String status);
	
}
