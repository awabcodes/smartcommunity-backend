import { INeedOrder } from 'app/shared/model/need-order.model';

export interface INeed {
  id?: number;
  name?: string;
  info?: string;
  available?: boolean;
  contact?: string;
  imageContentType?: string;
  image?: any;
  quantity?: string;
  orders?: INeedOrder[];
}

export class Need implements INeed {
  constructor(
    public id?: number,
    public name?: string,
    public info?: string,
    public available?: boolean,
    public contact?: string,
    public imageContentType?: string,
    public image?: any,
    public quantity?: string,
    public orders?: INeedOrder[]
  ) {
    this.available = this.available || false;
  }
}
