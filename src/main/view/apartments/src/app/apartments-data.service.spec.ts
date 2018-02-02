import { TestBed, inject } from '@angular/core/testing';

import { ApartmentsDataService } from './apartments-data.service';

describe('ApartmentsDataService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ApartmentsDataService]
    });
  });

  it('should be created', inject([ApartmentsDataService], (service: ApartmentsDataService) => {
    expect(service).toBeTruthy();
  }));
});
