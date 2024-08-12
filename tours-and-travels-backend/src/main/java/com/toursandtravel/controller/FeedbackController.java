package com.toursandtravel.controller;

import com.toursandtravel.entity.Feedback;
import com.toursandtravel.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/feedbacks")
@CrossOrigin(origins = "http://localhost:3000")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/all")
    public List<Feedback> getAllFeedbacks() {
        return feedbackService.getAllFeedbacks();
    }

    @GetMapping("/{id}")
    public Feedback getFeedbackById(@PathVariable int id) {
        return feedbackService.getFeedbackById(id);
    }

    @PostMapping("/add")
    public Feedback saveFeedback(@RequestBody Feedback feedback) {
        return feedbackService.saveFeedback(feedback);
    }

    @DeleteMapping("/{id}")
    public void deleteFeedback(@PathVariable int id) {
        feedbackService.deleteFeedback(id);
    }
}
