package com.toursandtravel.dao;

import com.toursandtravel.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackDao extends JpaRepository<Feedback, Integer> {
}
