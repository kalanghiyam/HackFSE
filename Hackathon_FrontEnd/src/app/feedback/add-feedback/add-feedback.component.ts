import { Component, OnInit } from '@angular/core';
import { FormGroup,FormBuilder, Validators } from '@angular/forms';
import {FeedBackDetails} from '../../model/feedback.model';
import {FeedBackService } from '../../service/feedback-details.service';

@Component({
  selector: 'app-add-feedback',
  templateUrl: './add-feedback.component.html',
  styleUrls: ['./add-feedback.component.css']
})
export class AddFeedbackComponent implements OnInit {
  feedBackForm: FormGroup;
  feedBack: Object ={};
  submitted = false;
  constructor(private formBuilder: FormBuilder,private feedBackService:FeedBackService) { }

  ngOnInit() {
    this.feedBackForm = this.formBuilder.group({
      feedBackDesc: ['', Validators.required],
      status: ['', Validators.required],
      inputType: ['', Validators.required]
    });
  }

  
  add(feedBack: FeedBackDetails): void {
   
    this.feedBackService.add(feedBack).subscribe(data => {alert("FeedBack created successfulyy");});
  }
  
  onSubmit(feedBack: FeedBackDetails) {
        this.submitted = true;
        if (this.feedBackForm.invalid) {
            return;
        }else{
           this.add(feedBack);
           this.submitted = false;
           this.feedBackForm.reset();
        }
  }
}
