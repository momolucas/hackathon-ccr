import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SpendingDashboadComponent } from './spending-dashboad.component';

describe('SpendingDashboadComponent', () => {
  let component: SpendingDashboadComponent;
  let fixture: ComponentFixture<SpendingDashboadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SpendingDashboadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpendingDashboadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
