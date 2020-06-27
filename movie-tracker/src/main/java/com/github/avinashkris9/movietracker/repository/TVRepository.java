package com.github.avinashkris9.movietracker.repository;

import com.github.avinashkris9.movietracker.entity.TvDetails;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TvRepository extends JpaRepository<TvDetails, Long> {


  TvDetails findByTvShowName(String tvShowName);

  Page<TvDetails> findAll(Pageable pageable);

  List<TvDetails> findByTvShowNameContainsIgnoreCase(String tvShowName);
}
