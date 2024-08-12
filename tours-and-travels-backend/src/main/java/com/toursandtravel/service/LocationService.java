package com.toursandtravel.service;

import java.util.List;

import com.toursandtravel.entity.Location;

public interface LocationService {

	Location add(Location location);

	Location update(Location location);

	Location getById(int id);

	List<Location> getAllByStatus(String status);

}
