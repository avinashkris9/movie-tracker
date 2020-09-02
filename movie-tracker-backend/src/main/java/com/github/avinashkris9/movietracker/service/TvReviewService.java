package com.github.avinashkris9.movietracker.service;

import com.github.avinashkris9.movietracker.entity.MovieReview;
import com.github.avinashkris9.movietracker.entity.TvReview;
import com.github.avinashkris9.movietracker.exception.NotFoundException;
import com.github.avinashkris9.movietracker.model.MovieReviewDTO;
import com.github.avinashkris9.movietracker.repository.MovieRepository;
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




  public List<MovieReviewDTO> getAllTvReviewsByTvId(long tvDetailsId)
  {
    List<TvReview> lists= tvReviewRepository.findByTvDetailsId(tvDetailsId);


    return lists.stream().map(customModelMapper::tvReviewEntity2MoviewReviewDTO).collect(
        Collectors.toList());
  }

  public MovieReviewDTO addNewTvReview(long tvDetailsId, MovieReviewDTO movieReviewDTO)
  {
    TvReview tvReview=customModelMapper.movieReviewDTO2TvReviewEntity(movieReviewDTO);
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

  public MovieReviewDTO updateTvReview(long tvDetailsId, long reviewId,MovieReviewDTO movieReviewDTO)
  {
    TvReview tvReview=customModelMapper.movieReviewDTO2TvReviewEntity(movieReviewDTO);
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
