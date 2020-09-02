package com.github.avinashkris9.movietracker.service;

import com.github.avinashkris9.movietracker.entity.TvDetails;
import com.github.avinashkris9.movietracker.entity.WatchList;
import com.github.avinashkris9.movietracker.exception.EntityExistsException;
import com.github.avinashkris9.movietracker.exception.NotFoundException;
import com.github.avinashkris9.movietracker.model.MovieDBDetails;
import com.github.avinashkris9.movietracker.model.WatchListDTO;
import com.github.avinashkris9.movietracker.repository.WatchListRepository;
import com.github.avinashkris9.movietracker.utils.APIUtils;
import com.github.avinashkris9.movietracker.utils.APIUtils.SHOW_TYPES;
import com.github.avinashkris9.movietracker.utils.ApiCodes;
import com.github.avinashkris9.movietracker.utils.ApiCodes.API_CODES;
import com.github.avinashkris9.movietracker.utils.CustomModelMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class WatchListService {


  private final WatchListRepository watchListRepository;
  private final CustomModelMapper customModelMapper;
  private final TheMovieDBService theMovieDBService;

  public WatchListDTO addToWatchList(WatchListDTO watchListDTO)
  {
      if(!Objects.isNull(watchListRepository.findByNameAndShowType(watchListDTO.getName(),watchListDTO.getShowType())))
      {
        log.error(" Entry for show type {} with name {} exists ",watchListDTO.getShowType(),watchListDTO.getName());
        throw new EntityExistsException("Already added to watch list");
      }

      if(watchListDTO.getExternalId() ==null)
      {
        MovieDBDetails optionalMovieDBDetails =
            theMovieDBService.getMovieDetailsBySearch(
                watchListDTO.getName(), watchListDTO.getShowType());

        if (!optionalMovieDBDetails.getMovieDBDetails().isEmpty()) {
          long theMovieDbId = optionalMovieDBDetails.getMovieDBDetails().get(0).getMovieId();
          log.debug("The movie db entry found with external id {} ", theMovieDbId);
          watchListDTO.setExternalId(theMovieDbId);
          theMovieDBService.appendTheMovieDBDataToWatchList(watchListDTO, watchListDTO.getShowType());
        }
      }
      watchListDTO.setDateAdded(LocalDate.now());
      WatchList watchList =watchListRepository.save( customModelMapper.watchListDTO2WatchListEntity(watchListDTO));
      watchListDTO.setId(watchList.getId());
      return watchListDTO;
  }
  public void  deleteFromWatchList(Long watchListId)
  {
    Optional<WatchList> watchListEntry = watchListRepository.findById(watchListId);
    watchListEntry.orElseThrow(() -> new NotFoundException(ApiCodes.NO_WATCH_LIST_ENTRY));
    watchListRepository.deleteById(watchListId);
  }

  public List<WatchListDTO> getAllWatchListEntriesByShowType(String showType)
  {
    List<WatchList> watchListEntityList=watchListRepository.findByShowType(showType);
    if(watchListEntityList.isEmpty())
    {
      throw new NotFoundException(API_CODES.WATCHLIST_EMPTY.name());
    }
    else
    {
      log.info(" Obtained {} for {} ",watchListEntityList.size(),showType);
       return
           watchListEntityList.stream().map( watchListEntity ->
          {
              WatchListDTO watchListDTO=customModelMapper.watchListEntity2WatchListDTO(watchListEntity);
              theMovieDBService.appendTheMovieDBDataToWatchList(watchListDTO, showType);
              return watchListDTO;
          }
          )
          .collect(
          Collectors.toList());
    }
  }


  public WatchListDTO getWatchListMovieEntryByName(String name)
  {

   WatchList watchListEntity= watchListRepository.findByNameAndShowType(name, SHOW_TYPES.MOVIE.name());
    if (!Objects.isNull(watchListEntity)) {
      WatchListDTO watchListDTO=customModelMapper.watchListEntity2WatchListDTO(watchListEntity);
      if(watchListDTO.getExternalId()!=0)
      {
        theMovieDBService.appendTheMovieDBDataToWatchList(watchListDTO, SHOW_TYPES.MOVIE.name());
      }
      return watchListDTO;
    }
    throw new NotFoundException(API_CODES.NOT_FOUND.name());
  }


  public WatchListDTO getWatchListTvEntryByName(String name)
  {

    WatchList watchListEntity= watchListRepository.findByNameAndShowType(name, SHOW_TYPES.TV.name());
    if (!Objects.isNull(watchListEntity)) {

      WatchListDTO watchListDTO=customModelMapper.watchListEntity2WatchListDTO(watchListEntity);
      if(watchListDTO.getExternalId()!=0)
      {
        theMovieDBService.appendTheMovieDBDataToWatchList(watchListDTO, SHOW_TYPES.TV.name());
      }
      return watchListDTO;
    }
    throw new NotFoundException(API_CODES.NOT_FOUND.name());
  }
}
