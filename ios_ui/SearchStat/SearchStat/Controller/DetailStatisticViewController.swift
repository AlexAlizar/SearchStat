//
//  DetailStatisticViewController.swift
//  SearchStat
//
//  Created by Евгений Скилиоти on 20.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import UIKit

class DetailStatisticViewController: UIViewController, UITableViewDelegate, UITableViewDataSource, DetailStatServiceDelegate {
    
    func dataLoadSingleCompleated(day: DayStatsV2) {
        print("Delegate return data for singl day")
        statsArray.append(day)
        print(day)
        reloadCellforName(day.personName)
    }
    
    func dataReturnNil(day: DayStatsV2) {
        print("Delegate return NIL")
        statsArray.append(day)
        print(day)
        reloadCellforName(day.personName)
    }
    
    func reloadCellforName(_ name: String) {
        DispatchQueue.main.async {
            var i = 0
            var index = 0
            for item in self.personNames{
                if item == name {
                    index = i
                    break
                }
                i += 1
            }
            let indexPath = IndexPath(row: index, section: 0)
            
            self.detailTableView.reloadRows(at: [indexPath], with: .automatic)
        }
    }
    
//    func testDetailData() {
//        var unixDay = TimeInterval(86400)
//        let currentDate = Date()
//
//
//        let dateOne = Date(timeIntervalSince1970: TimeInterval(1514505600)) // 29.12.2017
//        let dateTwo = Date(timeIntervalSince1970: TimeInterval(1514592000)) // 30.12.2017
//
//        DetailStatService.instance.requestSingleDetail(forPerson: "Путин", forSite: "meduza.io", date: dateOne)
//
//
//    }

    var unixDay = TimeInterval(86400)
    
    fileprivate let formatter: DateFormatter = {
        let formatter = DateFormatter()
        //MARK: ADDed for fix time zone
        formatter.timeZone = .current
        formatter.calendar = Calendar.current
        formatter.dateFormat = "dd.MM.yyyy"
        return formatter
    }()
    var currentSite: SiteModel?
    var currentDate = Date() {
        didSet {
            self.periodActivated = false
            self.calendarButton.setTitle(self.formatter.string(from: currentDate), for: .normal)
            self.requestData()
        }
    }
    
    private func requestData() {
        for item in personNames {
            DetailStatService.instance.requestSingleDetail(forPerson: item, forSite: currentSite!.name, date: currentDate)
        }
    }
    
    var personNames = [String]()
    var statsArray = [DayStatsV2]()
    
    var personsArray = [GeneralPersonV2]()
    
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
    

//    @IBAction func calendarDetailTapped(_ sender: UIButton) {
//        // Надо ли оно здесь?
//    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        DetailStatService.instance.delegate = self
        
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
        
        self.currentSite = MainService.instance.getSitesArray()[siteIndex]
        //MARK: заполняем таблицу имен для сайта
        for item in (self.currentSite?.personsArray)! {
            personNames.append(item.name)
        }
        //MARK: запрашиваю сервис на предмет первоначальных данных
        
        self.requestData()
        
        
        personsArray = self.currentSite!.personsArray //To del?
        
        self.nameSourceLabel.text = currentSite!.name
        
        let currentDateStringFormatted = formatter.string(from: Date())
        
        calendarButton.setTitle(currentDateStringFormatted, for: .normal)
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return personNames.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "detailcell", for: indexPath)  as! DetailStatisticCell
        
        //MARK: Single date OR Period
        if periodActivated {
            cell.setupDetailCellWith(period: periodDates, person: personsArray[indexPath.row])
        } else {
            
            
           let personName = personNames[indexPath.row]
            var generalPerson = GeneralPersonV2(name: personName, rank: "NIL")
            for item in self.statsArray {
                if item.personName == personName {
                    generalPerson.rank = "\(item.total)"
                }
            }
            cell.setupDetailCellWithPerson(generalPerson)
            
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

