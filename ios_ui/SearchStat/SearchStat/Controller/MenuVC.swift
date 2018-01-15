//
//  MenuVC.swift
//  SearchStat
//
//  Created by Roman Trekhlebov on 10.01.2018.
//  Copyright © 2018 Евгений Скилиоти. All rights reserved.
//

import UIKit

class MenuVC: UIViewController {
    //Outlets
    @IBOutlet weak var menuBtn: UIButton!
    @IBOutlet weak var loginBtn: UIButton!
    @IBOutlet weak var settingBtn: CustomButton!
    
    //Actions
    @IBAction func prepareForUnwind(segue: UIStoryboardSegue) {}
    @IBAction func loginBtnPressed(_ sender: Any) {
        if AuthService.instance.isLoggedin {
            UserDataService.instance.logoutUser()
            loginBtn.setTitle("Login", for: .normal)
            menuBtn.isHidden = true
            settingBtn.isHidden = true
        } else {
            performSegue(withIdentifier: TO_LOGIN, sender: nil)
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        //MARK: Ширина Бокового меню
        self.revealViewController().rearViewRevealWidth = self.view.frame.size.width - 60
        menuBtn.isHidden = true
        settingBtn.isHidden = true
        
        
        NotificationCenter.default.addObserver(self, selector: #selector(MenuVC.userDataDidChanged(_:)), name: NOTIF_USER_DID_CHANGED, object: nil)
        
        //temp
        if AuthService.instance.isLoggedin {
            UserDataService.instance.setUserData(name: AuthService.instance.userName)
            NotificationCenter.default.post(name: NOTIF_USER_DID_CHANGED, object: nil)
        }
    }

    @objc func userDataDidChanged(_ notif: Notification) {
        if AuthService.instance.isLoggedin {
            DispatchQueue.main.async {
                self.loginBtn.setTitle(UserDataService.instance.name, for: .normal)
                self.menuBtn.isHidden = false
                self.settingBtn.isHidden = false
            }

        } else {
            DispatchQueue.main.async {
                self.loginBtn.setTitle("Login", for: .normal)
                self.menuBtn.isHidden = true
            }
        }
    }

}
