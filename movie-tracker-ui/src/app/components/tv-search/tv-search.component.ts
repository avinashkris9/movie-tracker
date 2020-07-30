import { Component, OnInit } from '@angular/core';
import { TvService } from 'src/app/services/tv.service';
import { Subject, Observable } from 'rxjs';
import { MovieSearch } from 'src/app/model/movie-search';
import { FormControl } from '@angular/forms';
import { WatchListService } from 'src/app/services/watch-list.service';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { WatchList } from 'src/app/model/watch-list';

@Component({
  selector: 'app-tv-search',
  templateUrl: './tv-search.component.html',
  styleUrls: ['./tv-search.component.css']
})
export class TvSearchComponent implements OnInit {

  
  private searchTerms = new Subject<string>();
  movies$: Observable<MovieSearch[]>;
  movieSearchDetails: MovieSearch;
  myControl = new FormControl();
  isNewAdd:boolean=false;
  constructor(private movieService :TvService,private watchListService:WatchListService) { }
 



  ngOnInit() {
   
    

    this.movies$ =  this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.movieService.searchTv(term)),
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
  if (movie) { return movie.name; }
}



movieSelected(movieSearch: any) {

  this.movieSearchDetails = movieSearch.value;

 
  
}

addToWatchList(movie:MovieSearch)
  {

    console.log(movie);
   let  movieWatchList=new WatchList;
    movieWatchList.name=movie.title;
    movieWatchList.externalId=movie.id;
    movieWatchList.showType="TV";
    
    this.watchListService.addToWatchList(movieWatchList).subscribe
    (
      data => console.log(data)
    );
  }

  rateAndReview(movie:MovieSearch)
  {
    this.isNewAdd=true;
  }
}


