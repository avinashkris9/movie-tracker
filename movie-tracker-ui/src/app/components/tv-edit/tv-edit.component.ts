import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Movie } from 'src/app/model/movie';
import { MovieService } from 'src/app/services/movie.service';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';
import { TvService } from 'src/app/services/tv.service';

@Component({
  selector: 'app-tv-edit',
  templateUrl: './tv-edit.component.html',
  styleUrls: ['./tv-edit.component.css']
})
export class TvEditComponent implements OnInit {

  date = new Date();

  updateMovieForm = new FormGroup({
    lastWatched: new FormControl(this.date),
  
    rating: new FormControl(this.data.rating),
    review:new FormControl()
  
   
  });



  constructor(
    public dialogRef: MatDialogRef<TvEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Movie,
    
    private tvService: TvService, private route: Router,
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
    console.log(` movie ${this.data.name} last watched on ${this.data.lastWatched}`)
    this.data.rating =this.updateMovieForm.get('rating').value;
    this.data.review =this.updateMovieForm.get('review').value;
    this.tvService.updateMovie(this.data)
    .subscribe
    (
      data => {
        this.data=data;
       
        this.dialogRef.close(this.data);
      

      }
    )
  }
}
