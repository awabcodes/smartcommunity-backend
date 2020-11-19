import { IDonation } from 'app/shared/model/donation.model';

export interface IDonationRequest {
  id?: number;
  cause?: string;
  paymentInfo?: string;
  info?: string;
  totalAmount?: number;
  contact?: string;
  amountRaised?: number;
  donations?: IDonation[];
}

export class DonationRequest implements IDonationRequest {
  constructor(
    public id?: number,
    public cause?: string,
    public paymentInfo?: string,
    public info?: string,
    public totalAmount?: number,
    public contact?: string,
    public amountRaised?: number,
    public donations?: IDonation[]
  ) {}
}
