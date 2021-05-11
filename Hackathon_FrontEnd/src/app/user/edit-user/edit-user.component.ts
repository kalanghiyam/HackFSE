import { Component,Inject } from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {UserRoleService } from '../../service/user-role.service';
import {FormControl, Validators,FormGroup,FormBuilder} from '@angular/forms';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent  {
  userRoleForm: FormGroup;
  toppings = new FormControl("role");
  toppingList: string[] = ['Admin', 'POC', 'Event POC'];
  constructor(private formBuilder: FormBuilder,public dialogRef: MatDialogRef<EditUserComponent>,
    @Inject(MAT_DIALOG_DATA) public user: any, public userRoleService: UserRoleService) { }

    formControl = new FormControl('', [
      Validators.required
      // Validators.email,
    ]);
  
    getErrorMessage() {
      return this.formControl.hasError('required') ? 'Required field' :
        this.formControl.hasError('email') ? 'Not a valid email' :
          '';
    }
 
    cancel(): void {
      this.dialogRef.close();
    }

    save(): void {
      console.log(this.user);
      this.userRoleService.updateUser(this.user);
     }
  
}