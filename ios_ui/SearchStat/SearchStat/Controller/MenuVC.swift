//
//  MenuVC.swift
//  SearchStat
//
//  Created by Roman Trekhlebov on 10.01.2018.
//  Copyright © 2018 Евгений Скилиоти. All rights reserved.
//

import UIKit

class MenuVC: UIViewController {

    @IBAction func loginBtnPressed(_ sender: Any) {
        
        performSegue(withIdentifier: TO_LOGIN, sender: nil)
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        //MARK: Ширина Ьокового меню
        self.revealViewController().rearViewRevealWidth = self.view.frame.size.width - 60

        
    }



}
