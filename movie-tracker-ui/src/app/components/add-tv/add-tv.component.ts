import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { Subject, Observable } from 'rxjs';
import { MovieSearch } from 'src/app/model/movie-search';
import { TvService } from 'src/app/services/tv.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material';
import { DatePipe } from '@angular/common';
import { MessagesComponent } from 'src/app/messages/messages.component';
import { Movie } from 'src/app/model/movie';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-add-tv',
  templateUrl: './add-tv.component.html',
  styleUrls: ['./add-tv.component.css'],
  providers: [DatePipe]
  
})
export class AddTvComponent implements OnInit {

  maxDate: Date = new Date();
  date = new Date();
  message: string;
  tvSearchDetails: MovieSearch;
  tvForm = new FormGroup({
    name: new FormControl('', Validators.required),

    review: new FormControl(''),
    rating: new FormControl('', Validators.required),
    lastWatched: new FormControl(new Date())
  });
  
  private searchTerms = new Subject<string>();
  tv$: Observable<MovieSearch[]>;

  constructor(private tvService: TvService, private route: ActivatedRoute, private router: Router, private dialog: MatDialog, private datepipe: DatePipe) { }

  ngOnInit() {

    this.tv$ = //this.movieForm.get('movieName').valueChanges.pipe(

      this.searchTerms.pipe(
        // wait 300ms after each keystroke before considering the term
        debounceTime(300),

        // ignore new term if same as previous term
        distinctUntilChanged(),

        // switch to new search observable each time the term changes
        switchMap((term: string) => this.tvService.searchTv(term)),
      );

  }

  //add movie
  addTvShow(tv: Movie) {

  
    tv.lastWatched = this.datepipe.transform(tv.lastWatched, 'dd-MM-yyyy');
    tv.externalId=this.tvSearchDetails.id;
    
    console.log(' Sending to backend'+JSON.stringify(tv));
    this.tvService.addMovie(tv).subscribe
      (
        data => {
         
          this.message = " Movie Successfully Added"
          this.router.navigate(['/tv/details', data.id]);

        },
        (error) => {                              //Error callback
          console.error('error caught in component')
          // this.errorMessage = error;
          // this.loading = false;

          console.log(error);

          if (error.error.code == 'DUPLICATE') {
            // this.openSnackBar("Movie Exists","Please update")

            this.message = "TV Show Already Exists. Please update it here"
          }
          else {
            // this.openSnackBar("Sorry, Error Occurred","Please Try Again")
            this.message = "Sorry Error Occured. Please try after sometime!";

          }
          this.displayMessage(this.message);
        }

      )

  }
// Push a search term into the observable stream.
search(term: string): void {
  this.searchTerms.next(term);
}
  onSubmit() {
    // TODO: Use EventEmitter with form value
    console.warn(this.tvForm.value);


    this.addTvShow(this.tvForm.value);


  }
  tvSelected(movieSearch: any) {

    this.tvSearchDetails = movieSearch.value;
    this.tvForm.get('name').setValue(this.tvSearchDetails.name);

  

  }
  displayMessage(message: string) {
    this.dialog.open(MessagesComponent, {


      data: {
        message: message
      }
    });
  }
  displayFn(tv: MovieSearch): string {
    if (tv) { return tv.name; }
  }


  get movieName() { return this.tvForm.get('name'); }
  get rating() { return this.tvForm.get('rating'); }
}
