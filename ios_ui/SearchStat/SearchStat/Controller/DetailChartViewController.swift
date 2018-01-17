//
//  DetailChartViewController.swift
//  SearchStat
//
//  Created by Евгений Скилиоти on 06.01.2018.
//  Copyright © 2018 Евгений Скилиоти. All rights reserved.
//

import UIKit
import Charts


class DetailChartViewController: UIViewController {
    
    @IBOutlet weak var pieChartView: PieChartView!
    @IBOutlet weak var barChartView: BarChartView!
    
    
    var personArray = [Person]()
    var periodDates = [Date]()
    var currentDate = Date()
    var periodActivate: Bool!
    
    
    @IBAction func backToDetailStat(_ sender: Any) {
         self.dismiss(animated: true, completion: nil)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        initVCandUpdateCharts()
    }
    

    
    func initVCandUpdateCharts() {
        
        let siteIndex = UserDefaults.standard.integer(forKey: SITE_INDEX)
        //MARK: Чтение даты обновления данных
        //        let dateString = UserDefaults.standard.string(forKey: DATA_UPDATE_STRING)
        
        let site = MainService.instance.siteArray![siteIndex]
        personArray = site.personsArray
        
        
        
        pieChartView.chartDescription?.text = ""
        barChartView.chartDescription?.text = ""
        
        var dataEntries: [ChartDataEntry] = []
        if periodActivate {
            
            for i in 0..<personArray.count {
                let dataEntry = ChartDataEntry(x: Double(i), y: Double((personArray[i].filteredPeriodStats(startDate: periodDates[0], endDate: periodDates[periodDates.count - 1])?.total)!))
                dataEntries.append(dataEntry)
            }
            
        } else {
            
            for i in 0..<personArray.count {
                let dataEntry = ChartDataEntry(x: Double(i), y: Double((personArray[i].filteredStats(filteredDate: currentDate)?.total)!))
                dataEntries.append(dataEntry)
            }
        }
            
            
            
        let pieChartDataSet = PieChartDataSet(values: dataEntries, label: "")
        pieChartDataSet.colors = ChartColorTemplates.colorful()
        
        let pieChartData = PieChartData(dataSets: [pieChartDataSet])
        pieChartView.data = pieChartData
        
       

        var dataBarEntry: [BarChartDataEntry] = []
        
        if periodActivate {
            
            for i in 0..<personArray.count {
                let dataEntry = BarChartDataEntry(x: Double(i), y: Double((personArray[i].filteredPeriodStats(startDate: periodDates[0], endDate: periodDates[periodDates.count - 1])?.total)!))
                dataBarEntry.append(dataEntry)
            }
            
        } else {
            
            for i in 0..<personArray.count {
                let dataEntry = BarChartDataEntry(x: Double(i), y: Double((personArray[i].filteredStats(filteredDate: currentDate)?.total)!))
                dataBarEntry.append(dataEntry)
            }
        }
        
        let barChartDataSet = BarChartDataSet(values: dataBarEntry, label: "")
        let barChartData = BarChartData(dataSets: [barChartDataSet])
        barChartView.data = barChartData
        barChartView.xAxis.labelPosition = .bottom
        barChartView.xAxis.valueFormatter = IndexAxisValueFormatter(values: personArray.enumerated().map {index, element in return element.name})
        barChartDataSet.colors = ChartColorTemplates.colorful()
        
        
    }
}

