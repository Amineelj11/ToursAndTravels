package com.toursandtravel.dao;

import com.toursandtravel.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface MessageDao extends JpaRepository<Message, Integer> {
}
