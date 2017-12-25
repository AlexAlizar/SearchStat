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
    
    @IBOutlet weak var nameSourceLabel: UILabel!
    @IBOutlet weak var totalStatTableView: UITableView!
    
    var nameSourceLableString = " "
    
    override func viewDidLoad() {
        super.viewDidLoad()
        nameSourceLabel.text = nameSourceLableString
       
    }
    
    func initVC(_ site: Site) {
        personArray = site.personsArray
        nameSourceLableString = site.name + "    " + DateFormatter.localizedString(from: Date(), dateStyle: .short, timeStyle: .short)
    }
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return personArray.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "totalcell", for: indexPath) as! TotalStatisticCell
        
        cell.setupTotalCell(person: personArray[indexPath.row])
       
        return cell
    }


}
