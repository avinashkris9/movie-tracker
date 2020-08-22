package com.github.avinashkris9.movietracker.repository;

import com.github.avinashkris9.movietracker.entity.MovieReview;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieReviewRepository extends CrudRepository<MovieReview,Long> {


  List<MovieReview> findByMovieDetailsId(Long movieDetailsId);
  Optional<MovieReview> findByReviewIdAndMovieDetailsId(Long id ,Long movieDetailsId);
}
