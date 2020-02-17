package com.galvanize.gmdb.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "review")
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date lastUpdated;
    @Column(name = "review_text", columnDefinition = "longtext")
    private String reviewText;
    private String reviewTitle;
    private Long reviewerId;
    private Long movieId;

    public Review() {
    }

    public Review(Long movieId, String reviewTitle, String reviewText, Long reviewerId) {
        this.setMovieId(movieId);
        this.setReviewTitle(reviewTitle);
        this.setReviewText(reviewText);
        this.setReviewerId(reviewerId);
    }

    public Long getId() {
        return id;
    }

    /* not needed yet */
//    public void setId(Long id) {
//        this.id = id;
//    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    /* not needed yet */
//    public void setLastUpdated(Date lastUpdated) {
//        this.lastUpdated = lastUpdated;
//    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", lastUpdated=" + lastUpdated +
                ", reviewText='" + reviewText + '\'' +
                ", reviewTitle='" + reviewTitle + '\'' +
                ", reviewerId=" + reviewerId +
                ", movieId=" + movieId +
                '}';
    }
}
