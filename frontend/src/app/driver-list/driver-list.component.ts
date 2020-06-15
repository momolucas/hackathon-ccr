import { Component, OnInit } from '@angular/core';
import { DriveService } from '../drive.service';
import { Driver } from '../driver';

@Component({
  selector: 'app-driver-list',
  templateUrl: './driver-list.component.html',
  styleUrls: ['./driver-list.component.css']
})
export class DriverListComponent implements OnInit {
  constructor(public driverService: DriveService) {}

  ngOnInit(): void {
    // tratablhar com eventos para ao cadastrar novo motorista, esse componente deve ser atualizado
    this.driverService.findAll().subscribe(drivers=> this.driverService.drivers = drivers)
  }

}
