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
        

        
        var dataEntries: [ChartDataEntry] = []
        let colors: [UIColor] = [UIColor.blue, UIColor.brown, UIColor.green]
        
        for i in 0..<nameArray.count {
            let dataEntry = ChartDataEntry(x: Double(i), y: Double(totalArray[i]))
            dataEntries.append(dataEntry)
        }
        
        var dataBarEntries: [BarChartDataEntry] = []
        
        for i in 0..<nameArray.count {
            let dataBarEntry = BarChartDataEntry(x: Double(i), y: Double(totalArray[i]))
            dataBarEntries.append(dataBarEntry)
        }
        
        
        let barChartDataSet = BarChartDataSet(values: dataBarEntries, label: site.name)
        barChartDataSet.colors = colors
        let barChartData = BarChartData(dataSets: [barChartDataSet])
        barChartView.data = barChartData
        
        let pieChartDataSet = PieChartDataSet(values: dataEntries, label: site.name)
        let pieChartData = PieChartData(dataSet: pieChartDataSet)
        pieChartView.data = pieChartData
        pieChartView.holeColor = UIColor.darkGray
        pieChartDataSet.colors = colors
    }
}
