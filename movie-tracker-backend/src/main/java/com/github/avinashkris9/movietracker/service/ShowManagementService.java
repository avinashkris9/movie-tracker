package com.github.avinashkris9.movietracker.service;

import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ShowManagementService<T,S> {


 T addShowWatched(T t);
T updateShowWatched(T t, long id);
T getShowByShowId(long id);
List<T> getShowByShowName(String name);
S getAllShowsWatched(Pageable pageRequest);
void deleteShowWatched(long id);

}
