import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { DriveService } from '../drive.service';
import { DriverRequest } from '../dto/request/driver-request';
import { Router } from '@angular/router';

@Component({
  selector: 'app-driver-form',
  templateUrl: './driver-form.component.html',
  styleUrls: ['./driver-form.component.css']
})
export class DriverFormComponent implements OnInit {

  driverForm: FormGroup

  constructor(private router: Router ,private fb: FormBuilder, private driverService: DriveService) { }

  ngOnInit(): void {
    this.driverForm = this.fb.group({
      name: this.fb.control('', [Validators.required]),
      phoneNumber: this.fb.control('', [Validators.required]),
    })
  }

  onCreate() {
    
    var request: DriverRequest = new DriverRequest(
      this.driverForm.controls.name.value,
      "55"+this.driverForm.controls.phoneNumber.value
    )

    this.driverService.create(request).subscribe(driver=> {
      this.driverService.drivers.push(driver)
      this.router.navigateByUrl('/', { skipLocationChange: true });
    })
  }



}
