import { Component, OnInit } from '@angular/core';
import { MovieService } from 'src/app/services/movie.service';
import { Movie } from 'src/app/model/movie';
import { faStar } from '@fortawesome/free-solid-svg-icons';
@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.css']
})
export class MovieListComponent implements OnInit {

  private movies:Movie[]=null;;
  constructor( private movieService:MovieService) { }
  faCoffee = faStar;
  ngOnInit() {

   this.getAllWatchedMovies();
  }

  getAllWatchedMovies()
  {
  

     this.movieService.getAllMovies().subscribe
     (
         data => 
         {
          this.movies =data ;
          console.log(data);
          console.log("Any one hear me ?");
         }
     );
  }

}
