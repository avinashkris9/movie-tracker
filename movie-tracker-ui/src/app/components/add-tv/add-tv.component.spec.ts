import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddTvComponent } from './add-tv.component';

describe('AddTvComponent', () => {
  let component: AddTvComponent;
  let fixture: ComponentFixture<AddTvComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddTvComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddTvComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
