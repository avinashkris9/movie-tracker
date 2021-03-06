package com.github.avinashkris9.movietracker.service;

import com.github.avinashkris9.movietracker.entity.MovieReview;
import com.github.avinashkris9.movietracker.exception.NotFoundException;
import com.github.avinashkris9.movietracker.model.MovieReviewResponse;
import com.github.avinashkris9.movietracker.repository.MovieRepository;
import com.github.avinashkris9.movietracker.repository.MovieReviewRepository;
import com.github.avinashkris9.movietracker.utils.CustomModelMapper;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieReviewService  {

  private final MovieReviewRepository movieReviewRepository;
  private final MovieRepository movieRepository;
  private final CustomModelMapper customModelMapper;




  public List<MovieReview> getAllMovieReviewsByMovieId(long movieDetailsId)
  {

    return  movieReviewRepository.findByMovieDetailsId(movieDetailsId);

  }

  public MovieReviewResponse addNewMovieReview(long movieDetailsId, MovieReviewResponse movieReviewResponse)
  {
    MovieReview movieReview=customModelMapper.movieReviewDTO2MovieReviewEntity(movieReviewResponse);
    movieReview.setLastReviewed(LocalDate.now());
    return  movieRepository.findById(movieDetailsId).map(

        movie ->
        {
          //map the movie data.
          movieReview.setMovieDetails(movie);
          return customModelMapper.movieReviewEntity2MoviewReviewDTO(movieReviewRepository.save(movieReview));
        }
    ).orElseThrow(() -> new NotFoundException("Movie Not Found"));
  }

  public MovieReviewResponse updateMovieReview(long movieDetailsId, long reviewId,
      MovieReviewResponse movieReviewResponse)
  {
    MovieReview movieReview=customModelMapper.movieReviewDTO2MovieReviewEntity(movieReviewResponse);
    if(!movieRepository.existsById(movieDetailsId))
    {
      throw  new NotFoundException("Movie Not Found to add review");
    }

    return movieReviewRepository.findById(reviewId).map(

        review ->
        {
          review.setLastReviewed(LocalDate.now());
          review.setReview(movieReview.getReview());
          return customModelMapper.movieReviewEntity2MoviewReviewDTO(movieReviewRepository.save(review));
        }

    ).orElseThrow(() -> new NotFoundException("Review Not Found"));
  }

  public void deleteReviewById(long movieDetailsId, long reviewId) {

    movieReviewRepository
        .findByReviewIdAndMovieDetailsId(reviewId, movieDetailsId)
        .map(
            comment -> {
              movieReviewRepository.delete(comment);
              return 0;
            })
        .orElseThrow(() -> new NotFoundException("Review Not Found"));
  }
}
