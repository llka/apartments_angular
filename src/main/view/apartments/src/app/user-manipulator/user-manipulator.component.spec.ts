import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserManipulatorComponent } from './user-manipulator.component';

describe('UserManipulatorComponent', () => {
  let component: UserManipulatorComponent;
  let fixture: ComponentFixture<UserManipulatorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserManipulatorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserManipulatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
