package com.github.avinashkris9.movietracker.controller;

import com.github.avinashkris9.movietracker.entity.WatchList;
import com.github.avinashkris9.movietracker.model.WatchListDTO;
import com.github.avinashkris9.movietracker.service.WatchListService;
import com.github.avinashkris9.movietracker.utils.APIUtils;
import com.github.avinashkris9.movietracker.utils.APIUtils.SHOW_TYPES;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/watchlist")
@Slf4j
@AllArgsConstructor
@CrossOrigin
public class WatchListController {

  private final WatchListService watchListService;

  @GetMapping()
  List<WatchListDTO> getAllWatchListEntries(@RequestParam (defaultValue = "MOVIE") String showType)
  {
    //TODO - use a pattern in @RequestParam to validate the param to only have either Movie/TV as value.
    if(!showType.equalsIgnoreCase(SHOW_TYPES.MOVIE.name()))
    {
      showType=SHOW_TYPES.TV.name();
    }
    return watchListService.getAllWatchListEntriesByShowType(showType);
  }

  @GetMapping("/search")
  WatchListDTO getAllWatchListEntriesByNameAndShowType(@RequestParam  @Valid @Pattern(regexp = "MOVIE|TV",message = "showType must be either MOVIE or TV") String showType,@RequestParam String name)
  {

    if(showType.equalsIgnoreCase(SHOW_TYPES.MOVIE.name()))
    {
      return watchListService.getWatchListMovieEntryByName(name);
    }

      return  watchListService.getWatchListMovieEntryByName(name);

  }


  @PostMapping()
  public WatchListDTO addShowToWatchList(@RequestBody  @Valid WatchListDTO watchListDTO)
  {
    //TODO -validation for showType
    log.debug(watchListDTO.toString());
    log.info("Adding movie {} to watch list ",watchListDTO.getName());
    return watchListService.addToWatchList(watchListDTO);
  }



  @DeleteMapping("/{id}")
  public void deleteShowFromWatchList(@PathVariable long id)
  {
      log.info("Remove from watch list entry with id {} ",id);
     watchListService.deleteFromWatchList(id);
  }
}
