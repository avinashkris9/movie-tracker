import { Component, OnInit, Inject } from '@angular/core';

import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Movie } from 'src/app/model/movie';
import { FormGroup, FormControl } from '@angular/forms';
import { MovieService } from 'src/app/services/movie.service';
import { Router } from '@angular/router';
import { formatDate, DatePipe } from '@angular/common';


@Component({
  selector: 'app-movie-edit',
  templateUrl: './movie-edit.component.html',
  styleUrls: ['./movie-edit.component.css'],
  providers:[DatePipe]
})
export class MovieEditComponent implements OnInit {
  date = new Date();

  updateMovieForm = new FormGroup({
    lastWatched: new FormControl(this.date),
  
    rating: new FormControl(this.data.rating),
  
   
  });



  constructor(
    public dialogRef: MatDialogRef<MovieEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Movie,
    
    private movieService: MovieService, private route: Router,
    public datepipe: DatePipe) {}

  onNoClick(): void {
    this.dialogRef.close();
  }


  
  ngOnInit() {
   
  }

  onSubmit()
  {

    
   

    let formattedDt = this.updateMovieForm.get('lastWatched').value;
    
    this.data.lastWatched=this.datepipe.transform(formattedDt, 'dd-MM-yyyy');;
    console.log(` movie ${this.data.movieName} last watched on ${this.data.lastWatched}`)
    this.data.rating =this.updateMovieForm.get('rating').value;
    this.movieService.updateMovie(this.data)
    .subscribe
    (
      data => {
        this.data=data;
       
        this.dialogRef.close(this.data);
      

      }
    )
  }
}
