import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router'
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { DriverListComponent } from './driver-list/driver-list.component';
import { DriverCardComponent } from './driver-card/driver-card.component';
import { FootComponent } from './foot/foot.component';
import { DriverFormComponent } from './driver-form/driver-form.component';
import { DriverDashboardComponent } from './driver-dashboard/driver-dashboard.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { FuelComponent } from './fuel/fuel.component';
import { SpendingComponent } from './spending/spending.component';
import { SpendingDashboadComponent } from './spending-dashboad/spending-dashboad.component';

const routes: Routes = [
  { path: '', component: DashboardComponent },
  { path: 'driver/new', component: DriverFormComponent },
  { path: 'driver/:id/dashboad', component: DriverDashboardComponent }
];

@NgModule({
  declarations: [
    DashboardComponent,
    AppComponent,
    DriverListComponent,
    DriverCardComponent,
    FootComponent,
    DriverFormComponent,
    DriverDashboardComponent,
    FuelComponent,
    SpendingComponent,
    SpendingDashboadComponent
  ],
  imports: [
    HttpClientModule,
    ReactiveFormsModule,
    RouterModule.forRoot(routes),
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
