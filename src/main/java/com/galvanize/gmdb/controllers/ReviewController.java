package com.galvanize.gmdb.controllers;

import com.galvanize.gmdb.entities.Review;
import com.galvanize.gmdb.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/gmdb/api/reviews")
public class ReviewController {

    ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Review> getAllReviews(){
        return reviewService.findAll();
    }

    @GetMapping("{reviewId}")
    public Review getByReviewId(@PathVariable long reviewId){
        return reviewService.findById(reviewId);
    }

    @PostMapping
    public ResponseEntity<Review> createNewReview(@RequestBody Review review){
        Review newReview;
        try {
            newReview = reviewService.createNew(review);
        }catch (RuntimeException re){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(newReview);
    }

    @GetMapping("imdb/{imdbid}")
    public List<Review> getByImdbId(@PathVariable String imdbid){
        return reviewService.findByImdbId(imdbid);
    }
}
