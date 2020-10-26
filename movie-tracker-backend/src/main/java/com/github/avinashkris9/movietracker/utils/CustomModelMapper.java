package com.github.avinashkris9.movietracker.utils;

import com.github.avinashkris9.movietracker.entity.MovieDetails;
import com.github.avinashkris9.movietracker.entity.MovieReview;
import com.github.avinashkris9.movietracker.entity.TvDetails;
import com.github.avinashkris9.movietracker.entity.TvReview;
import com.github.avinashkris9.movietracker.entity.WatchList;
import com.github.avinashkris9.movietracker.model.MovieDetailsDTO;
import com.github.avinashkris9.movietracker.model.MovieReviewDTO;
import com.github.avinashkris9.movietracker.model.WatchListDTO;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CustomModelMapper {

  public MovieDetails movieDTO2MovieEntity(MovieDetailsDTO movieDetailsDTO) {
    MovieDetails movieDetails = new MovieDetails();

    movieDetails.setLastWatched(movieDetailsDTO.getLastWatched());
    movieDetails.setNumberOfWatch(movieDetailsDTO.getNumberOfWatch());
    movieDetails.setExternalId(movieDetailsDTO.getExternalId());
    movieDetails.setMovieName(movieDetailsDTO.getName());
    movieDetails.setRating(movieDetailsDTO.getRating());


    if(!movieDetails.getMovieReviews().isEmpty())
    {movieDetails.setMovieReviews(movieDetailsDTO.getReviews().stream().map(
        this::movieReviewDTO2MovieReviewEntity).collect(Collectors.toSet())
    );
    }
    return movieDetails;
  }

  public MovieDetailsDTO movieEntity2MovieDTO(MovieDetails movieDetails) {
    MovieDetailsDTO details = new MovieDetailsDTO();
    details.setLastWatched(movieDetails.getLastWatched());
    details.setNumberOfWatch(movieDetails.getNumberOfWatch());
    details.setExternalId(movieDetails.getExternalId());
    details.setName(movieDetails.getMovieName());
    details.setRating(movieDetails.getRating());

    if (!movieDetails.getMovieReviews().isEmpty()) {
      details.setReviews(
          movieDetails.getMovieReviews().stream()
              .map(this::movieReviewEntity2MoviewReviewDTO)
              .collect(Collectors.toSet()));
    }
    details.setId(movieDetails.getId());
    return details;
  }

  public TvDetails movieDetailsDTO2TvEntity(MovieDetailsDTO movieDetailsDTO) {
    TvDetails tvDetails = new TvDetails();

    tvDetails.setLastWatched(movieDetailsDTO.getLastWatched());

    tvDetails.setExternalId(movieDetailsDTO.getExternalId());
    tvDetails.setTvShowName(movieDetailsDTO.getName());
    tvDetails.setRating(movieDetailsDTO.getRating());

    if(!tvDetails.getTvReviews().isEmpty())
    {tvDetails.setTvReviews(movieDetailsDTO.getReviews().stream().map(
        this::movieReviewDTO2TvReviewEntity).collect(Collectors.toSet())
    );
    }
    return tvDetails;
  }

  public MovieDetailsDTO tvEntity2MovieDTO(TvDetails tvDetails) {
    MovieDetailsDTO details = new MovieDetailsDTO();
    details.setLastWatched(tvDetails.getLastWatched());

    details.setExternalId(tvDetails.getExternalId());
    details.setName(tvDetails.getTvShowName());
    details.setRating(tvDetails.getRating());
//    details.setReview(tvDetails.getReview());
    details.setId(tvDetails.getId());
    if (!tvDetails.getTvReviews().isEmpty()) {
      details.setReviews(
          tvDetails.getTvReviews().stream()

              .map(this::tvReviewEntity2MoviewReviewDTO)
              .collect(Collectors.toSet()));
    }
    return details;
  }

  public WatchList watchListDTO2WatchListEntity(WatchListDTO watchListDTO)
  {
    WatchList watchList=new WatchList();
    watchList.setName(watchListDTO.getName());
    watchList.setDateAdded(watchListDTO.getDateAdded());
    watchList.setShowType(watchListDTO.getShowType());
    watchList.setExternalId(watchListDTO.getExternalId());
    return watchList;
  }

  public WatchListDTO watchListEntity2WatchListDTO(WatchList watchListEntity)
  {
    WatchListDTO watchList=new WatchListDTO();
    watchList.setName(watchListEntity.getName());
    watchList.setDateAdded(watchListEntity.getDateAdded());
    watchList.setShowType(watchListEntity.getShowType());
    watchList.setExternalId(watchListEntity.getExternalId());
    watchList.setId(watchListEntity.getId());
    return watchList;
  }



  public MovieReview movieReviewDTO2MovieReviewEntity(MovieReviewDTO movieReviewDTO)
  {
    MovieReview movieReview=new MovieReview();
    movieReview.setReview(movieReviewDTO.getReview());
    movieReview.setReviewId(movieReviewDTO.getReviewId());
    movieReview.setLastReviewed(movieReviewDTO.getLastReviewed());
    return  movieReview;
  }

  public MovieReviewDTO movieReviewEntity2MoviewReviewDTO(MovieReview movieReview)
  {

    MovieReviewDTO movieReviewDTO=new MovieReviewDTO();
    movieReviewDTO.setReviewId(movieReview.getReviewId());
    movieReviewDTO.setReview(movieReview.getReview());
    movieReviewDTO.setLastReviewed(movieReview.getLastReviewed());
    return  movieReviewDTO;
  }

  public TvReview movieReviewDTO2TvReviewEntity(MovieReviewDTO movieReviewDTO)
  {
    TvReview movieReview=new TvReview();
    movieReview.setReview(movieReviewDTO.getReview());
    movieReview.setReviewId(movieReviewDTO.getReviewId());
    movieReview.setLastReviewed(movieReviewDTO.getLastReviewed());
    return  movieReview;
  }

  public MovieReviewDTO tvReviewEntity2MoviewReviewDTO(TvReview movieReview)
  {

    MovieReviewDTO movieReviewDTO=new MovieReviewDTO();
    movieReviewDTO.setReviewId(movieReview.getReviewId());
    movieReviewDTO.setReview(movieReview.getReview());
    movieReviewDTO.setLastReviewed(movieReview.getLastReviewed());
    return  movieReviewDTO;
  }

}
