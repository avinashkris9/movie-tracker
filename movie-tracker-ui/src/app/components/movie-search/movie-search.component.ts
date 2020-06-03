import { Component, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { MovieSearch } from 'src/app/model/movie-search';
import { debounceTime, distinctUntilChanged, switchMap, startWith, map } from 'rxjs/operators';
import { MovieService } from 'src/app/services/movie.service';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-movie-search',
  templateUrl: './movie-search.component.html',
  styleUrls: ['./movie-search.component.css']
})
export class MovieSearchComponent implements OnInit {

  private searchTerms = new Subject<string>();
  movies$: Observable<MovieSearch[]>;

  myControl = new FormControl();
  
  constructor(private movieService :MovieService) { }
 



  ngOnInit() {
   
    

    this.movies$ = this.myControl.valueChanges.pipe(
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
  return movie && movie.title ? movie.title : '';
}
}
