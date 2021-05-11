import { Component, OnInit,ViewChild } from '@angular/core';
import {FeedBackDetails} from '../../model/feedback.model';
import {FeedBackService } from '../../service/feedback-details.service';
import {MatPaginator, MatTableDataSource,MatDialog ,MatSort } from '@angular/material';
import { EditFeedbackComponent } from '../edit-feedback/edit-feedback.component';

@Component({
  selector: 'app-view-feedback',
  templateUrl: './view-feedback.component.html',
  styleUrls: ['./view-feedback.component.css']
})
export class ViewFeedbackComponent implements OnInit {

  displayedColumns: string[] = ['id','status','feedBackDesc','inputType','Edit','delete'];
  dataSource = new MatTableDataSource<FeedBackDetails>();
  
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  constructor(private feedBackService: FeedBackService,private dialog: MatDialog) { 
    var data = sessionStorage.getItem('id');
    console.log(data);
  }

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.getList();
  }
  public getList = () => {
    this.feedBackService.get()
    .subscribe(feedback => {
      this.dataSource.data = feedback as FeedBackDetails[];
    })
  }

  redirectToDelete = (id: number) => {
    this.feedBackService.delete(id).subscribe(
      data => {
        console.log(data);
        this.getList();
      },
      error => console.log(error));;
    this.loadData();
  }

   loadData() {
    this.dataSource = new MatTableDataSource<FeedBackDetails>();
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.getList();
    
  }

 

  startEdit(i:number,id: number, status: String, feedBackDesc: string, inputType: string) {
    console.log("user"+id);
      const dialogRef = this.dialog.open(EditFeedbackComponent, {
        width: '50%',height: '70%',
      data: {i:i,id:id, status:status, feedBackDesc:feedBackDesc,inputType:inputType}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log("inside");
        this.loadData();
    });
  }
}
