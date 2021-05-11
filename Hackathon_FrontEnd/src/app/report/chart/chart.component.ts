import { Component, OnInit ,ViewChild} from '@angular/core';
import * as CanvasJS from 'src/assets/canvasjs.min';

import { ReportServiceService } from 'src/app/service/report-service.service';
import {MatPaginator, MatTableDataSource,MatIconModule,MatSortModule,MatInputModule ,MatSort } from '@angular/material';
import { EventStatusCount } from 'src/app/model/status.model';
import { Observable} from 'rxjs'; 
import { Dashboard } from '../../model/dahboard.model';
import { TokenStorageService } from '../../auth/token-storage.service';

@Component({
  selector: 'charts', 
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css']
})
export class ChartComponent implements OnInit {

  options: Observable<string[]>;
  participatedCount = [];
  unRegisteredCount = [];
  notAttendedCount = [];
  eventId=[];
  smileyReport=[];
  columnParticipated=[];
  columnNotAttended=[];
  columnUnRegistered=[];
  info: any;
  constructor(private token: TokenStorageService,private repService: ReportServiceService) { }
  displayedColumns: string[] = ['eventId'];
  dataSource = new MatTableDataSource<EventStatusCount>();
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort; 

  ngOnInit() {
    this.info = {
      token: this.token.getToken(),
      associateId: this.token.getAssociateId(),
      role: this.token.getRole()
    };
    this.getSmileyBasedChart();
    this.getEventBasedCount()
   
    if(this.info.role == 'Admin' || this.info.role == 'PMO') {
      this.getFeedbackRespondedList();
    }

  
  }

getEventBasedCount(){
  this.repService.getEventDashboardReport(this.info.role,this.info.associateId).subscribe((data: Dashboard[]) => {
    data.forEach(y => {
      console.log("dfdsfdf"+y.participatedCount);
      this.columnParticipated.push({label:y.eventId,y:y.participatedCount});
      this.columnNotAttended.push({label:y.eventId,y:y.notAttendedCount});
      this.columnUnRegistered.push({label:y.eventId,y:y.unRegisteredCount});
    });
    
    var chart = new CanvasJS.Chart("chartContainer", {
    animationEnabled: true,exportEnabled: true,
    title:{text: "Events Details with Participated,NotAttended,UnRegistered Chart"},	
    axisY:{title: "Event Participated Count",titleFontColor: "#4F81BC",gridThickness: 0,
              labelFontColor: "#4F81BC",tickColor: "#4F81BC"},
    legend: {cursor:"pointer",itemclick: toggleDataSeries},
    data: [{type: "column",name: "Participated:",legendText: "Participated",indexLabel: "{y}",
              showInLegend: true, dataPoints: this.columnParticipated},
            {type: "column",	name: "NotAttended:",legendText: "NotAttended",showInLegend: true,
              dataPoints: this.columnNotAttended
            },
            {type: "column",	name: "UnRegistered:",legendText: "UnRegistered",
              showInLegend: true,dataPoints: this.columnUnRegistered
            }]
          });
          chart.render();  
          function toggleDataSeries(e) {
            if (typeof(e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
              e.dataSeries.visible = false;
            }
            else {
              e.dataSeries.visible = true;
            }
            chart.render();
          }      
        
         
        });    
}
getSmileyBasedChart(){
    this.repService.fetchSmileyReport(this.info.role,this.info.associateId).subscribe((res: Dashboard[]) => {
      res.forEach(y => {
        this.smileyReport.push({label:y.eventId,y:y.averageSmileyCount});
      });
  var chart = new CanvasJS.Chart("smileyContainer",{exportEnabled: true,
    title: { text: "Smiley Based Chart"},
    axisY:{gridThickness: 0, minimum: 0, maximum: 5},
    data: [{type: "column",indexLabel: "{y}", dataPoints:this.smileyReport}]
  });
  chart.render();
});
}

getFeedbackRespondedList() {

    this.repService.getFeedbackRespondedList().subscribe((res: Dashboard[]) => {
      res.forEach(y => {
        this.smileyReport.push({label:y.eventId,y:y.averageSmileyCount});
      });
    let chart = new CanvasJS.Chart("cityContainer", {
      animationEnabled: true,
      startAngle: 240,
      exportEnabled: true,
      title: {
        text: "Event Feedback Responed details" 
      },data: [{
        type: "pie",
        indexLabel: "{label} {y}",
        dataPoints:this.smileyReport
      }]
    });
      
    chart.render();
  });
}
}
  
