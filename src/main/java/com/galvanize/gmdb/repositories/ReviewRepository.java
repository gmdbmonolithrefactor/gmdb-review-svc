package com.galvanize.gmdb.repositories;

import com.galvanize.gmdb.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByMovieId(Long movieId);
    List<Review> findAllByReviewerIdIn(List<Long> ids);
}
