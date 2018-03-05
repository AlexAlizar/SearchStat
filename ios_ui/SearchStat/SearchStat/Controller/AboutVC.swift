//
//  AboutVC.swift
//  SearchStat
//
//  Created by Roman Trekhlebov on 04.03.2018.
//  Copyright © 2018 Евгений Скилиоти. All rights reserved.
//

import UIKit

class AboutVC: UIViewController {

    
    //Outlets
    
    @IBOutlet weak var backBtn: UIButton!
    
    //Actions
    
    @IBAction func backbtnPressed(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
}
