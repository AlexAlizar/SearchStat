//
//  DetailStatisticViewController.swift
//  SearchStat
//
//  Created by Евгений Скилиоти on 20.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import UIKit

class DetailStatisticViewController: UIViewController, UITableViewDelegate, UITableViewDataSource, FSCalendarDelegate, FSCalendarDataSource {

    
    //MARK: Variables for calendar
    // к след релизу перенесем на отдельный VC
    
    var unixDay = TimeInterval(86400)
    var currentDate = Date()
    
    fileprivate let gregorian = Calendar(identifier: .gregorian)
    fileprivate let formatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateFormat = "dd MMMM yyyy"
        return formatter
    }()
    
    fileprivate weak var calendar: FSCalendar!
    
    @IBOutlet weak var detailCalendarConstraint: NSLayoutConstraint!
    
    @IBAction func previouslyButton(_ sender: UIButton) {
        currentDate -= unixDay
        calendarButton.setTitle(self.formatter.string(from: currentDate), for: .normal)
        
        //1. запрос массива за выбранную дату
        for i in 0..<personArray.count {
            
            let temp = personArray[i].filteredStats(filteredDate: currentDate)
            if temp != nil {
                dayStatArray[i] = temp!
                //2. reload data
                detailTableView.reloadData()
            }
        }
        
    }
    
    @IBAction func laterButton(_ sender: UIButton) {
        currentDate += unixDay
        calendarButton.setTitle(self.formatter.string(from: currentDate), for: .normal)
        
        //1. запрос массива за выбранную дату
        for i in 0..<personArray.count {
            
            let temp = personArray[i].filteredStats(filteredDate: currentDate)
            if temp != nil {
                dayStatArray[i] = temp!
                //2. reload data
                detailTableView.reloadData()
            }
        }
    }
    
    //=========================
    
    
    @IBOutlet weak var detailTableView: UITableView!
    @IBOutlet weak var calendarButton: UIButton!
    
    var personArray = [Person]()
    var dayStatArray = [DayStats]()
    var nameSourceLabelStr = " "
    
    @IBOutlet weak var nameSourceLabel: UILabel!
    @IBOutlet weak var detailCalendarView: UIView!
    @IBOutlet weak var detailBackGroundBtn: UIButton!
    
    @IBAction func calendarDetailTapped(_ sender: UIButton) {
        
        UIView.animate(withDuration: 0.2) {
            self.detailCalendarConstraint.constant = -80
            self.detailBackGroundBtn.alpha = 0.5
            self.view.layoutIfNeeded()
        }
    }
    
    @IBAction func hideDetailCalendar(_ sender: UIButton) {
        dissMissCalendar()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        nameSourceLabel.text = nameSourceLabelStr
        
        
        // MARK: Вместо Choose the date
        calendarButton.setTitle(self.formatter.string(from: currentDate), for: .normal)
        
        let siteIndex = MainService.instance.lasSiteIndex
        personArray = MainService.instance.siteArray![siteIndex].personsArray
        
        //1. запрос массива за выбранную дату
        for i in 0..<personArray.count {
            
            let temp = personArray[i].filteredStats(filteredDate: currentDate)
            if temp != nil {
                dayStatArray.append(temp!)
                //2. reload data
                detailTableView.reloadData()
            }
        }

    }
    
    
    // MARK: Это тоже перенесем
    
    func calendar(_ calendar: FSCalendar, didSelect date: Date , at monthPosition: FSCalendarMonthPosition) {

        currentDate = date
        calendarButton.setTitle(self.formatter.string(from: date), for: .normal)
        
        
        dissMissCalendar()
        //1. запрос массива за выбранную дату
        for i in 0..<personArray.count {
            
            let temp = personArray[i].filteredStats(filteredDate: date)
            if temp != nil {
                dayStatArray[i] = temp!
                //2. reload data
                detailTableView.reloadData()
            }
            
        }
        

    }
    
    
    
    func dissMissCalendar() {
        UIView.animate(withDuration: 0.1) {
            self.detailCalendarConstraint.constant = 600
            self.detailBackGroundBtn.alpha = 0
            self.view.layoutIfNeeded()
        }
    }
    
    //=========================

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return personArray.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "detailcell", for: indexPath)  as! DetailStatisticCell
        cell.setupDetailCell(person: personArray[indexPath.row], dayStat: dayStatArray[indexPath.row])

        return cell
    }
}

