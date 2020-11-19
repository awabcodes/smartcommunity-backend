export interface INeedOrder {
  id?: number;
  quantity?: string;
  note?: string;
  userLogin?: string;
  userId?: number;
  needName?: string;
  needId?: number;
}

export class NeedOrder implements INeedOrder {
  constructor(
    public id?: number,
    public quantity?: string,
    public note?: string,
    public userLogin?: string,
    public userId?: number,
    public needName?: string,
    public needId?: number
  ) {}
}
