package com.toursandtravel.service;

import java.util.List;

import com.toursandtravel.entity.Transport;

public interface TransportService {

	Transport add(Transport transport);

	Transport update(Transport transport);

	Transport getById(int id);

	List<Transport> getAllByStatus(String status);
}
