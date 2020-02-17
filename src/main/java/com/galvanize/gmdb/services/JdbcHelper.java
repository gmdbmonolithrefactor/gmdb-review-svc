package com.galvanize.gmdb.services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JdbcHelper {

    private JdbcTemplate jdbcTemplate;

    public JdbcHelper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Boolean movieExistsById(long movieId) {
        return jdbcTemplate.queryForObject(
                "select exists(select 1 from movies where movie_id = ?)", Boolean.class, movieId);
    }

    public Boolean userExistsById(long userId) {
        return jdbcTemplate.queryForObject(
                "select exists(select 1 from user where id = ?)", Boolean.class, userId);
    }

    public Long findMovieIdFromImdbId(String imdbId){
        return jdbcTemplate.queryForObject(
                "select movie_id from movies where imdbid = ?", Long.class, imdbId);
    }

    /**
     * Returns the IDs of reviewers/users with a given screen name
     * @param screenName Screen name you are looking for
     * @return list of ids that have @screenName
     */
    public List<Long> findUserIdsFromUserScreenName(String screenName){
        return jdbcTemplate.queryForList(
                "select id from user where screen_name = ?", Long.class, screenName);
    }
}
