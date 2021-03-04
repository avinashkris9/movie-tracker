package com.github.avinashkris9.movietracker.service;

import java.util.List;


public interface ReviewManagementService <T,S> {


  List<T>getAllReviewsByShowId(long id);
  T addReview(long id, T t);
  T updateReview(long showId,long reviewId, T t);
 void deleteReviewById(long reviewId);

}
