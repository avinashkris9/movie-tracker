import { Component, OnInit } from '@angular/core';
import { TvService } from 'src/app/services/tv.service';
import { Movie } from 'src/app/model/movie';
import { faCalendar, faStar } from '@fortawesome/free-solid-svg-icons';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material';
import { MovieEditComponent } from '../movie-edit/movie-edit.component';

@Component({
  selector: 'app-tv-details',
  templateUrl: './tv-details.component.html',
  styleUrls: ['./tv-details.component.css']
})
export class TvDetailsComponent implements OnInit {

 
  private movieId: number;
   movie:Movie=new Movie();
  faCalendar = faCalendar;
  faStar=faStar;
  message: string;

  constructor( private route:ActivatedRoute , private movieService:TvService,
    public dialog: MatDialog, private router:Router) { }


  ngOnInit() {
    this.getMovieDetails();
  }
  getMovieDetails() {
    const hasCategoryId: boolean = this.route.snapshot.paramMap.has("id");
    if (hasCategoryId) {

      this.movieId = +this.route.snapshot.paramMap.get("id");
      this.movieService.getByTvId(this.movieId).subscribe
      (
 
         data => 
         {
           this.movie =data;
           console.log(this.movie)
        
         }
           )
    }
  }
 
 
  openUpdateDialog()
  {
    let dialogRef = this.dialog.open(MovieEditComponent, {
      width: '250px',
      data: this.movie,
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.movie.lastWatched=result.lastWatched;
      this.movie.numberOfWatch=result.numberOfWatch;
      console.log(this.movie);
    });
  }

  deleteMovie(movie:Movie)
  {
    console.log('Request to delete movie' +movie.id);
    this.movieService.deleteByTvId(movie.id).subscribe(
      (data) =>
      {
        this.message="Success";
        this.router.navigate(['/tv']).then(() => {
          window.location.reload();  //reload the /movies page
        });;;
      },
      (error) => {                              //Error callback
        console.error('error caught in component')
        // this.errorMessage = error;
        // this.loading = false;

        console.log(error.statusText);
      }
    );
    
  }
}
