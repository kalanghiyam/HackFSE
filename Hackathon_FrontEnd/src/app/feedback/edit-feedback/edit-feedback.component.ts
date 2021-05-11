import { Component,Inject } from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FeedBackService } from '../../service/feedback-details.service';
import {FormControl, Validators,FormGroup,FormBuilder} from '@angular/forms';

@Component({
  selector: 'app-edit-feedback',
  templateUrl: './edit-feedback.component.html',
  styleUrls: ['./edit-feedback.component.css']
})
export class EditFeedbackComponent {
  
  constructor(private formBuilder: FormBuilder,public dialogRef: MatDialogRef<EditFeedbackComponent>,
    @Inject(MAT_DIALOG_DATA) public feedback: any, public feedBackService: FeedBackService) { }

    formControl = new FormControl('', [
      Validators.required
      // Validators.email,
    ]);
  
    getErrorMessage() {
      return this.formControl.hasError('required') ? 'Required field' :'';
    }
  
    cancel(): void {
      this.dialogRef.close();
    }

    save(): void {
      console.log(this.feedback);
      this.feedBackService.update(this.feedback);
     }
  
}