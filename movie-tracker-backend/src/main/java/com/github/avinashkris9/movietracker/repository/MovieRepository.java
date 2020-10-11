package com.github.avinashkris9.movietracker.repository;

import com.github.avinashkris9.movietracker.entity.MovieDetails;

import com.github.avinashkris9.movietracker.entity.TvDetails;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<MovieDetails, Long> {

  MovieDetails findByMovieName(String movieName);

  Page<MovieDetails> findAll(Pageable pageable);

  List<MovieDetails> findByMovieNameContainsIgnoreCase(String movieName);

  long countByRating(int rating);

  @Query("Select month(md.lastWatched),count(*) from MovieDetails md group by month( md.lastWatched) ")
  List<Object[]> monthlyCount();
}
