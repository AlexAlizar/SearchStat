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
//      выводится название источника
        
//        pieChartView.chartDescription?.text = site.name
//        barChartView.chartDescription?.text = site.name
        
        barChartView.chartDescription?.text = ""
        pieChartView.chartDescription?.text = ""
        
        var dataEntries: [ChartDataEntry] = []
        let colors: [UIColor] = [UIColor.blue, UIColor.brown, UIColor.green]
        
        for i in 0..<nameArray.count {
            let dataEntry = ChartDataEntry(x: Double(i), y: Double(totalArray[i]))
           
            dataEntries.append(dataEntry)
        }
        
        var dataBarEntries: [BarChartDataEntry] = []
        
        for i in 0..<nameArray.count {
            let dataBarEntry = BarChartDataEntry(x: Double(i), y: Double(totalArray[i]))
//                let dataBarEntry = BarChartDataEntry(x: Double(i), y: Double(totalArray[i]), data: nameArray[i] as AnyObject)
            dataBarEntries.append(dataBarEntry)
        }
        

        
        let barChartDataSet = BarChartDataSet(values: dataBarEntries, label: nameArray.description)
        barChartDataSet.colors = colors
        let barChartData = BarChartData(dataSets: [barChartDataSet])
        barChartView.data = barChartData
        barChartView.animate(xAxisDuration: 2.0, yAxisDuration: 2.0)
        barChartView.xAxis.labelPosition = .bottom
        
        
        
        
        let pieChartDataSet = PieChartDataSet(values: dataEntries, label: nameArray.description)
        let pieChartData = PieChartData(dataSets: [pieChartDataSet])
        pieChartView.data = pieChartData
        pieChartView.animate(xAxisDuration: 2.0, yAxisDuration: 2.0)
        pieChartView.holeColor = UIColor.darkGray
        pieChartDataSet.colors = colors
    }
}
