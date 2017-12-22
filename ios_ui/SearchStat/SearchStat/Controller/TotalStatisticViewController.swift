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
    
    var sourceName: String?
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        if let nameTolabel = sourceName {
            nameSourceLabel.text = nameTolabel
        }
     
        
        
        MainService.instance.getPerson { (result) in
            if result {
                personArray = MainService.instance.personArray!
            }
        }
        
        
       
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
