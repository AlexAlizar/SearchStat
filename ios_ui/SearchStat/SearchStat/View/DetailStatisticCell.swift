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
        
        
    }
    
    func setupDetailCell(person: Person, dayStat: DayStats) {
      nameLabel.text = person.name
      dayStatlabel.text = String(dayStat.total)
    }
    
}
