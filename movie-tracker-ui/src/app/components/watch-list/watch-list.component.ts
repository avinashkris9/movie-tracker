import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { WatchListService } from 'src/app/services/watch-list.service';
import { WatchList } from 'src/app/model/watch-list';
import { MatAccordion } from '@angular/material';
import {MatDialog, MAT_DIALOG_DATA} from '@angular/material/dialog'
import { AddMovieComponent } from '../add-movie/add-movie.component';
@Component({
  selector: 'app-watch-list',
  templateUrl: './watch-list.component.html',
  styleUrls: ['./watch-list.component.css']
})
export class WatchListComponent implements OnInit {

  constructor(private watchListService:WatchListService) { }
  
  movieList:WatchList;
  ngOnInit() {
    this.getAllMovieList();
  }
 
  getAllMovieList()
  {
    
      this.watchListService.getAllTvShows().subscribe(
    data => {
         
      // this.message = " Movie Successfully Added"
      // this.router.navigate(['/movies/details', data.id]);
    
      this.movieList=data;

    },
    (error) => {                              //Error callback
      console.error('error caught in component')
      // this.errorMessage = error;
      // this.loading = false;

    });
  }

addToReview(watchedMovie:WatchList)
{

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