package com.github.avinashkris9.movietracker.repository;

import com.github.avinashkris9.movietracker.entity.WatchList;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchListRepository extends CrudRepository<WatchList,Long> {

  List<WatchList> findAll();

  WatchList findByNameAndShowType(String movieName,String showType);
  List<WatchList>findByShowType(String showType);
  WatchList findByName(String movieName);

}
