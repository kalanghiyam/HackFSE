import { Component, OnInit,ViewChild,ElementRef} from '@angular/core';
import {MatPaginator, MatTableDataSource,MatSort } from '@angular/material';
import {Observable} from 'rxjs';
import { ReportServiceService } from 'src/app/service/report-service.service';
import { EventSummaryDetails } from 'src/app/model/report.model';
import * as XLSX from 'xlsx';
import { FormGroup,FormControl,FormBuilder} from '@angular/forms';
import { TokenStorageService } from '../../auth/token-storage.service';
import { tokenKey } from '@angular/core/src/view';

@Component({
  selector: 'app-summary',
  templateUrl: './summary.component.html',
  styleUrls: ['./summary.component.css']
})

export class SummaryComponent implements OnInit {
  info: any;
  myControl = new FormControl();
  locations: Observable<string[]>;
  projects : Observable<string[]>; 
  summaryForm: FormGroup;

  @ViewChild('TABLE') table: ElementRef;  

  displayedColumns: string[] = ['eventId', 'month', 'baseLocation','beneficiaryName',
  'venueAddress','councilName','project','eventName','eventDesc','eventDate'
  ,'noofVolunteer','volunteerhrs','overallVolRingHrs', 'livesImpacted','activityType', 
  'status', 'pocId' ,'pocName' ,'pocContactNo'
];
   

dataSource = new MatTableDataSource<EventSummaryDetails>();
@ViewChild(MatPaginator) paginator: MatPaginator;
@ViewChild(MatSort) sort: MatSort; 

  constructor(private token: TokenStorageService,private repService: ReportServiceService,private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.getEntries();
    this.getLocationList();
    this.getProjectList();
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;   
    this.summaryForm = this.formBuilder.group({
      baseLocation: [''],
      project: [''] 
    });
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      role: this.token.getRole()
    };
    if(this.info.role == 'POC') {
      this.doFilter(this.token.getAssociateId());
    }
  }

  public doFilter = (value: string) => {
    this.dataSource.filter = value.trim().toLocaleLowerCase();
  }

  ExportTOExcel()
{
  // const ws: XLSX.WorkSheet=XLSX.utils.table_to_sheet(this.dataSource);

  const ws: XLSX.WorkSheet=XLSX.utils.table_to_sheet(this.table.nativeElement);
  const wb: XLSX.WorkBook = XLSX.utils.book_new();
  XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
  /* save to file */
  XLSX.writeFile(wb, 'SheetJS.xlsx');
  
}

onSubmit() {


  if (this.summaryForm.invalid) {  
          return;
  }else{
      this.loadData();
      this.repService.getSearchSummary(this.summaryForm.controls.baseLocation.value,
        this.summaryForm.controls.project.value ) .subscribe(entry => {
          this.dataSource.data = entry as EventSummaryDetails[];   
      })
      if(this.info.role == 'POC') {
        this.doFilter(this.token.getAssociateId());
      }
  }
}

public loadData() {
  this.dataSource = new MatTableDataSource<EventSummaryDetails>();
  this.dataSource.paginator = this.paginator;
  this.dataSource.sort = this.sort;
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
 }
 
  public getEntries = () => {
    this.repService.getEntries()
    .subscribe(entry => {
      this.dataSource.data = entry as EventSummaryDetails[];
    })
  }

  public getLocationList = () => {    
      this.locations = this.repService.getLocationDropdown();
    
  }

  public getProjectList = () => { 
    this.projects = this.repService.getProjectDropdown();
  
}

}
