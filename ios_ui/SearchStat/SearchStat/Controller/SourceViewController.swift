//
//  SourceViewController.swift
//  SearchStat
//
//  Created by Евгений Скилиоти on 20.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import UIKit

class SourceViewController: UIViewController, UITableViewDelegate, UITableViewDataSource, MainServiceDelegate {
    
    var sitesArray: [SiteModel] = []

    @IBOutlet weak var menuBtn: UIButton!
    @IBOutlet weak var sourceTableView: UITableView!
    
    @IBOutlet weak var commInd: UIActivityIndicatorView!
    override func viewDidLoad() {
        super.viewDidLoad()
        //MARK: показать меню
        menuBtn.addTarget(self.revealViewController(), action: #selector(SWRevealViewController.revealToggle(_:)), for: .touchUpInside)
        //MARK: Обработчики нашатий для меню
        self.view.addGestureRecognizer(self.revealViewController().panGestureRecognizer())
        self.view.addGestureRecognizer(self.revealViewController().tapGestureRecognizer())
        
        
        self.commInd.isHidden = false
        self.commInd.startAnimating()
        
        MainService.instance.beginInit()
        MainService.instance.delegate = self
        
//        self.initVC()
        NotificationCenter.default.addObserver(self, selector: #selector(SourceViewController.userDataDidChanged(_:)), name: NOTIF_USER_DID_CHANGED, object: nil)
        
    }
    @objc func userDataDidChanged(_ notif: Notification) {
        
        self.performSegue(withIdentifier: TO_FIRST_VC, sender: nil)
    }
    
    //MARK: Delegate method from MainService
    internal func initCompleated() {
        
        self.sitesArray = MainService.instance.getSitesArray()
        
        
        DispatchQueue.main.async {
            self.commInd.isHidden = true
            self.commInd.stopAnimating()
            self.sourceTableView.reloadData()
        }
        
    }
    
    func initFail(error: String) {
        //error message
        let alert = UIAlertController(title: "Error", message: error, preferredStyle: UIAlertControllerStyle.alert)
        
        let action = UIAlertAction(title: "Try again", style: .default) { (action) in
            debugPrint("restarting...")
            MainService.instance.beginInit()
        }
        alert.addAction(action)
        DispatchQueue.main.async {
            self.present(alert, animated: true, completion: nil)
        }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return sitesArray.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell  {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! SourceCell
            cell.setupCell(site: sitesArray[indexPath.row])
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        performSegue(withIdentifier: TO_TOTAL_STAT, sender: self)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == TO_TOTAL_STAT {
            
            if let indexPath = sourceTableView.indexPathForSelectedRow {
                
                //MARK: Запоминаем выбранный сайт
                UserDefaults.standard.set(indexPath.row, forKey: SITE_INDEX)        
            }
        }
    }
    
}




