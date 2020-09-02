import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { WatchListService } from 'src/app/services/watch-list.service';
import { WatchList } from 'src/app/model/watch-list';
import { MatAccordion } from '@angular/material';
import {MatDialog, MAT_DIALOG_DATA} from '@angular/material/dialog'
import { AddMovieComponent } from '../add-movie/add-movie.component';
import { Movie } from 'src/app/model/movie';
import { Router } from '@angular/router';
@Component({
  selector: 'app-watch-list',
  templateUrl: './watch-list.component.html',
  styleUrls: ['./watch-list.component.css']
})
export class WatchListComponent implements OnInit {

  constructor(private watchListService:WatchListService,public dialog: MatDialog,
    private route :Router) { }
  public movie: Movie;
  movieList:WatchList;
  tvList:WatchList;
  isTvLoading:Boolean =true;
  isMovieLoading:Boolean =true;
  ngOnInit() {
    this.getAllMovieList();
    this.getAllTvList();
  }
 
  getAllMovieList()
  {
    
      this.watchListService.getAllMovieShows().subscribe(
    data => {
         
      // this.message = " Movie Successfully Added"
      // this.router.navigate(['/movies/details', data.id]);
    
      this.movieList=data;
      this.isMovieLoading=false;

    },
    (error) => {                              //Error callback
      console.error('error caught in component')
      this.isMovieLoading=false;
      // this.errorMessage = error;
      // this.loading = false;

    });
  }
  getAllTvList()
  {
    
      this.watchListService.getAllTvShows().subscribe(
    data => {
         
      // this.message = " Movie Successfully Added"
      // this.router.navigate(['/movies/details', data.id]);
      this.isTvLoading=false;
      this.tvList=data;

    },
    (error) => {                              //Error callback
      console.error('error caught in component')
      // this.errorMessage = error;
      // this.loading = false;
      this.isTvLoading=false;
    });
  }
openAddToWatched(watchedMovie:WatchList)
{
 
  
  this.movie.externalId=watchedMovie.id;
  this.movie.name=watchedMovie.name;
  this.movie.overView=watchedMovie.overView;
  this.movie.originalLanguage=watchedMovie.originalLanguage;
  this.movie.posterPath=watchedMovie.posterPath;

  
  
}


  deleteFromWatchList(watchedMovie:WatchList)
  {

    this.watchListService.deleteFromWatchList(watchedMovie)
    .subscribe(
      data => 
      {console.log("Removed")
      this.ngOnInit();},

      (error) =>
      console.error(error)
    )
  }
}