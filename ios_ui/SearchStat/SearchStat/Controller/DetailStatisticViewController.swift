//
//  DetailStatisticViewController.swift
//  SearchStat
//
//  Created by Евгений Скилиоти on 20.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import UIKit

class DetailStatisticViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {

    var unixDay = TimeInterval(86400)
    
    fileprivate let formatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateFormat = "dd MMMM yyyy"
        return formatter
    }()
    
    var currentDate = Date() {
        didSet {
            self.periodActivated = false
            self.calendarButton.setTitle(self.formatter.string(from: currentDate), for: .normal)
            self.detailTableView.reloadData()
        }
    }
    var personsArray = [Person]()
    var periodActivated = false
    var periodDates = [Date]() {
        didSet {
            if periodDates.count > 1 {
                self.periodActivated = true
                let startDate = self.formatter.string(from: periodDates[0])
                let endDate = self.formatter.string(from: periodDates[1])
                self.calendarButton.setTitle("\(startDate) - \(endDate)", for: .normal)
                self.detailTableView.reloadData()
            }
        }
    }
    var observer: NSObjectProtocol?
    
    //Outlets
    @IBOutlet weak var nameSourceLabel: UILabel!
    @IBOutlet weak var detailTableView: UITableView!
    @IBOutlet weak var calendarButton: UIButton!
    
    @IBAction func switchDatePressed(_ sender: UIButton) {
        if sender.currentTitle == "PrewDate" {
            currentDate -= unixDay
        } else {
            currentDate += unixDay
        }
    }
    

    @IBAction func calendarDetailTapped(_ sender: UIButton) {
        
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.initVC()
    }
    
    // MARK: Получаем данные с CalendarVC
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        //MARK: Здесь дата
       observer = NotificationCenter.default.addObserver(forName: .sendDate , object: nil, queue: OperationQueue.main) { (notification) in
            let detailVC = notification.object as! CalendarVC
            self.currentDate = detailVC.selectedDay
        }
        
        //MARK: Здесь массив с датами за период
        NotificationCenter.default.addObserver(forName: .sendPeriod , object: nil, queue: OperationQueue.main) { (notification) in
            let detailVC = notification.object as! CalendarVC
            self.periodDates = detailVC.selectedPeriod
        }
    }
    
    //MARK: Удаляем обзервер
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        
        if let observer = observer {
            NotificationCenter.default.removeObserver(observer)
        }
    }
    
    private func initVC() {
        //MARK: Чтение выбранного сайта
        let siteIndex = UserDefaults.standard.integer(forKey: SITE_INDEX)
        
        let site = MainService.instance.siteArray![siteIndex]
        personsArray = site.personsArray
        
        self.nameSourceLabel.text = site.name
        
        let currentDateStringFormatted = formatter.string(from: Date())
        
        calendarButton.setTitle(currentDateStringFormatted, for: .normal)
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return personsArray.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "detailcell", for: indexPath)  as! DetailStatisticCell
        
        //MARK: Single date OR Period
        if periodActivated {
            cell.setupDetailCellWith(period: periodDates, person: personsArray[indexPath.row])
        } else {
            cell.setupDetailCell(person: personsArray[indexPath.row], forDate: currentDate)
        }
        

        return cell
    }
}

