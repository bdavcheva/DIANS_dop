import {Component, OnInit} from '@angular/core';
import {Chart, registerables} from "chart.js";
import {HttpClient} from "@angular/common/http";
import {WebSocketService} from "../websocket-service";
import {debounceTime, map, switchMap} from "rxjs";
import {StockValue} from "./stock-model";
import {FormControl, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";


@Component({
  selector: 'app-root',
  templateUrl: './stocks.component.html',
  styleUrls: ['./stocks.component.css'],
  imports: [
    ReactiveFormsModule,
    NgForOf,
    NgIf
  ],
  standalone: true
})
export class StocksComponent implements OnInit {
  stockChart: any;
  volumeChart: any;
  isLoading = false;
  stockData: string | null = null;
  options: string[] = ['AAPL', 'EUR/USD', 'ETH/BTC', 'NVDA'];
  count: number = 8;
  form = new FormGroup({
    selectedOption: new FormControl('')
  });

  constructor(private httpClient: HttpClient, private webSocketService: WebSocketService) {
    Chart.register(...registerables);
  }

  ngOnInit(): void {
    this.webSocketService.getStockUpdates()
      .pipe(
        debounceTime(500),
        map(it => {
          return it.symbol;
        }),
        switchMap(symbol => this.getStockData(symbol)))
      .subscribe(resp => {
        this.stockData = JSON.parse(JSON.stringify(resp)).data
        this.isLoading = false;
        this.createChart();
        this.createVolumeChart()
        this.startCounter();

      });

    this.form.controls.selectedOption.valueChanges.subscribe(value => {
      this.isLoading = true;
      this.stockChart?.destroy();
      this.volumeChart?.destroy();

      this.count--;
      this.httpClient.get(`/api/fetch-stock?symbol=${value}`).subscribe();
    })
  }

  getStockData(symbol: string) {
    return this.httpClient.get(`/api/data?symbol=${symbol}`)
  }

  createChart() {
    const data = JSON.parse(this.stockData!!)
    const labels = data.values.map((it: StockValue) => it.datetime);
    const closePrices = data.values.map((it: StockValue) => it.close);
    this.stockChart = new Chart("stockChartCanvas", {
      type: 'line',
      data: {
        labels: labels.reverse(),
        datasets: [{
          label: 'Price ($)',
          data: closePrices.reverse(),
          borderColor: 'blue',
          backgroundColor: 'rgba(0, 123, 255, 0.2)',
          borderWidth: 2,
          fill: true
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {display: true},
        },
        scales: {
          x: {
            type: 'category',
            title: {display: true, text: 'Date'}
          },
          y: {
            title: {display: true, text: 'Price ($)'}
          }
        }
      }
    });
  }

  createVolumeChart(): void {

    const data = JSON.parse(this.stockData!!)
    const ctx = document.getElementById('volumeChart') as HTMLCanvasElement;
    this.volumeChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels: data.values.map((it: StockValue) => it.datetime),
        datasets: [{
          label: 'Stock Volume',
          data: data.values.map((it: StockValue) => it.volume),
          backgroundColor: 'green'
        }]
      },
      options: {
        responsive: true,
        scales: {
          x: {type: 'category'},
          y: {beginAtZero: true}
        }
      }
    });
  }

  startCounter() {
    setInterval(() => {
      this.count = 8;
    }, 60000);
  }

}
