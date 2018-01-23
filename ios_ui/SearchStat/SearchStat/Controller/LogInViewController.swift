//
//  LogInViewController.swift
//  SearchStat
//
//  Created by Евгений Скилиоти on 19.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import UIKit

class LogInViewController: UIViewController, UITextFieldDelegate{
    
    
    @IBOutlet weak var loginScrollView: UIScrollView!
    @IBOutlet weak var scrollViewConst: NSLayoutConstraint!
    @IBOutlet weak var emailTextField: CustomTextField!
    @IBOutlet weak var passwordTextField: CustomTextField!
    
    @IBAction func restorePasswordBtn(_ sender: UIButton) {
    }
    
    @IBAction func logInBtn(_ sender: CustomButton) {
        
        authorization()
    }
        
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setupView()
        emailTextField.delegate = self
        passwordTextField.delegate = self
    }
    
    func authorization() {
        //authorization...
        guard let user = emailTextField.text , emailTextField.text != "" else { return }
        guard let pass = passwordTextField.text, passwordTextField.text != "" else { return }
        
        AuthService.instance.loginUser(user: user, password: pass) { (success) in
            if success {
                UserDataService.instance.setUserData(name: AuthService.instance.userName)
                self.performSegue(withIdentifier: UNWIND, sender: nil)
                NotificationCenter.default.post(name: NOTIF_USER_DID_CHANGED, object: nil)
            } else {
                let alert = UIAlertController(title: "Error", message: "Communication/Login \n Error", preferredStyle: UIAlertControllerStyle.alert)
                alert.addAction(UIAlertAction(title: "Ok", style: UIAlertActionStyle.default, handler: nil))
                self.present(alert, animated: true, completion: nil)
            }
        }
    }
    
    func setupView() {
        
        let tap = UITapGestureRecognizer(target: self, action: #selector(RegistrationViewController.handleTap))
            view.addGestureRecognizer(tap)

    }
    
    @objc func handleTap() {
        
        view.endEditing(true)
    }
    
    //MARK: Text field delegate for Done button on keyboard
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        
        if textField == emailTextField {
            textField.resignFirstResponder()
            passwordTextField.becomeFirstResponder()
        } else if textField == passwordTextField {
            textField.resignFirstResponder()
            authorization()
        }
        return true
    }
}

//MARK: For scrolling when typing begin
extension LogInViewController {
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        addObservers()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        removeObservers()
    }

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
            loginScrollView.contentInset = contentInset
            scrollViewConst.constant = -100
            UIScrollView.animate(withDuration: 0.3) {
                self.view.layoutIfNeeded()
        }
    }
    
    func keyboardWillHide(notification: Notification) {
        loginScrollView.contentInset = UIEdgeInsets.zero
        scrollViewConst.constant = 0
        UIScrollView.animate(withDuration: 0.3) {
            self.view.layoutIfNeeded()
        }
    }
}
