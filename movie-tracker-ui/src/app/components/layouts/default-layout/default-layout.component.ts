import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-default-layout',
  templateUrl: './default-layout.component.html',
  styleUrls: ['./default-layout.component.css']
})
export class DefaultLayoutComponent implements OnInit {
  sideBarOpen: boolean =true;
  constructor() { }

  ngOnInit() {
    // if (window.innerWidth<1440) {

    //   this.sideBarOpen=false;
      
    //   }
  }



  sideBarToggler()
  {
this.sideBarOpen=!this.sideBarOpen;


  }
}
