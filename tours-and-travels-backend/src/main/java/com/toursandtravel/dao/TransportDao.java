package com.toursandtravel.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toursandtravel.entity.Transport;

@Repository
public interface TransportDao extends JpaRepository<Transport, Integer> {

	List<Transport> findByStatus(String status);

}
