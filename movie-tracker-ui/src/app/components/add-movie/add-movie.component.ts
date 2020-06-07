import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MovieService } from 'src/app/services/movie.service';
import { Movie } from 'src/app/model/movie';
import { Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { MovieSearch } from 'src/app/model/movie-search';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition } from '@angular/material';
import { MatDialog, MatDialogRef } from '@angular/material';
import { MessagesComponent } from 'src/app/messages/messages.component';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-add-movie',
  templateUrl: './add-movie.component.html',
  styleUrls: ['./add-movie.component.css'],
  providers:[DatePipe]
})
export class AddMovieComponent implements OnInit {


  maxDate: Date =new Date();
  movieDetails: Movie;;
  message: string;
  date = new Date();
  // horizontalPosition: MatSnackBarHorizontalPosition = 'start';
  // verticalPosition: MatSnackBarVerticalPosition = 'bottom';
  movieForm = new FormGroup({
    movieName: new FormControl('', Validators.required),
    review: new FormControl(),
    rating: new FormControl('',Validators.required),
    lastWatched: new FormControl(new Date())
  });
  private searchTerms = new Subject<string>();
  movies$: Observable<MovieSearch[]>;

  myControl = new FormControl();
  constructor(private movieService: MovieService, private route: ActivatedRoute, private router: Router, private dialog: MatDialog,private datepipe: DatePipe) {



   }

  ngOnInit() {
    this.movies$ = this.movieForm.get('movieName').valueChanges.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.movieService.searchMovie(term)),
    );
  }
  displayFn(movie: MovieSearch): string {
    return movie && movie.title ? movie.title : '';
  }

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }

  addMovie(movie: Movie) {


    movie.lastWatched= this.datepipe.transform(movie.lastWatched,'dd-MM-yyyy');
    this.movieService.addMovie(movie).subscribe
      (
        data => {
          console.log(data);
          this.message = " Movie Successfully Added"
          this.router.navigate(['/movies/details', data.id]);

        },
        (error) => {                              //Error callback
          console.error('error caught in component')
          // this.errorMessage = error;
          // this.loading = false;

          console.log(error);

          if (error.error.code == 'MOVIE_EXISTS') {
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
  //  openSnackBar(inputString: string, outputString:string) {
  //     this._snackBar.open(inputString, outputString, {
  //       duration: 500,
  //       horizontalPosition: this.horizontalPosition,
  //       verticalPosition: this.verticalPosition,
  //     });
  //   }

  onSubmit() {
    // TODO: Use EventEmitter with form value
    console.warn(this.movieForm.value);
    this.addMovie(this.movieForm.value);


  }

  displayMessage(message: string) {
    this.dialog.open(MessagesComponent, {


      data: {
        message: message
      }
    });
  }


  get movieName() { return this.movieForm.get('movieName'); }
  get rating() { return this.movieForm.get('rating'); }


}
