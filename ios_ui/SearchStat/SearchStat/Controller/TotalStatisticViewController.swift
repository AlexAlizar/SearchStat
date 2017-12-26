//
//  TotalStatisticViewController.swift
//  SearchStat
//
//  Created by Евгений Скилиоти on 20.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import UIKit

class TotalStatisticViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {

    var personArray = [Person]()

    
    @IBOutlet weak var backGroundButton: UIButton!
    @IBOutlet weak var nameSourceLabel: UILabel!
    @IBOutlet weak var totalStatTableView: UITableView!
    @IBOutlet weak var calendarView: UIView!
    @IBOutlet weak var calendarViewConstraint: NSLayoutConstraint!
    
    @IBAction func calendarButtonTapped(_ sender: UIButton) {
        calendarViewConstraint.constant = 0
        backGroundButton.alpha = 0.5
        UIView.animate(withDuration: 0.3) {
            self.view.layoutIfNeeded()
        }
    }
 
    @IBAction func hideCalendarButton(_ sender: UIButton) {
        calendarViewConstraint.constant = 375
        backGroundButton.alpha = 0
        
        UIView.animate(withDuration: 0.1) {
            self.view.layoutIfNeeded()
        }
    }
    
    var nameSourceLabelString = " "
    
    override func viewDidLoad() {
        super.viewDidLoad()
        nameSourceLabel.text = nameSourceLabelString
//        nameSourceLabel.text = MainService.instance.lastUpdateDate!
        
    }
    
    func initVC(_ site: Site) {
        personArray = site.personsArray
        nameSourceLabelString = site.name + "    " + DateFormatter.localizedString(from: Date(), dateStyle: .short, timeStyle: .short)
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return personArray.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "totalcell", for: indexPath) as! TotalStatisticCell
        
        cell.setupTotalCell(person: personArray[indexPath.row])
       
        return cell
    }
}
