import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Driver } from './driver';
import { Observable } from 'rxjs';
import { DriverRequest } from './dto/request/driver-request';

@Injectable({
  providedIn: 'root'
})
export class DriveService {

  drivers: Array<Driver> = Array();

  constructor(private http: HttpClient) { }

  create(driverRequest: DriverRequest):Observable<Driver> {
    return this.http.post<Driver>("http://3.136.158.129:8080/api/driver/register", driverRequest)
  }

  findAll(): Observable<Array<Driver>>  {
    return  this.http.get<Array<Driver>>("http://3.136.158.129:8080/api/driver/list");
  }
}
