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
        //MARK: ADDed for fix time zone
        formatter.timeZone = .current
        formatter.calendar = Calendar.current
        formatter.dateFormat = "dd.MM.yyyy"
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
                let endDate = self.formatter.string(from: periodDates.last!)
                self.calendarButton.setTitle("\(startDate) - \(endDate)", for: .normal)
                self.detailTableView.reloadData()
            }
        }
    }
    
    var observerDate: NSObjectProtocol?
    var observerPeriod: NSObjectProtocol?
    
    //Outlets
    @IBOutlet weak var nameSourceLabel: UILabel!
    @IBOutlet weak var detailTableView: UITableView!
    @IBOutlet weak var calendarButton: UIButton!
    
    @IBAction func backBtnPressed(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }
    
    @IBAction func switchDatePressed(_ sender: UIButton) {
        if sender.currentTitle == "PrewDate" {
            currentDate -= unixDay
            print(self.currentDate)
        } else {
            currentDate += unixDay
            print(self.currentDate)
        }
    }
    

    @IBAction func calendarDetailTapped(_ sender: UIButton) {
        // Надо ли оно здесь?
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.initVC()
    }
    
    // MARK: Получаем данные с CalendarVC
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        //MARK: Здесь дата
       observerDate = NotificationCenter.default.addObserver(forName: .sendDate , object: nil, queue: OperationQueue.main) { (notification) in
            let detailVC = notification.object as! CalendarVC
            self.currentDate = detailVC.selectedDay
        print(self.currentDate)
        }
        
        //MARK: Здесь массив с датами за период
       observerPeriod = NotificationCenter.default.addObserver(forName: .sendPeriod , object: nil, queue: OperationQueue.main) { (notification) in
            let detailVC = notification.object as! CalendarVC
            self.periodDates = detailVC.selectedPeriod
            print(self.periodDates)
        }
    }
    
    //MARK: Удаляем обзерверы
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        
        if let observer = observerDate {
            NotificationCenter.default.removeObserver(observer)
        }
        
        if let observer = observerPeriod {
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
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == TO_DETAIL_CHART {
            if let destVC: DetailChartViewController = segue.destination as? DetailChartViewController {
                destVC.currentDate = currentDate
                destVC.periodDates = periodDates
                destVC.periodActivate = periodActivated
            }
        }
    }
}

