package com.toursandtravel.dao;

import com.toursandtravel.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeDao extends JpaRepository<Type, Integer> {

    List<Type> findByStatus(String status);

}