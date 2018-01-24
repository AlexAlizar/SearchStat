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
    
    override func viewDidLoad() {
        super.viewDidLoad()
        //MARK: показать меню
        menuBtn.addTarget(self.revealViewController(), action: #selector(SWRevealViewController.revealToggle(_:)), for: .touchUpInside)
        //MARK: Обработчики нашатий для меню
        self.view.addGestureRecognizer(self.revealViewController().panGestureRecognizer())
        self.view.addGestureRecognizer(self.revealViewController().tapGestureRecognizer())
        
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
            self.sourceTableView.reloadData()
        }
        
    }
    
//    private func initVC() {
//        if MainService.instance.siteArray == nil {
//            MainService.instance.getSites { (result) in
//                if result {
//                    self.sitesArray = MainService.instance.siteArray!
//                    DispatchQueue.main.async {
//                        self.sourceTableView.reloadData()
//                    }
//                } else {
//                    //ошибка в сервисе используем фейковые данные
//                    //выводим предупреждение
//                    self.sitesArray = MainService.instance.siteArray!
//
//                    DispatchQueue.main.async {
//                        self.sourceTableView.reloadData()
//                    }
//                }
//            }
//        } else {
//            self.sitesArray = MainService.instance.siteArray!
//        }
//    }
    
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
                
//                let destVC: TotalStatisticViewController = segue.destination as! TotalStatisticViewController
//                destVC.initVC()
                
                
            }
        }
    }
    
}




