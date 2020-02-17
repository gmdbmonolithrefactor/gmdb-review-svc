package com.galvanize.gmdb.services;

import com.galvanize.gmdb.entities.Review;
import com.galvanize.gmdb.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    ReviewRepository reviewRepository;
    JdbcHelper jdbcHelper;

    public ReviewService(ReviewRepository reviewRepository, JdbcHelper jdbcHelper) {
        this.reviewRepository = reviewRepository;
        this.jdbcHelper = jdbcHelper;
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Review findById(Long id) {
        return reviewRepository.findById(id).get();
    }

    public Review createNew(Review review) {
        if (jdbcHelper.movieExistsById(review.getMovieId()) && jdbcHelper.userExistsById(review.getReviewerId())){
            return reviewRepository.save(review);
        }else{
            throw new RuntimeException(String.format("User ID %s and/or Movie ID %s does not exist",
                                                     review.getReviewerId(), review.getMovieId()));
        }
    }

    public List<Review> findByImdbId(String imdbId) {
        Long movieId = jdbcHelper.findMovieIdFromImdbId(imdbId);
        return reviewRepository.findAllByMovieId(movieId);
    }


    public List<Review> findByReviewerScreenName(String screenName) {
        List<Long> userIds = jdbcHelper.findUserIdsFromUserScreenName(screenName);
        if (userIds.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        return reviewRepository.findAllByReviewerIdIn(userIds);
    }
}
