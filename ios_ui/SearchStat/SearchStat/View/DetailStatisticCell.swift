//
//  DetailStatisticCell.swift
//  SearchStat
//
//  Created by Евгений Скилиоти on 23.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import UIKit


class DetailStatisticCell: UITableViewCell {
    
    
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var dayStatlabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        dayStatlabel.layer.borderColor = UIColor.lightGray.cgColor
        dayStatlabel.layer.borderWidth = 1
        
        nameLabel.layer.borderWidth = 1
        nameLabel.layer.borderColor = UIColor.lightGray.cgColor
        
    }
    
    
    func setupDetailCellWithPerson(_ person: GeneralPersonV2) {
        nameLabel.text = person.name
        dayStatlabel.text = person.rank
        
    }
    
    func setupDetailCell(person: GeneralPersonV2, forDate date: Date) {
        nameLabel.text = person.name
        
//        if let stats = person.filteredStats(filteredDate: date) {
//            dayStatlabel.text = String(stats.total)
//        } else {
//            dayStatlabel.text = "NoDataForSelectedDate"
//        }
    }
    
    func setupDetailCellWith(period periodArray: [Date], person: GeneralPersonV2) {
        nameLabel.text = person.name
        let arrayCount = periodArray.count
//        if let stats = person.filteredPeriodStats(startDate: periodArray[0], endDate: periodArray[arrayCount - 1]) {
//            dayStatlabel.text = String(stats.total)
//            
//        } else {
//            dayStatlabel.text = "PeriodDataNotFound"
//        }


    }
    
}
