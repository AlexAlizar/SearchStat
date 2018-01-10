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
    
    @IBOutlet weak var userImageView: CustomImageView!
    @IBOutlet weak var userPhoto: UIButton!
    @IBOutlet weak var userNameTextField: CustomTextField!
    @IBOutlet weak var emailTextField: CustomTextField!
    @IBOutlet weak var passwordTextField: CustomTextField!
    @IBOutlet weak var repeatPassTextField: CustomTextField!
    
    @IBAction func createUserBtn(_ sender: CustomButton) {
        guard let name = userNameTextField.text , userNameTextField.text != "" else { return }
        guard let email = emailTextField.text , emailTextField.text != "" else {return }
        guard let pass = passwordTextField.text, passwordTextField.text != "" else { return }
        
        AuthService.instance.registerUser(email: email, password: pass) { (success) in
            if success {
                AuthService.instance.loginUser(email: email, password: pass, completion: { (success) in
                    if success {
                        UserDataService.instance.setUserData(id: "0", email: email, name: name)
                        self.performSegue(withIdentifier: UNWIND, sender: nil)
                        NotificationCenter.default.post(name: NOTIF_USER_DID_CHANGED, object: nil)
                        
                    }
                })
            }
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
    }
    
    func setupView() {
        let tap = UITapGestureRecognizer(target: self, action: #selector(RegistrationViewController.handleTap))
        view.addGestureRecognizer(tap)
    }
    
    @objc func handleTap() {
        view.endEditing(true)
    }
    
}
