//
//  SourceCell.swift
//  SearchStat
//
//  Created by Roman Trekhlebov on 21.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import UIKit

class SourceCell: UITableViewCell {

    @IBOutlet weak var siteLbl: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }
    
    func setupCell(site: Site) {
        siteLbl.text = site.name
    }

}
