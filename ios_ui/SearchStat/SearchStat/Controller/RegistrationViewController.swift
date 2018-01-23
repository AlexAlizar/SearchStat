//
//  RegistrationViewController.swift
//  SearchStat
//
//  Created by Евгений Скилиоти on 20.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import UIKit

class RegistrationViewController: UIViewController, UITextFieldDelegate {

    
    @IBAction func closeBtnPressed(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }
    
    @IBAction func userPhotoBtn(_ sender: UIButton) {
        
    }
    
    @IBOutlet weak var registrationScrollViewConst: NSLayoutConstraint!
    @IBOutlet weak var registrationScrollView: UIScrollView!
    @IBOutlet weak var userImageView: CustomImageView!
    @IBOutlet weak var userPhoto: UIButton!
    @IBOutlet weak var userNameTextField: CustomTextField!
    @IBOutlet weak var emailTextField: CustomTextField!
    @IBOutlet weak var passwordTextField: CustomTextField!
    @IBOutlet weak var repeatPassTextField: CustomTextField!
    
    @IBAction func createUserBtn(_ sender: CustomButton) {
        createUser()
    }
    
    func createUser() {
        guard let name = userNameTextField.text , userNameTextField.text != "" else { return }
        guard let email = emailTextField.text , emailTextField.text != "" else {return }
        guard let pass = passwordTextField.text, passwordTextField.text != "" else { return }
        
        AuthService.instance.registerUser(email: email, password: pass) { (success) in
            if success {
                AuthService.instance.loginUser(user: email, password: pass, completion: { (success) in
                    if success {
                        UserDataService.instance.setUserData(name: name)
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
        userNameTextField.delegate = self
        emailTextField.delegate = self
        passwordTextField.delegate = self
        repeatPassTextField.delegate = self
        
    }
    
    func setupView() {
        
        let tap = UITapGestureRecognizer(target: self, action: #selector(RegistrationViewController.handleTap))
        view.addGestureRecognizer(tap)
//        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(didTapView(gesture:)))
//        view.addGestureRecognizer(tapGesture)
    }
    
    @objc func handleTap() {
        view.endEditing(true)
    }
    
    //MARK: Text field delegate for Done button on keyboard
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        
        if textField == userNameTextField {
            textField.resignFirstResponder()
            emailTextField.becomeFirstResponder()
        } else if textField == emailTextField {
            textField.resignFirstResponder()
            passwordTextField.becomeFirstResponder()
        } else if textField == passwordTextField {
            textField.resignFirstResponder()
            repeatPassTextField.becomeFirstResponder()
        } else if textField == repeatPassTextField {
            textField.resignFirstResponder()
            createUser()
        }
        return true
    }
}

//MARK: For scrolling when typing begin
extension RegistrationViewController {
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        addObservers()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        removeObservers()
    }
    
//    @objc func didTapView(gesture: UITapGestureRecognizer) {
//        view.endEditing(true)
//    }

    func addObservers() {
        NotificationCenter.default.addObserver(forName: .UIKeyboardWillShow, object: nil, queue: nil) { notification in
            self.keyboardWillShow(notification: notification)
        }
        NotificationCenter.default.addObserver(forName: .UIKeyboardWillHide, object: nil, queue: nil) { notification in
            self.keyboardWillHide(notification: notification)
        }
    }
    
    func removeObservers() {
        NotificationCenter.default.removeObserver(self)
    }
    
    func keyboardWillShow(notification: Notification) {
        guard let userInfo = notification.userInfo,
            let frame = (userInfo[UIKeyboardFrameEndUserInfoKey] as? NSValue)?.cgRectValue else { return }
            let contentInset = UIEdgeInsets(top: 0, left: 0, bottom: frame.height  , right: 0)
            registrationScrollView.contentInset = contentInset
            registrationScrollViewConst.constant = -100
            UIScrollView.animate(withDuration: 0.3) {
                self.view.layoutIfNeeded()
        }
    }
    
    func keyboardWillHide(notification: Notification) {
        registrationScrollView.contentInset = UIEdgeInsets.zero
        registrationScrollViewConst.constant = 0
        UIScrollView.animate(withDuration: 0.3) {
            self.view.layoutIfNeeded()
        }
    }
}
