export interface IDonation {
  id?: number;
  amount?: number;
  receiptNumber?: string;
  userLogin?: string;
  userId?: number;
  requestCause?: string;
  requestId?: number;
}

export class Donation implements IDonation {
  constructor(
    public id?: number,
    public amount?: number,
    public receiptNumber?: string,
    public userLogin?: string,
    public userId?: number,
    public requestCause?: string,
    public requestId?: number
  ) {}
}
