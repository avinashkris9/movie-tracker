package com.github.avinashkris9.movietracker.service;

import com.github.avinashkris9.movietracker.entity.TvReview;
import com.github.avinashkris9.movietracker.exception.NotFoundException;
import com.github.avinashkris9.movietracker.model.MovieReviewResponse;
import com.github.avinashkris9.movietracker.repository.TvRepository;
import com.github.avinashkris9.movietracker.repository.TvReviewRepository;
import com.github.avinashkris9.movietracker.utils.CustomModelMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TvReviewService {

  private final TvReviewRepository tvReviewRepository;
  private final TvRepository tvRepository;
  private final CustomModelMapper customModelMapper;




  public List<MovieReviewResponse> getAllTvReviewsByTvId(long tvDetailsId)
  {
    List<TvReview> lists= tvReviewRepository.findByTvDetailsId(tvDetailsId);


    return lists.stream().map(customModelMapper::tvReviewEntity2MoviewReviewDTO).collect(
        Collectors.toList());
  }

  public MovieReviewResponse addNewTvReview(long tvDetailsId, MovieReviewResponse movieReviewResponse)
  {
    TvReview tvReview=customModelMapper.movieReviewDTO2TvReviewEntity(movieReviewResponse);
    tvReview.setLastReviewed(LocalDate.now());
    return  tvRepository.findById(tvDetailsId).map(

        movie ->
        {
          //map the movie data.
          tvReview.setTvDetails(movie);
          return customModelMapper.tvReviewEntity2MoviewReviewDTO(tvReviewRepository.save(tvReview));
        }
    ).orElseThrow(() -> new NotFoundException("Movie Not Found"));
  }

  public MovieReviewResponse updateTvReview(long tvDetailsId, long reviewId,
      MovieReviewResponse movieReviewResponse)
  {
    TvReview tvReview=customModelMapper.movieReviewDTO2TvReviewEntity(movieReviewResponse);
    if(!tvRepository.existsById(tvDetailsId))
    {
      throw  new NotFoundException("Movie Not Found to add review");
    }

    return tvReviewRepository.findById(reviewId).map(

        review ->
        {
          review.setLastReviewed(LocalDate.now());
          review.setReview(tvReview.getReview());
          return customModelMapper.tvReviewEntity2MoviewReviewDTO(tvReviewRepository.save(review));
        }

    ).orElseThrow(() -> new NotFoundException("Review Not Found"));
  }

  public void deleteReviewById(long tvDetailsId, long reviewId) {

    tvReviewRepository
        .findByReviewIdAndTvDetailsId(reviewId, tvDetailsId)
        .map(
            comment -> {
              tvReviewRepository.delete(comment);
              return 0;
            })
        .orElseThrow(() -> new NotFoundException("Review Not Found"));
  }
}
