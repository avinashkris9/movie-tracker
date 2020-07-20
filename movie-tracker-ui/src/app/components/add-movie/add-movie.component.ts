import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MovieService } from 'src/app/services/movie.service';
import { Movie } from 'src/app/model/movie';
import { Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { MovieSearch } from 'src/app/model/movie-search';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog, MatDialogRef } from '@angular/material';
import { MessagesComponent } from 'src/app/components/messages/messages.component';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-add-movie',
  templateUrl: './add-movie.component.html',
  styleUrls: ['./add-movie.component.css'],
  providers: [DatePipe]
  
})
export class AddMovieComponent implements OnInit {


  maxDate: Date = new Date();
  movieDetails: Movie;;
  movieSearchDetails: MovieSearch;
  message: string;
  date = new Date();

  movieForm = new FormGroup({
    name: new FormControl('', Validators.required),

    review: new FormControl(''),
    rating: new FormControl('', Validators.required),
    lastWatched: new FormControl(new Date())
  });

  private searchTerms = new Subject<string>();
  movies$: Observable<MovieSearch[]>;

  constructor(private movieService: MovieService, private route: ActivatedRoute, private router: Router, private dialog: MatDialog, private datepipe: DatePipe) {

  }

  ngOnInit() {
    this.movies$ = //this.movieForm.get('movieName').valueChanges.pipe(

      this.searchTerms.pipe(
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

  //add movie
  addMovie(movie: Movie) {

  
    movie.lastWatched = this.datepipe.transform(movie.lastWatched, 'dd-MM-yyyy');
    movie.externalId=this.movieSearchDetails.id;
    movie.posterPath=this.movieSearchDetails.poster_path;
    movie.overView=this.movieSearchDetails.overview;
    movie.imdbId=this.movieSearchDetails.imdbId;
    movie.originalLanguage=this.movieSearchDetails.original_language

   
    console.log(' Sending to backend'+JSON.stringify(movie));
   this.movieService.addMovie(movie).subscribe
      (
        data => {
         
          this.message = " Movie Successfully Added"
          this.router.navigate(['/movies/details', data.id]);

        },
        (error) => {                              //Error callback
          console.error('error caught in component')
          // this.errorMessage = error;
          // this.loading = false;

          console.log(error);

          if (error.error.code == 'DUPLICATE') {
            // this.openSnackBar("Movie Exists","Please update")

            this.message = "Movie Already Exists. Please update it here"
          }
          else {
            // this.openSnackBar("Sorry, Error Occurred","Please Try Again")
            
            this.message = "Sorry Error Occured. Please try after sometime!";

          }
          this.displayMessage(this.message);
        }

      )

  }


  onSubmit() {
    // TODO: Use EventEmitter with form value
    console.warn(this.movieForm.value);


    this.addMovie(this.movieForm.value);


  }

  movieSelected(movieSearch: any) {

    this.movieSearchDetails = movieSearch.value;
  
    this.movieForm.get('name').setValue(this.movieSearchDetails.title);
    
  

  }
  displayMessage(message: string) {
    this.dialog.open(MessagesComponent, {


      data: {
        message: message
      }
    });
  }
  displayFn(movie: MovieSearch): string {
    if (movie) { return movie.title; }
  }

  get movieName() { return this.movieForm.get('name'); }
  get rating() { return this.movieForm.get('rating'); }


}
