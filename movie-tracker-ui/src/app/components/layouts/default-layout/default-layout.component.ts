import { Component, OnInit } from '@angular/core';
import {MediaMatcher} from '@angular/cdk/layout';
@Component({
  selector: 'app-default-layout',
  templateUrl: './default-layout.component.html',
  styleUrls: ['./default-layout.component.css']
})
export class DefaultLayoutComponent implements OnInit {
  sideBarOpen: boolean =true;
  constructor() { }

  ngOnInit() {
  }


  sideBarToggler()
  {
this.sideBarOpen=!this.sideBarOpen;
console.log(this.sideBarOpen)
  }
}
