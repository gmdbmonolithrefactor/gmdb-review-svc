package com.galvanize.gmdb.controllers;

import com.galvanize.gmdb.entities.Review;
import com.galvanize.gmdb.repositories.ReviewRepository;
import com.galvanize.gmdb.services.JdbcHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest @Transactional
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:test.properties")
class ReviewControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ReviewRepository reviewRepository;

    @MockBean
    JdbcHelper jdbcHelper;

    List<Review> testReviews;

    @BeforeEach
    void setUp() {
        testReviews = new ArrayList<>();
        for (long i = 40; i < 50; i++) {
            testReviews.add(new Review(i, "Review Title number "+i, "ReviewBody", 1L));
        }
        reviewRepository.saveAll(testReviews);

    }

    @Test
    void findAll() throws Exception {
        mvc.perform(get("/gmdb/api/reviews"))
                .andExpect(status().isOk());
    }

    @Test
    void findOne() throws Exception {
        mvc.perform(get("/gmdb/api/reviews/"+testReviews.get(5).getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviewTitle", is(testReviews.get(5).getReviewTitle())));
    }

    @Test
    void postReviewMovieExists() throws Exception {
        String json = "{ \"movieId\": 22, " +
                        "\"reviewerId\": 22, " +
                        "\"reviewTitle\": \"This is a title\" }";

        Mockito.when(jdbcHelper.movieExistsById(22L)).thenReturn(true);
        Mockito.when(jdbcHelper.userExistsById(22L)).thenReturn(true);

        mvc.perform(post("/gmdb/api/reviews").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())));

    }

    @Test
    void postReviewMovieNotExists() throws Exception {
        String json = "{ \"movieId\": 22, " +
                "\"reviewerId\": 22, " +
                "\"reviewTitle\": \"This is a title\" }";

        Mockito.when(jdbcHelper.movieExistsById(22L)).thenReturn(false);
        Mockito.when(jdbcHelper.userExistsById(22L)).thenReturn(true);

        mvc.perform(post("/gmdb/api/reviews").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is(400));

    }

    @Test
    void getReviewsByImdbId() throws Exception {
        String imdbid = "aa111bb";
        Mockito.when(jdbcHelper.findMovieIdFromImdbId(imdbid)).thenReturn(testReviews.get(5).getMovieId());

        mvc.perform(get("/gmdb/api/reviews/imdb/"+imdbid))
                .andDo(print())
                .andExpect(status().isOk());
    }
}