import { Component, OnInit, ViewChild } from '@angular/core';
import {DataSource} from '@angular/cdk/collections';
import {MatPaginator, MatTableDataSource,MatIconModule,MatSortModule,MatInputModule ,MatSort } from '@angular/material';
import { ReportServiceService } from '../service/report-service.service';
import { EventSummaryDetails } from '../model/report.model';
import { Router, ActivatedRoute } from '@angular/router';
import * as CanvasJS from 'src/assets/canvasjs.min';
import { MAT_LABEL_GLOBAL_OPTIONS} from '@angular/material/core';
import {MatFormFieldModule} from '@angular/material/form-field';
import {Observable} from 'rxjs';
import {FormControl} from '@angular/forms';
import {MatDialogModule,MatDialogRef ,MatDialog,MatDialogConfig,MatSelectModule} from "@angular/material";
import {ChartComponent} from 'src/app/report/chart/chart.component'
import { EventStatusCount } from '../model/status.model';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {
  displayedColumns: string[] = ['eventId', 'month', 'baseLocation','beneficiaryName',
  'venueAddress','councilName','project','eventName','eventDesc','eventDate'
  ,'noofVolunteer','volunteerhrs','overallVolRingHrs', 'livesImpacted','activityType', 
  'status', 'pocId' ,'pocName' ,'pocContactNo'
];
  dataSource = new MatTableDataSource<EventSummaryDetails>();
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort; 
  
  constructor(private repService: ReportServiceService,private dialog: MatDialog) { 

  }
  

  openDialog() {
    
    const dialogConfig = new MatDialogConfig();   

        dialogConfig.disableClose = false;
        dialogConfig.autoFocus = true;
        dialogConfig.closeOnNavigation= true; 
         this.dialog.open(ChartComponent,dialogConfig);
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

  search(selectedValue: string, selectedValueEle: string) {
    selectedValue = selectedValue.trim(); // Remove whitespace
  selectedValue = selectedValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches

   selectedValueEle = selectedValueEle.trim(); 

   this.dataSource.filter = selectedValue + selectedValueEle;
   debugger
}

  ngOnInit() {
    this.getEntries();
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;   
  }
  
   
  public getEntries = () => {
    this.repService.getEntries()
    .subscribe(entry => {
      this.dataSource.data = entry as EventSummaryDetails[];
    })
  }

}
