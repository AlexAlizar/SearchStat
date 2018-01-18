//
//  TotalStatisticCell.swift
//  SearchStat
//
//  Created by Евгений Скилиоти on 22.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import UIKit


class TotalStatisticCell: UITableViewCell {
    
    @IBOutlet weak var personNameLabel: UILabel!
    @IBOutlet weak var totalStatLabel: UILabel!
    
    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        personNameLabel.layer.borderWidth = 1
        personNameLabel.layer.borderColor = UIColor.lightGray.cgColor
        
        totalStatLabel.layer.borderWidth = 1
        totalStatLabel.layer.borderColor = UIColor.lightGray.cgColor
    }
    
    func setupTotalCell(person: GeneralPersonV2) {
        personNameLabel.text = person.name
        totalStatLabel.text = String(person.rank)
    }
}
