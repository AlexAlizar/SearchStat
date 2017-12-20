//
//  CustomButton.swift
//  SearchStat
//
//  Created by Евгений Скилиоти on 19.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import UIKit



class CustomButton: UIButton {
    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        self.layer.borderWidth = 1.0
        self.layer.cornerRadius = self.frame.size.height/2
        self.layer.masksToBounds = true
        self.layer.borderColor = UIColor.white.cgColor
        
        

        
    }
}
