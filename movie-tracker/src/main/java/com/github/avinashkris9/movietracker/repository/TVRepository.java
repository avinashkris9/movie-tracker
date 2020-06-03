package com.github.avinashkris9.movietracker.repository;

import com.github.avinashkris9.movietracker.entity.TVShow;
import org.springframework.data.repository.CrudRepository;

public interface TVRepository extends CrudRepository<TVShow, Long> {

}
