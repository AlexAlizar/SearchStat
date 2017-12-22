//
//  SourceViewController.swift
//  SearchStat
//
//  Created by Евгений Скилиоти on 20.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import UIKit

class SourceViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
<<<<<<< HEAD
    
    
    
    var sitesArray: [Site] = []
=======
    let arr = ["rbc","rt","lenta"]
    var sitesArray = [Site]()
>>>>>>> e3dead4d86117e2a81060704a1daecd7ce5a1291
    
    @IBOutlet weak var sourceTableView: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
<<<<<<< HEAD
        
=======
>>>>>>> e3dead4d86117e2a81060704a1daecd7ce5a1291
        MainService.instance.getSites { (result) in
            if result {
                sitesArray = MainService.instance.siteArray!
                
            }
        }
        
<<<<<<< HEAD
        
        
=======
        view.setGradientBackground(colorOne: Colors.gradientColorOne, colorTwo: Colors.gradientColorTwo)
>>>>>>> e3dead4d86117e2a81060704a1daecd7ce5a1291
    }
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return sitesArray.count
    }
<<<<<<< HEAD

=======
    
    
>>>>>>> e3dead4d86117e2a81060704a1daecd7ce5a1291
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
                destVC.sourceName = sitesArray[indexPath.row].name + "    " + DateFormatter.localizedString(from: Date(), dateStyle: .short, timeStyle: .short)
            }
        }
    }
    
}




