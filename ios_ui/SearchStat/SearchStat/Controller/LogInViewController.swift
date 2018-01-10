//
//  LogInViewController.swift
//  SearchStat
//
//  Created by Евгений Скилиоти on 19.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import UIKit

class LogInViewController: UIViewController {
    
    @IBOutlet weak var emailTextField: CustomTextField!
    @IBOutlet weak var passwordTextField: CustomTextField!
    
    @IBAction func restorePasswordBtn(_ sender: UIButton) {
    }
    
    @IBAction func logInBtn(_ sender: CustomButton) {
        //authorization...
        self.dismiss(animated: true, completion: nil)
    }
        
    override func viewDidLoad() {
        super.viewDidLoad()
        
    }


}
