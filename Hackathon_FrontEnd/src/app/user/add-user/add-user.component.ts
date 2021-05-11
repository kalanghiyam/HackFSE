import { Component, OnInit ,ViewChild,ElementRef} from '@angular/core';
import { FormGroup,FormBuilder, Validators } from '@angular/forms';
import { UserRoleService } from '../../service/user-role.service';
import { User } from '../../model/user.model';
import { HttpEvent,HttpResponse } from '@angular/common/http';
import * as XLSX from 'xlsx';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {
  uploadForm: FormGroup;
  userRoleForm: FormGroup;
  user: Object ={};
  submitted = false;
  errorMessage:string;
  filesToUpload: File;
  @ViewChild('TABLE') table: ElementRef;  
  constructor(
    private formBuilder: FormBuilder,
    private userService: UserRoleService
  ) { }

  ngOnInit() {
    this.userRoleForm = this.formBuilder.group({
      associateId: ['', Validators.required],
      role: ['', Validators.required],
      associateName: ['', Validators.required]
    });

    this.uploadForm = this.formBuilder.group({
      filesToUpload: ['', Validators.required]
    });
  }
  
  onSubmit(user: User) {
   
    this.submitted = true;

        // stop here if form is invalid
        if (this.userRoleForm.invalid) {
            return;
        }else{
          this.submitted = false;
    this.userService.addUser(user).subscribe(data => {alert("User created successfulyy");});
    this.errorMessage = 'User saved successfully';
    this.userRoleForm.reset();

        }
  }

  ExportTOExcel(){
    // const ws: XLSX.WorkSheet=XLSX.utils.table_to_sheet(this.dataSource);
  
    const ws: XLSX.WorkSheet=XLSX.utils.table_to_sheet(this.table.nativeElement);
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
    /* save to file */
    XLSX.writeFile(wb, 'SheetJS.xlsx');
    
  }

  
  upload() {
    this.errorMessage = '';
    const file:File = this.filesToUpload;
    
    if (file == null) {
      this.errorMessage = 'Please Upload File';
      return;
    }else if(!this.validateFile(file)){
      console.log("inside file");
      this.errorMessage = 'Invalid File Format or File Name should contain UserDetails';
    } else {
      const formData: any = new FormData();
        formData.append("file", file[0]);
      
     
      this.userService.saveUpload(formData).subscribe((event: HttpEvent<any>)  => {
        if (event instanceof HttpResponse) //Check if it is a response
            {
              this.errorMessage  = 'File Upload Successfully'+event.body;
              // console.log("status"+event.status);
              // console.log("statustext"+event.statusText);
            }
       
    }, error => {
       this.errorMessage = 'Invalid File Content,Some of the records are not saved successfully';
      console.log("error"+error);
    });

    }
   
  }
  
  fileChangeEvent(fileInput: any) {
    this.filesToUpload = <File>fileInput.target.files;
  }

  
private validateFile(file: File) {
    var fileName=file[0].name;
    console.log("filename"+file[0].name)
    var ext = fileName.substring(fileName.lastIndexOf('.') + 1);
    if (!(ext.toLowerCase() == 'xlsx' || ext.toLowerCase() == 'xls')) {
        return false;
      }else if(!fileName.startsWith('UserDetails')){
        return false;
      }
    return true;
}


}