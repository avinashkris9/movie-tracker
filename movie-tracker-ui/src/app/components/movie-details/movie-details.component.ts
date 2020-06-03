import { Component, OnInit } from '@angular/core';
import { MovieService } from 'src/app/services/movie.service';
import { ActivatedRoute } from '@angular/router';
import { Movie } from 'src/app/model/movie';
import { faCalendar,faStar } from '@fortawesome/free-solid-svg-icons';
@Component({
  selector: 'app-movie-details',
  templateUrl: './movie-details.component.html',
  styleUrls: ['./movie-details.component.css']
})
export class MovieDetailsComponent implements OnInit {

  private movieId: number;
  private movie:Movie;
  faCalendar = faCalendar;
  faStar=faStar;
  constructor( private route:ActivatedRoute , private movieService:MovieService) { }

  ngOnInit() {
    this.getMovieDetails();
  }
  getMovieDetails() {
    const hasCategoryId: boolean = this.route.snapshot.paramMap.has("id");
    if (hasCategoryId) {

      this.movieId = +this.route.snapshot.paramMap.get("id");
      this.movieService.getByMovieId(this.movieId).subscribe
      (
 
         data => 
         {
           this.movie =data;
          // console.log(" Calling API"+this.product.imageUrl);
         }
           )
    }
  }

}
