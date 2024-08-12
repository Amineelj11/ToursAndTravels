package com.toursandtravel.service;
import com.toursandtravel.dao.FeedbackDao;
import com.toursandtravel.entity.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackDao feedbackRepository;

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public Feedback getFeedbackById(int id) {
        return feedbackRepository.findById(id).orElse(null);
    }

    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public void deleteFeedback(int id) {
        feedbackRepository.deleteById(id);
    }
}
