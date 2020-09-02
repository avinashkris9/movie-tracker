import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TvEditComponent } from './tv-edit.component';

describe('TvEditComponent', () => {
  let component: TvEditComponent;
  let fixture: ComponentFixture<TvEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TvEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TvEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
