//
//  CalendarVC.swift
//  SearchStat
//
//  Created by Timofey Kazaev on 30.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import UIKit

class CalendarVC: UIViewController,FSCalendarDelegate, FSCalendarDataSource {

    var tapCounter = 0
    var selectedDay = Date()
    var selectedPeriod = [Date]()
    
    
    @IBOutlet weak var calendar: FSCalendar!
    @IBOutlet weak var dayPeriodBtn: UIButton!
   
    //MARK: Проверяем статус кнопки и меняем тайтл
    @IBAction func setPeriodBtn(_ sender: UIButton) {
        
    //MARK: можно включить слайд выбор
//        calendar.swipeToChooseGesture.isEnabled = true

        if calendar.allowsMultipleSelection == false {
            calendar.allowsMultipleSelection = true
        } else if calendar.allowsMultipleSelection == true {
            calendar.allowsMultipleSelection = false
        }
        
        if calendar.allowsMultipleSelection == true {
            dayPeriodBtn.setTitle("Period", for: .normal)
        }  else if calendar.allowsMultipleSelection == false{
            dayPeriodBtn.setTitle("Day", for: .normal)
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
            calendar.allowsMultipleSelection = false
            dayPeriodBtn.setTitle("Day", for: .normal)
        
    }
    
    func calendar(_ calendar: FSCalendar, didSelect date: Date, at monthPosition: FSCalendarMonthPosition) {
        
        if calendar.allowsMultipleSelection == false {
            setDate(date: date)
        } else if calendar.allowsMultipleSelection == true{
            setPeriod(date: date)
        }
    }
   
    // MARK: Выбираем дату и отправляем ее на DetailStatisticVC
    func setDate(date: Date) {
        selectedDay = date
        NotificationCenter.default.post(name: .sendDate, object: self)
        dismiss(animated: true, completion: nil)
    }
    
    //MARK: Выбираем период и отправляем его на DetailStatisticVC
    func setPeriod(date: Date) {
        
        var firstDay = calendar.selectedDates.first
        var lastDay = calendar.selectedDates.last
        
        let firstUnixDay = firstDay?.timeIntervalSince1970
        let lastUnixDay = lastDay?.timeIntervalSince1970
        
        //MARK: Проверям даты, если первая больше последней, меняем их местами
        if Int(lastUnixDay!) <= Int(firstUnixDay!) {
            changeValuesOfVariables(&firstDay!, &lastDay!)
        }
        
        //MARK: Считаем касания
        tapCounter = tapCounter + 1
        if tapCounter == 2 {
            selectedPeriod = generateDatesArrayBetweenTwoDates(startDate: firstDay!, endDate: lastDay!)
            NotificationCenter.default.post(name: .sendPeriod, object: self)
            dismiss(animated: true, completion: nil)            
        }
    }
    
    //MARK: Генерим массив дат
    func generateDatesArrayBetweenTwoDates(startDate: Date , endDate:Date) ->[Date] {
       
        var datesArray = [Date]()
        var startDate = startDate
        var calendar = NSCalendar.current
        // Added
        calendar.timeZone = .current
   
        while startDate <= endDate {
            datesArray.append(startDate)
            startDate = calendar.date(byAdding: .day, value: 1, to: startDate)!
        }
        return datesArray
    }
    
    //MARK: Подменяем значения переменных
    func changeValuesOfVariables(_ a: inout Date, _ b: inout Date) {
        
        let temporaryA = a
        a = b
        b = temporaryA
    }
}

extension Notification.Name {
    
    static let sendDate = Notification.Name(rawValue: "sendDate")
    static let sendPeriod = Notification.Name(rawValue: "sendPeriod")
}



