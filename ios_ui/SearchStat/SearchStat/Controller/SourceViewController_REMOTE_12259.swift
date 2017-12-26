//
//  SourceViewController.swift
//  SearchStat
//
//  Created by Евгений Скилиоти on 20.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import UIKit

class SourceViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    let arr = ["rbc","rt","lenta"]
    var sitesArray = [Site]()
    
    @IBOutlet weak var sourceTableView: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        MainService.instance.getSites { (result) in
            if result {
                sitesArray = MainService.instance.siteArray!
            }
        }
        
        view.setGradientBackground(colorOne: Colors.gradientColorOne, colorTwo: Colors.gradientColorTwo)
    }
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return sitesArray.count
    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell  {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! SourceCell
    
        cell.setupCell(site: sitesArray[indexPath.row])
        return cell
    }
}




