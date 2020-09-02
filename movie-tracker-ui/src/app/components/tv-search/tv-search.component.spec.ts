import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TvSearchComponent } from './tv-search.component';

describe('TvSearchComponent', () => {
  let component: TvSearchComponent;
  let fixture: ComponentFixture<TvSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TvSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TvSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
