//
//  DetailStatisticViewController.swift
//  SearchStat
//
//  Created by Евгений Скилиоти on 20.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import UIKit

class DetailStatisticViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {

    
    
    @IBOutlet weak var detailTableView: UITableView!
    
    @IBOutlet weak var calendarButton: UIButton!
    
    @IBAction func calendarbtnPressed(_ sender: Any) {
        
        let calendarVC = CalendarVC()
        calendarVC.modalPresentationStyle = .custom
        self.present(calendarVC, animated: true, completion: nil)
    }
    
    var personArray = [Person]()
    var dayStatArray = [DayStats]()
    
    @IBOutlet weak var nameSourceLabel: UILabel!
    
    @IBOutlet weak var detailCalendarView: UIView!
    
    @IBOutlet weak var detailBackGroundBtn: UIButton!
    @IBOutlet weak var detailCalendarConstraint: NSLayoutConstraint!
   
    @IBAction func calendarDetailTapped(_ sender: UIButton) {
        detailCalendarConstraint.constant = 0
        detailBackGroundBtn.alpha = 0.5
        UIView.animate(withDuration: 0.3) {
            self.view.layoutIfNeeded()
        }
    }
    
    @IBAction func hideDetailCalendar(_ sender: UIButton) {
        detailCalendarConstraint.constant = 375
        detailBackGroundBtn.alpha = 0
        UIView.animate(withDuration: 0.1) {
            self.view.layoutIfNeeded()
        }
    }
    

    
    override func viewDidLoad() {
        super.viewDidLoad()

        calendarButton.setTitle(MainService.instance.lastUpdateDate, for: .normal)
        
        MainService.instance.getPerson { (result) in
            if result {
                personArray = MainService.instance.personArray!
            }
        }
        MainService.instance.getDayStat { (result) in
            if result {
                dayStatArray = MainService.instance.dayStatArray!
            }
        }
        
    }
    

    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return personArray.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "detailcell", for: indexPath)  as! DetailStatisticCell
        cell.setupDetailCell(person: personArray[indexPath.row], dayStat: dayStatArray[indexPath.row])

        return cell
    }
}
