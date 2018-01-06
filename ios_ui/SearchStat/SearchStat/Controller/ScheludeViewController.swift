//
//  ScheludeViewController.swift
//  SearchStat
//
//  Created by Евгений Скилиоти on 23.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import UIKit
import Charts

class ScheludeViewController: UIViewController, ChartViewDelegate {
    
    var personArray: [Person] = []
    
    
    @IBOutlet weak var pieChartView: PieChartView!
    @IBOutlet weak var barChartView: BarChartView!
    

    override func viewDidLoad() {
        super.viewDidLoad()
//        pieChartView.delegate = self
//        barChartView.delegate = self
        initVCAndUpdateChart()
    }
    
    private func initVCAndUpdateChart() {
        //MARK: Чтение выбранного сайта
        let siteIndex = UserDefaults.standard.integer(forKey: SITE_INDEX)
        //MARK: Чтение даты обновления данных
//        let dateString = UserDefaults.standard.string(forKey: DATA_UPDATE_STRING)
        
        let site = MainService.instance.siteArray![siteIndex]
        personArray = site.personsArray

        
        let totalArray = personArray.enumerated().map{ index, element in
                return element.total
            }
     
        
        let nameArray = personArray.enumerated().map { index, element in
            return element.name
        }
      
        pieChartView.chartDescription?.text = site.name
        barChartView.chartDescription?.text = site.name
        
//        var dataEntries: [BarChartDataEntry] = []
        
//        for i in 0..<personArray.count {
//            let dataEntry = BarChartDataEntry(x: Double(i), y: Double(totalArray[i]))
//            dataEntries.append(dataEntry)
//        }
//
//        let chartDataSet = BarChartDataSet(values: dataEntries, label: "")
//        let barData = BarChartData(dataSets: [chartDataSet])
//        barChartView.data = barData
        
        
        
//        Бар - график
        
                let dataEntries1 = BarChartDataEntry(x: Double(1), y: Double(totalArray[0]))
                let dataEntries2 = BarChartDataEntry(x: Double(2), y: Double(totalArray[1]))
                let dataEntries3 = BarChartDataEntry(x: Double(3), y: Double(totalArray[2]))
        
                let chartDataSet1 = BarChartDataSet(values: [dataEntries1], label: nameArray[0])
                let chartDataSet2 = BarChartDataSet(values: [dataEntries2], label: nameArray[1])
                chartDataSet2.colors = [UIColor.brown]
                let chartDataSet3 = BarChartDataSet(values: [dataEntries3], label: nameArray[2])
                chartDataSet3.colors = [UIColor.green]
        
                let dataSets = [chartDataSet1,chartDataSet2,chartDataSet3]
                let chartData = BarChartData(dataSets: dataSets)
                   barChartView.data = chartData
//      Диаграмма
        let dataPie1 = PieChartDataEntry(value: Double(totalArray[0]), label: nameArray[0])
        let dataPie2 = PieChartDataEntry(value: Double(totalArray[1]), label: nameArray[1])
        let dataPie3 = PieChartDataEntry(value: Double(totalArray[2]), label: nameArray[2])
        
        let chartData1 = PieChartDataSet(values: [dataPie1], label: nameArray[0])
        let chartData2 = PieChartDataSet(values: [dataPie2], label: nameArray[1])
//        chartData2.colors = [UIColor.green]
        let chartData3 = PieChartDataSet(values: [dataPie3], label: nameArray[2])
//        chartData3.colors = [UIColor.brown]
        
        let dataPieSets = [chartData1,chartData2,chartData3]
        let chartPieData = PieChartData(dataSets: dataPieSets)
        pieChartView.data = chartPieData

        
    }
}
