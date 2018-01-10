//
//  RegistrationViewController.swift
//  SearchStat
//
//  Created by Евгений Скилиоти on 20.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import UIKit

class RegistrationViewController: UIViewController {

    
    @IBAction func closeBtnPressed(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }
    
    @IBAction func userPhotoBtn(_ sender: UIButton) {
    }
    
    @IBOutlet weak var userPhoto: UIButton!
    @IBOutlet weak var userNameTextField: CustomTextField!
    @IBOutlet weak var emailTextField: CustomTextField!
    @IBOutlet weak var passwordTextField: CustomTextField!
    @IBOutlet weak var repeatPassTextField: CustomTextField!
    
    @IBAction func createUserBtn(_ sender: CustomButton) {
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
       
    }
}
