//
//  TotalStatisticViewController.swift
//  SearchStat
//
//  Created by Евгений Скилиоти on 20.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import UIKit

class TotalStatisticViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {

    var personArray = [Person]()
    
    @IBOutlet weak var nameDateLabel: UILabel!
    @IBOutlet weak var nameSourceLabel: UILabel!
    @IBOutlet weak var totalStatTableView: UITableView!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.initVC()
        
    }
    
    private func initVC() {
        //MARK: Чтение выбранного сайта
        let siteIndex = UserDefaults.standard.integer(forKey: SITE_INDEX)
        //MARK: Чтение даты обновления данных
        let dateString = UserDefaults.standard.string(forKey: DATA_UPDATE_STRING)
        
        let site = MainService.instance.siteArray![siteIndex]
        personArray = site.personsArray
        
        
        nameSourceLabel.text = site.name
        nameDateLabel.text = dateString
    }
        
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return personArray.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "totalcell", for: indexPath) as! TotalStatisticCell
        
        cell.setupTotalCell(person: personArray[indexPath.row])
       
        return cell
    }
    
//    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
//        if segue.identifier == TO_DETAIL_STAT {
//            let destVC: DetailStatisticViewController = segue.destination as! DetailStatisticViewController
//
////            destVC.nameSourceLabelStr = nameSourceDetailLabel
//        }
//    }
}
