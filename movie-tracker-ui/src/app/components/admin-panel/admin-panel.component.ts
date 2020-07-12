import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { TrackerStatistics } from 'src/app/model/TrackerStatistics';
import * as Highcharts from 'highcharts';

import HC_exporting from 'highcharts/modules/exporting';
@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {

  constructor(private adminService:AdminService) { }
  chartOptions: {};
  Highcharts = Highcharts;
  statistics:TrackerStatistics;
//    months = {
//     'Jan' : '1',
//     'Feb' : '2',
//     'Mar' : '3',
//     'Apr' : '4',
//     'May' : '5',
//     'Jun' : '6',
//     'Jul' : '7',
//     'Aug' : '8',
//     'Sep' : '09',
//     'Oct' : '10',
//     'Nov' : '11',
//     'Dec' : '12'
// }
 months = ["jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"];
 chartData: [];
  ngOnInit() {
    this.getStatistics();
    
    
    
  }
chartOutput =[{
  name: 'Movies',
  data: [2,3,15,13,36,3,7,2,8]

}, {
  name: 'Tv',
  data: [1,1,3,4,1,5,1,1,1,4,10]

}];

 
  chartInit()
  {
    this.chartOutput=Object.values(this.statistics.monthlyCount);
    this.chartOptions = {
      chart: {
        type: 'column'
    },
      title: {
        text: 'Yearly Statistics'
      },
      subtitle: {
        text: 'Movie/TvShows'
      },
      tooltip: {
        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
            '<td style="padding:0"><b>{point.y} </b></td></tr>',
        footerFormat: '</table>',
        shared: true,
        useHTML: true
    },
    plotOptions: {
        column: {
            pointPadding: 0.2,
            borderWidth: 0
        }
    },
      credits: {
        enabled: false
      },
      exporting: {
        enabled: true,
      },
 
    xAxis: {
      categories: Object.keys(this.statistics.monthlyCount),
      // [
      //     'Jan',
      //     'Feb',
      //     'Mar',
      //     'Apr',
      //     'May',
      //     'Jun',
      //     'Jul',
      //     'Aug',
      //     'Sep',
      //     'Oct',
      //     'Nov',
      //     'Dec'
      // ],
      crosshair: true
  },
  yAxis: {
    min: 0,

    title: {
        text: 'Count'
    }
},
      legend: {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'top',
        x: -40,
        y: 80,
        floating: true,
        borderWidth: 1,
        backgroundColor:
            Highcharts.defaultOptions.legend.backgroundColor || '#FFFFFF',
        shadow: true
    },
      series:[
        
        {
          name: 'Movies',
          
          data: this.chartOutput
        }
      ] 
    };

    HC_exporting(Highcharts);

    setTimeout(() => {
      window.dispatchEvent(
        new Event('resize')
      );
    }, 300);
  }
 

  getStatistics()  {
      this.adminService.getStatistics().subscribe(
        data => 
        {
          console.log(data);
          this.statistics=data;
          console.log(Object.values(this.statistics.monthlyCount));
          this.chartInit();
          this.chartify();
          
        }
      )
   
      
     
  }

  chartify()
  {
    for (let key of Object.keys(this.statistics.monthlyCount)) {
      let monthlyVaue = this.statistics.monthlyCount[key];
      // this.chartData.push({
      //   name: key,
      //   y: monthlyVaue});
      
      
      
    }
  }
}
