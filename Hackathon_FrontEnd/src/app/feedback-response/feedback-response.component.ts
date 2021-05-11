import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, } from '@angular/forms';
import { FeedBackService } from '../service/feedback-details.service';
import { FeedBackDetails} from '../model/feedback.model';
import { TokenStorageService } from '../auth/token-storage.service';

@Component({
  selector: 'app-feedback-response',
  templateUrl: './feedback-response.component.html',
  styleUrls: ['./feedback-response.component.css']
})
export class FeedbackResponseComponent implements OnInit {

  feedbackResponseForm: FormGroup;
  feedbackDetails: FeedBackDetails[]=[];
  info: any;
  errorMessage:string;
  checked:boolean=false;

  constructor( private router: Router,
    private feedBackService: FeedBackService,
    private tokenStorage: TokenStorageService,
    private token: TokenStorageService) { }

  ngOnInit() {
    this.info = {
      token: this.token.getToken(),
      eventStatus: this.token.getEventStatus(),
      eventId: this.token.getEvent(),
      response: this.token.getEventResponded()
    };
    if(this.info.eventId == null || this.info.token == null || this.info.eventStatus == null){
      this.router.navigate(['/denied']);
    }
    this.getList();
  }

 
  public getList = () => {
    console.log(this.token.getEventStatus());
    this.feedBackService.getFeedbackDetails(this.info.eventStatus)
    .subscribe(feedback => {
      this.feedbackDetails = feedback as FeedBackDetails[];
    })
  }

  radioChecked(id){
    this.feedbackDetails.forEach(item=>{
      if(item.id !== id){ 
         item.selected = false;
      }else{
         item.selected = true;
         this.checked = true;
      } 
    })
  }

  onSubmit(feedbackDetails: FeedBackDetails[]){
    this.errorMessage ='';
    for(let i =0; i < feedbackDetails.length; i++){
      console.log(i);
      var inputType = feedbackDetails[i].inputType;
      var smileyValue = feedbackDetails[i].smileyValue;
      
      if(inputType == 'Smiley' && smileyValue== null) {
        this.errorMessage ='Value is Required';
      }else if(inputType == 'TextArea' && feedbackDetails[i].feedbackResponse==null) {
        this.errorMessage ='Value is Required';
      }else if(inputType == 'Radio' && !this.checked) {
        this.errorMessage ='Value is Required';
      }
    }
    if(this.errorMessage==''){
      console.log(feedbackDetails);
      this.feedBackService.saveEventResponse(feedbackDetails,this.tokenStorage.getAssociateId(),
      this.tokenStorage.getEvent()).subscribe(data=>{
        this.errorMessage = 'Record Save succesfully';
      
      });
      this.reloadPage();
    }
    }

      reloadPage() {
         this.tokenStorage.saveEventResponded('Y');
        //  window.location.reload();
      }

}