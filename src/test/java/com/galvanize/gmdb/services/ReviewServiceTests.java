package com.galvanize.gmdb.services;

import com.galvanize.gmdb.entities.Review;
import com.galvanize.gmdb.repositories.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:test.properties")
public class ReviewServiceTests {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    private List<Review> testReviews;

    @MockBean
    JdbcHelper jdbcHelper;

    @BeforeEach
    void setUp() {
        testReviews = new ArrayList<>();
        for (long i = 40; i < 50; i++) {
            testReviews.add(new Review(i, "Review Title number "+i, "ReviewBody", i));
        }
        reviewRepository.saveAll(testReviews);

    }

    @Test
    void getAllReviews() {
        List<Review> reviews = reviewService.findAll();

        reviews.forEach(System.out::println);

        assertEquals(testReviews, reviews);
    }

    @Test
    void findOne() {
        Review expected = testReviews.get(7);

        Review actual = reviewService.findById(expected.getId());

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void createReviewUserExists() {
        //(Long movieId, String reviewTitle, String reviewText, Long reviewerId)
        Review expected = new Review(33L, "NEW REVIEW", "NEW DETAILS", 77L);
        Mockito.when(jdbcHelper.userExistsById(77L)).thenReturn(true);
        Mockito.when(jdbcHelper.movieExistsById(33L)).thenReturn(true);
        Review actual = reviewService.createNew(expected);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }
    @Test()
    void createReviewUserNotExists() {
        //(Long movieId, String reviewTitle, String reviewText, Long reviewerId)
        Review expected = new Review(33L, "NEW REVIEW", "NEW DETAILS", 77L);
        Mockito.when(jdbcHelper.userExistsById(77L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> reviewService.createNew(expected));
    }

    @Test
    void findByImdbId() {
        Review expected = testReviews.get(3);
        String imdbId = "a111aa";

        Mockito.when(jdbcHelper.findMovieIdFromImdbId(imdbId)).thenReturn(expected.getMovieId());
        List<Review> actual = reviewService.findByImdbId(imdbId);

        assertFalse(actual.isEmpty());
        assertEquals(expected, actual.get(0));
    }

    @Test
    void findByUserScreenName() {
        List<Review> expected = Arrays.asList(testReviews.get(1), testReviews.get(4));
        String screenName = "screenName";
        List<Long> userIds = new ArrayList<>(Arrays.asList(expected.get(0).getReviewerId(), expected.get(1).getReviewerId()));

        Mockito.when(jdbcHelper.findUserIdsFromUserScreenName(screenName)).thenReturn(userIds);

        List<Review> actuals = reviewService.findByReviewerScreenName(screenName);

        assertFalse(actuals.isEmpty());
        assertEquals(expected, actuals);

    }

    @Test
    void findByUserScreenNameNotExists() {
        String screenName = "screenName";

        Mockito.when(jdbcHelper.findUserIdsFromUserScreenName(screenName)).thenReturn(Collections.emptyList());

        List<Review> actuals = reviewService.findByReviewerScreenName(screenName);

        assertTrue(actuals.isEmpty());

    }
}
