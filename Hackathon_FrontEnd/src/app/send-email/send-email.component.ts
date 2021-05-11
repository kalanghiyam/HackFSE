import { Component, OnInit,ViewChild } from '@angular/core';
import { FormGroup,FormControl,FormBuilder,Validators} from '@angular/forms';
import { Observable} from 'rxjs';
import { UploadFileService } from '../service/file-upload.service';
import { EventEmployeeInfo } from '../model/eventEmployeeInfo.model';
import { MatPaginator, MatTableDataSource,MatSort } from '@angular/material';
import { SelectionModel} from '@angular/cdk/collections';


@Component({
  selector: 'app-send-email',
  templateUrl: './send-email.component.html',
  styleUrls: ['./send-email.component.css']
})
export class SendEmailComponent implements OnInit {
  myControl = new FormControl();
  options: Observable<string[]>;
  sendEmailForm: FormGroup;
  message:string;

  displayedColumns: string[] = ['select','eventId','associateId','associateName','bu','eventStatus'];
  dataSource = new MatTableDataSource<EventEmployeeInfo>();
  selection = new SelectionModel<EventEmployeeInfo>(true, []);
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private uploadService: UploadFileService,private formBuilder: FormBuilder) { }
 
  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.getEventDropDownList();
    this.getSendMailEventList();
    this.sendEmailForm = this.formBuilder.group({
      eventId: ['', Validators.required]

    });
   
  }


  public doFilter = (value: string) => {
    this.dataSource.filter = value.trim().toLocaleLowerCase();
  }
  
  onSubmit() {


    if (this.sendEmailForm.invalid) {
            return;
    }else{
        this.loadData();
        this.uploadService.getSearchList(this.sendEmailForm.controls.eventId.value) .subscribe(eventEmployeeInfo => {
          this.dataSource.data = eventEmployeeInfo as EventEmployeeInfo[];
        })
    }


  }
  public getEventDropDownList() {
    this.options = this.uploadService.getEventDropDownList();
  }

  public getSendMailEventList(){
    this.uploadService.getSendMailEventList()
    .subscribe(eventEmployeeInfo => {
      this.dataSource.data = eventEmployeeInfo as EventEmployeeInfo[];
    })
  }

  
  public loadData() {
    this.dataSource = new MatTableDataSource<EventEmployeeInfo>();
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  public sendEmail(){
    
    this.uploadService.sendEmail( this.dataSource.data).subscribe(
      data => {
        console.log(data);
      },
      error => console.log(error));;
      this.message='Email sent successfully';
  }
}
