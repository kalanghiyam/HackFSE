import { Component, OnInit,ViewChild,ElementRef} from '@angular/core';
import {MatDialogModule,MatDialogRef ,MatDialog,MatDialogConfig,MatSelectModule} from "@angular/material";
import {DataSource} from '@angular/cdk/collections';
import {MatPaginator, MatTableDataSource,MatIconModule,MatSortModule,MatInputModule ,MatSort } from '@angular/material';
import { MAT_LABEL_GLOBAL_OPTIONS} from '@angular/material/core';
import {MatFormFieldModule} from '@angular/material/form-field';
import {Observable} from 'rxjs';
import { ReportServiceService } from 'src/app/service/report-service.service';
import * as XLSX from 'xlsx';
import { FormGroup,FormControl,FormBuilder,Validators} from '@angular/forms';
import { ResponseSummaryDTO } from 'src/app/model/responseSummary.model';

   
const EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
const EXCEL_EXTENSION = '.xlsx';

@Component({ 
  selector: 'app-response',
  templateUrl: './response.component.html',
  styleUrls: ['./response.component.css']
})
export class ResponseComponent implements OnInit {

  @ViewChild('TABLE') table: ElementRef; 

  displayedColumns: string[] = ['event','feedbackResponse','status' ];

  dataSource = new MatTableDataSource<ResponseSummaryDTO>();
@ViewChild(MatPaginator) paginator: MatPaginator;
@ViewChild(MatSort) sort: MatSort; 

  constructor(private repService: ReportServiceService) { }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  ExportTOExcel()
  {
    // const ws: XLSX.WorkSheet=XLSX.utils.table_to_sheet(this.dataSource);
  
    const ws: XLSX.WorkSheet=XLSX.utils.table_to_sheet(this.table.nativeElement);
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
    /* save to file */
    XLSX.writeFile(wb, 'ResponseReport.xlsx');
    
  } 
  
  public getResponses = () => { 
    this.repService.getResponses()
    .subscribe(entry => { 
      this.dataSource.data = entry as ResponseSummaryDTO[];
    })
  } 

  ngOnInit() {
    this.getResponses(); 
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

}
