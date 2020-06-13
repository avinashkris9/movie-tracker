import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.css']
})
export class MessagesComponent implements OnInit {


  constructor(private  dialogRef:  MatDialogRef<MessagesComponent>, @Inject(MAT_DIALOG_DATA) public  data:  any) {
  }
  public  closeMe() {
      this.dialogRef.close();
  }


  ngOnInit() {
  }

}
