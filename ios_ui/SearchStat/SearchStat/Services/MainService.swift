//
//  MainService.swift
//  SearchStat
//
//  Created by Roman Trekhlebov on 20.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import Foundation

class MainService {
    
    static let instance = MainService()
    
    public private(set) var siteArray: [Site]?
    
    func getSites(completionHandler: CompletionHandler) {
        
        // Request
        
        let urlString = BASE_URL
        guard let url = URL(string: urlString) else {return}
        
        URLSession.shared.dataTask(with: url) { (data, response, error) in
            guard let data = data else {return}
            guard error == nil else {return}
            
            do {
                let siteForSearch = try JSONDecoder().decode(SiteForSearch.self, from: data)
                print(siteForSearch)
               
            } catch let error {
                print(error)
                }
            } .resume()
        
        self.siteArray = generateFakeSites()
        completionHandler(true)
    }
    
//___________________________________________________________________________
    public private(set) var personArray: [Person]?
    
    func getPerson(completionHandler: CompletionHandler) {
        
        self.personArray = generateFakePersonArray()
        completionHandler(true)
    }
//___________________________________________________________________________
    
    //tempFakeFunc
    
    private func generateFakeSites() -> [Site] {
        let tempSite = Site(id: 0, name: "rbc", personsArray: generateFakePersonArray())
        let tempSite1 = Site(id: 1, name: "rt", personsArray: generateFakePersonArray())
        let tempSite2 = Site(id: 2, name: "lenta", personsArray: generateFakePersonArray())
        var tempArr = [Site]()
        tempArr.append(tempSite)
        tempArr.append(tempSite1)
        tempArr.append(tempSite2)
        
        return tempArr
    }
    
    private func generateFakePersonArray() -> [Person] {
        var personsArray = [Person]()
            personsArray.append(Person(id: 0, name: "Person1", total: 229, dayStatsArray: generateFakeDayStatsArray()))
            personsArray.append(Person(id: 0, name: "Person2", total: 399, dayStatsArray: generateFakeDayStatsArray()))
            personsArray.append(Person(id: 0, name: "Person3", total: 11000, dayStatsArray: generateFakeDayStatsArray()))
        return personsArray
    }
    
    private func generateFakeDayStatsArray() -> [DayStats] {
        var dayStatsArray = [DayStats]()
        for i in 1...30 {
            let tempDate = Date(timeIntervalSince1970: TimeInterval((20 + i)*365*24*60*60))
            
            dayStatsArray.append(DayStats(day: tempDate, total: 1 + i * 100))

        }
        return dayStatsArray
    }
}
