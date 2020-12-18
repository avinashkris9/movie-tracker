import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { MovieSearch } from 'src/app/model/movie-search';
import { debounceTime, distinctUntilChanged, switchMap, startWith, map } from 'rxjs/operators';
import { MovieService } from 'src/app/services/movie.service';
import { FormControl } from '@angular/forms';
import { WatchList } from 'src/app/model/watch-list';
import { WatchListService } from 'src/app/services/watch-list.service';
import {MatDialog} from '@angular/material/dialog';
import { AddMovieComponent } from '../add-movie/add-movie.component';
import { MessagesComponent } from '../messages/messages.component';

@Component({
  selector: 'app-movie-search',
  templateUrl: './movie-search.component.html',
  styleUrls: ['./movie-search.component.css']
})
export class MovieSearchComponent implements OnInit {
  
  message: string='';
  private searchTerms = new Subject<string>();
  movies$: Observable<MovieSearch[]>;
  movieSearchDetails: MovieSearch;
  myControl = new FormControl();
  isNewAdd:boolean=false;
  
  @ViewChild('callAPIDialog', {static: false}) callAPIDialog: TemplateRef<any>;
  constructor(private movieService :MovieService,private watchListService:WatchListService
    ,public dialog: MatDialog) { }
 

    openDialog( movie:MovieSearch) {
      const  dialogRef = this.dialog.open(this.callAPIDialog,{ data: movie });
     
    }

    openAddReviewDialog(movie:MovieSearch)
    {
    
      let dialogRef = this.dialog.open(AddMovieComponent, {
        width: '50%',
        data: movie,
      });
  
     
    }
  ngOnInit() {
   
    

    this.movies$ =  this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.movieService.searchMovie(term)),
    );


  }
 // Push a search term into the observable stream.
 search(term: string): void {

  this.searchTerms.next(term);

}

onSubmit() {
  // TODO: Use EventEmitter with form value
  
  

}
displayFn(movie: MovieSearch): string {
  if (movie) { return movie.title; }
}



movieSelected(movieSearch: any) {

  this.movieSearchDetails = movieSearch.value;
  // this. isNewAdd=false;
 
  
}

addToWatchList(movie:MovieSearch)
  {

   
   let  movieWatchList=new WatchList;
    movieWatchList.name=movie.title;
    movieWatchList.externalId=movie.id;
    movieWatchList.showType="MOVIE";
    
    this.watchListService.addToWatchList(movieWatchList).subscribe
    (
      data => console.log(data),
      
      (error) => {                              //Error callback
        console.error('error caught in component')
        // this.errorMessage = error;
        // this.loading = false;

        console.log(error);

        if (error.error.code == 'DUPLICATE') {
          // this.openSnackBar("Movie Exists","Please update")

          this.message = "Movie Already in your watch list!"
        }
        else {
          // this.openSnackBar("Sorry, Error Occurred","Please Try Again")
          
          this.message = "Sorry Error Occured. Please try after sometime!";

        }
        this.displayMessage(this.message);
      }
    );
  }

  rateAndReview(movie:MovieSearch)
  {
    this.isNewAdd=true;
  
    this.openAddReviewDialog(movie);
  }

  displayMessage(message: string) {
    this.dialog.open(MessagesComponent, {


      data: {
        message: message
      }
    });
  }
}
