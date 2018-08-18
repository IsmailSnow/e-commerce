import { TestBed, inject } from '@angular/core/testing';

import { CartServiceService } from './cart-service.service';

describe('CartServiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CartServiceService]
    });
  });

  it('should be created', inject([CartServiceService], (service: CartServiceService) => {
    expect(service).toBeTruthy();
  }));
});
