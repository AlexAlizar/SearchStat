//
//  DetailStatisticViewController.swift
//  SearchStat
//
//  Created by Евгений Скилиоти on 20.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import UIKit

class DetailStatisticViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
//    func dataArrayCompleated(array: [DayStatsV2], requestID: String) {
//        print("H")
//    }
//
//    func dataArrayReturnZero(array: [DayStatsV2], requestID: String) {
//        print("h")
//    }
//
//
//    func dataLoadSingleCompleated(day: DayStatsV2) {
//        print("Delegate return data for singl day")
//        statsArray.append(day)
//        print(day)
//        reloadCellforName(day.personName)
//    }
//
//    func dataReturnNil(day: DayStatsV2) {
//        print("Delegate return NIL")
//        statsArray.append(day)
//        print(day)
//        reloadCellforName(day.personName)
//    }
    
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
    
    
    private func requestSingle(person: String, site: String, date: Date) {
        DetailStatService.instance.requestDetailFromServiceV2(forPerson: person, forSite: site, startDate: date, endDate: date) { (success, error, result) in
            guard error == nil else {
                print(error!)
                return
            }
            if success {
                print(result!)
                if result!.count == 0 {
                    print("No data for: \(person) for: \(date)")
                    self.reloadCellforName(person)
                }else {
                    self.statsArray.append((result?.first)!)
                    self.reloadCellforName(person)
                }
            }else {
                print("no error, but succes is false")
            }
        }
    }
    
    var periodResult = [String : Int?]()
    
    private func requestPeriod(person: String, site: String, dateArray: [Date]) {
        DetailStatService.instance.requestDetailFromServiceV2(forPerson: person, forSite: site, startDate: periodDates.first! , endDate: periodDates.last!) { (success, error, result) in
            guard error == nil else {
                print(error!)
                return
            }
            if success {
                print(result!)
                if result!.count == 0 {
                    print("No PERIOD data for: \(person) for: \(self.calendarButton.currentTitle!)")
                    self.periodResult = [person : nil]
                    self.reloadCellforName(person)
                }else {
                    var total = 0
                    for item in result! {
                        total += item.total
                    }
                    self.periodResult[person] = total
                    self.reloadCellforName(person)

                }
            }else {
                print("no error, but succes is false")
            }
        }
    }
    private func requestData() {
        for item in personNames {
            self.statsArray.removeAll()
            self.periodResult.removeAll()
            DispatchQueue.main.async {
                self.detailTableView.reloadData()
            }
            
            if periodActivated {
               self.requestPeriod(person: item, site: currentSite!.name, dateArray: periodDates)
            } else {
                
                self.requestSingle(person: item, site: currentSite!.name, date: currentDate)
//                DetailStatService.instance.requestSingleDetail(forPerson: item, forSite: currentSite!.name, date: currentDate)

            }
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
                //тут будет запрос с передачей массива [Date]
                self.requestData()
                
//                self.detailTableView.reloadData()
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
//        DetailStatService.instance.delegate = self
        
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
        let personName = personNames[indexPath.row]
        if periodActivated {
            
            if let total = periodResult[personName] {
                cell.setupDetailCellWithPeriod(person: personName, total: total)
            } else {
                cell.setupDetailCellWithPeriod(person: personName, total: nil)
            }
            
        } else {

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

