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
    
    var currentDate = Date()
    var personsArray = [Person]()
    
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
        calendarButton.setTitle(self.formatter.string(from: currentDate), for: .normal)
        self.detailTableView.reloadData()
    }
    

    @IBAction func calendarDetailTapped(_ sender: UIButton) {
        
//        UIView.animate(withDuration: 0.2) {
//            self.detailCalendarConstraint.constant = -80
//            self.detailBackGroundBtn.alpha = 0.5
//            self.view.layoutIfNeeded()
//        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.initVC()
    }
    
    private func initVC() {
        //MARK: Чтение выбранного сайта
        let siteIndex = UserDefaults.standard.integer(forKey: SITE_INDEX)
        
        let site = MainService.instance.siteArray![siteIndex]
        personsArray = site.personsArray
        
        self.nameSourceLabel.text = site.name
        
        let formatter = DateFormatter()
        formatter.dateFormat = "dd MMMM yyyy"
        let currentDateStringFormatted = formatter.string(from: Date())
        
        calendarButton.setTitle(currentDateStringFormatted, for: .normal)
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return personsArray.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "detailcell", for: indexPath)  as! DetailStatisticCell
        cell.setupDetailCell(person: personsArray[indexPath.row], forDate: currentDate)

        return cell
    }
}

