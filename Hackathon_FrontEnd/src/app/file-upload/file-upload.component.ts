import { Component, OnInit } from '@angular/core';
import { HttpEvent,HttpResponse } from '@angular/common/http';
import { UploadFileService } from '../service/file-upload.service';
import { FormGroup,FormBuilder, Validators } from '@angular/forms';
import { EventSummaryDetails } from '../model/report.model';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent implements OnInit {
 
  uploadForm: FormGroup;
  filesToUpload: Array<File> = [];
  errorMessage:string;

  eventSummaryDetails : EventSummaryDetails[];
  constructor( private formBuilder: FormBuilder,private uploadService: UploadFileService) { }
 
  ngOnInit() {
    this.uploadForm = this.formBuilder.group({
      filesToUpload: ['', Validators.required]
    });
  }

  upload() {
    this.errorMessage = '';
    const files: Array<File> = this.filesToUpload;
    
    if (files.length==0) {
      this.errorMessage = 'Please Upload File';
      return;
    }else if(!this.validateFile(files)){
      console.log("inside file");
      this.errorMessage = 'Invalid File Format';
    } else if(!this.validateFileName(files)){
      
      this.errorMessage = 'File name should be OutReach Event Information or Outreach Events Summary,'
      +'Volunteer_Enrollment Details_Not_Attend or Volunteer_Enrollment Details_Unregistered';
    }else {
      const formData: any = new FormData();
      for(let i =0; i < files.length; i++){
        formData.append("file", files[i]);
      }
     
      this.uploadService.save(formData).subscribe((event: HttpEvent<any>)  => {
        if (event instanceof HttpResponse) //Check if it is a response
            {
              this.errorMessage = 'File Upload Successfully'+event.body;
              // console.log("status"+event.status);
              // console.log("statustext"+event.statusText);
            }
       
    }, error => {
       this.errorMessage = 'Invalid File Content,Some of the records are not saved successfully';
      console.log("error"+error);
    });

    }
   
  }

private validateFile(fileList: File[]) {
  for(let i =0; i < fileList.length; i++){
    var fileName=fileList[i].name;
    var ext = fileName.substring(fileName.lastIndexOf('.') + 1);
    if (!(ext.toLowerCase() == 'xlsx' || ext.toLowerCase() == 'xls')) {
      console.log("inside -----");
        return false;
      }
    }
    return true;
}

private validateFileName(fileList: File[]) {
  for(let i =0; i < fileList.length; i++){
    var fileName=fileList[i].name;
    if (!(fileName.startsWith('OutReach Event Information') 
          || fileName.startsWith('Outreach Events Summary')
          || fileName.startsWith('Volunteer_Enrollment Details_Not_Attend')
          || fileName.startsWith('Volunteer_Enrollment Details_Unregistered'))) {
            return false;
    }
  }
  return true;
}

  fileChangeEvent(fileInput: any) {
    this.filesToUpload = <Array<File>>fileInput.target.files;
  }
}
