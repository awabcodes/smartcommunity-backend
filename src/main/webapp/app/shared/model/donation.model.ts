export interface IDonation {
  id?: number;
  amount?: number;
  receiptNumber?: string;
  collected?: boolean;
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
    public collected?: boolean,
    public userLogin?: string,
    public userId?: number,
    public requestCause?: string,
    public requestId?: number
  ) {
    this.collected = this.collected || false;
  }
}
