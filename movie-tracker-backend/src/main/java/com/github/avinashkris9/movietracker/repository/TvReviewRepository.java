package com.github.avinashkris9.movietracker.repository;

import com.github.avinashkris9.movietracker.entity.MovieReview;
import com.github.avinashkris9.movietracker.entity.TvReview;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TvReviewRepository extends CrudRepository<TvReview,Long> {


  List<TvReview> findByTvDetailsId(Long tvDetailsId);
  Optional<TvReview> findByReviewIdAndTvDetailsId(Long id ,Long tvDetailsId);
}
