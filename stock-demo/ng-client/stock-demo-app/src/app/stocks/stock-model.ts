export interface StockData {
  meta: MetaData;
  values: StockValue[];
  status: string;
}

export interface MetaData {
  symbol: string;
  interval: string;
  currency: string;
}

export interface StockValue {
  datetime: string;
  open: number;
  high: number;
  low: number;
  close: number;
  volume: number;
}

export interface DataResponse{
  data: StockData
}
