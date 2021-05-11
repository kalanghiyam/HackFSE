import { Component, OnInit,ViewChild } from '@angular/core';
import { User } from '../../model/user.model';
import {UserRoleService } from '../../service/user-role.service';
import {MatPaginator, MatTableDataSource,MatDialog ,MatSort } from '@angular/material';
import { EditUserComponent } from '../edit-user/edit-user.component';

@Component({
  selector: 'app-view-user',
  templateUrl: './view-user.component.html',
  styleUrls: ['./view-user.component.css']
})
export class ViewUserComponent implements OnInit {
   displayedColumns: string[] = ['associateId', 'associateName', 'role','Edit','delete'];
   dataSource = new MatTableDataSource<User>();
   
   @ViewChild(MatPaginator) paginator: MatPaginator;
   @ViewChild(MatSort) sort: MatSort;

  constructor(private userService: UserRoleService,private dialog: MatDialog) { }
  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.getUsers();
  }
 
  public getUsers = () => {
    this.userService.getUsers()
    .subscribe(user => {
      this.dataSource.data = user as User[];
    })
  }

  
  redirectToEdit = (user: User) => {
    this.getUsers();
  }

  redirectToDelete = (id: number) => {
    this.userService.deleteUser(id) .subscribe(
      data => {
        console.log(data);
        this.getUsers();
      },
      error => console.log(error));
}

   loadData() {
    this.dataSource = new MatTableDataSource<User>();
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.getUsers();
    
  }

 

  startEdit(i: number, associateId: number, associateName: string, role: string) {
    console.log("user"+associateId);
      const dialogRef = this.dialog.open(EditUserComponent, {
      data: {associateId:associateId, associateName:associateName, role:role}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log("inside");
        this.loadData();
    });
  }
}
