import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { TrackerStatistics } from 'src/app/model/TrackerStatistics';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {

  constructor(private adminService:AdminService) { }
 
  statistics:TrackerStatistics;
  ngOnInit() {
    this.getStatistics();
    
  }

 

  getStatistics()  {
      this.adminService.getStatistics().subscribe(
        data => 
        {
          this.statistics=data;
   
          
        }
      )
   
  }
}
