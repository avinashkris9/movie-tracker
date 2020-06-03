package com.github.avinashkris9.movietracker.entity;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TVSHOWS")
public class TVShow {

    @Id
    private String id;

    private String tvShowName;

    private String platform;
    private LocalDate lastWatched;
    private boolean isCompleted;
    private String review;
    private int rating;


    public TVShow() {
    }


    public TVShow(String id, String tvShowName, String platform, LocalDate lastWatched,
        boolean isCompleted,
        String review, int rating) {
        this.id = id;
        this.tvShowName = tvShowName;
        this.platform = platform;
        this.lastWatched = lastWatched;
        this.isCompleted = isCompleted;
        this.review = review;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTvShowName() {
        return tvShowName;
    }

    public void setTvShowName(String tvShowName) {
        this.tvShowName = tvShowName;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public LocalDate getLastWatched() {
        return lastWatched;
    }

    public void setLastWatched(LocalDate lastWatched) {
        this.lastWatched = lastWatched;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
