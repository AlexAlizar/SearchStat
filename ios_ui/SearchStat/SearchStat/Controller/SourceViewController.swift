//
//  SourceViewController.swift
//  SearchStat
//
//  Created by Евгений Скилиоти on 20.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import UIKit

class SourceViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    var sitesArray: [Site] = []

    @IBOutlet weak var sourceTableView: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        if MainService.instance.siteArray == nil {
            MainService.instance.getSites { (result) in
                if result {
                    self.sitesArray = MainService.instance.siteArray!
                    DispatchQueue.main.async {
                        self.sourceTableView.reloadData()
                    }
                } else {
                    //ошибка в сервисе используем фейковые данные
                    //выводим предупреждение
                    self.sitesArray = MainService.instance.siteArray!
                    
                    DispatchQueue.main.async {
                        self.sourceTableView.reloadData()
                    }
                }
            }
        } else {
            self.sitesArray = MainService.instance.siteArray!
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
        performSegue(withIdentifier: "toTotalStatistic", sender: self)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "toTotalStatistic" {
            if let indexPath = sourceTableView.indexPathForSelectedRow {
                let destVC: TotalStatisticViewController = segue.destination as! TotalStatisticViewController
                destVC.initVC(sitesArray[indexPath.row])
            }
        }
    }
    
}




