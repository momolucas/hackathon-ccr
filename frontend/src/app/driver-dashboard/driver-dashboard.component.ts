import { Component, OnInit } from '@angular/core';
import { DriveService } from '../drive.service';

@Component({
  selector: 'app-driver-dashboard',
  templateUrl: './driver-dashboard.component.html',
  styleUrls: ['./driver-dashboard.component.css']
})
export class DriverDashboardComponent implements OnInit {

  constructor(public driverService: DriveService) { }

  ngOnInit(): void {
  }

}
