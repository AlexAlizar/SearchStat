//
//  CustomTextField.swift
//  SearchStat
//
//  Created by Евгений Скилиоти on 22.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import UIKit


class CustomTextField: UITextField {
    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        layer.borderWidth = 1
        layer.borderColor = UIColor.white.cgColor
        layer.cornerRadius = 5
    }
}
