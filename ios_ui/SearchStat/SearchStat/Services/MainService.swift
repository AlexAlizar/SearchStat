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
    
    public private(set) var lastUpdateDate: String? 
    

    
    
    func getSites(completionHandler: @escaping CompletionHandler) {
        
        // Request
        
        let urlString = BASE_URL
        guard let url = URL(string: urlString) else {return}
        
        URLSession.shared.dataTask(with: url) { (data, response, error) in
            guard let data = data else {
                print("No Internet Connection")
                self.siteArray = self.generateFakeSites()
                completionHandler(false)
                return
            }
            guard error == nil else {return}
            
            do {
                let siteForSearch = try JSONDecoder().decode(SiteForSearch.self, from: data)
                print(siteForSearch)
                self.siteArray = self.generateSiteArrayFromJson(siteForSearch)
                completionHandler(true)
               
            } catch let error {
                print(error)
                self.siteArray = self.generateFakeSites()
                completionHandler(false)
                }
            } .resume()
        
        lastUpdateDate = DateFormatter.localizedString(from: Date(), dateStyle: .short, timeStyle: .none)
        
    }
    

    
    
    
    
    private func generateSiteArrayFromJson (_ json: SiteForSearch ) -> [Site] {
        var siteArray = [Site]()
        for site in json.sites {
            
            let personsArray = generatePersonsArrayFromJson(site.persons)
            
            let tempSite = Site(id: Int(site.SiteID)!, name: site.SiteName, personsArray: personsArray)
            siteArray.append(tempSite)
        }
        return siteArray
    }
    
    private func generatePersonsArrayFromJson(_ json: [PersonsDescription]) -> [Person] {
        var personsArray = [Person]()
        for item in json {
            personsArray.append(generatePersonFromJson(item))
        }
        return personsArray
        
    }
    
    private func generatePersonFromJson (_ json: PersonsDescription) -> Person {
        let person = Person(id: Int(json.PersonID)!, name: json.PersonName, total: Int(json.PersonRank)!, dayStatsArray: generateFakeDayStatsArray())
        //FIX!! FAKE DATA!!
        return person
    }
//___________________________________________________________________________
    public private(set) var personArray: [Person]?
    
    func getPerson(completionHandler: CompletionHandler) {
        
        self.personArray = generateFakePersonArray()
        completionHandler(true)
    }
//___________________________________________________________________________

    public private(set) var dayStatArray: [DayStats]?
    
    func getDayStat(completionHandler: CompletionHandler) {
        
        self.dayStatArray = generateFakeDayStatsArray()
        completionHandler(true)
    }
//___________________________________________________________________________
    //tempFakeFunc
    
    private func generateFakeSites() -> [Site] {
        let tempSite = Site(id: 0, name: "fakeSiteOne", personsArray: generateFakePersonArray())
        let tempSite1 = Site(id: 1, name: "fakeSiteTwo", personsArray: generateFakePersonArray())
        let tempSite2 = Site(id: 2, name: "fakeSiteTree", personsArray: generateFakePersonArray())
        var tempArr = [Site]()
        tempArr.append(tempSite)
        tempArr.append(tempSite1)
        tempArr.append(tempSite2)

        return tempArr
    }
    
    private func generateFakePersonArray() -> [Person] {
        var personsArray = [Person]()
            personsArray.append(Person(id: 0, name: "FakePersonOne", total: 222, dayStatsArray: generateFakeDayStatsArray()))
            personsArray.append(Person(id: 1, name: "FakePersonTwo", total: 3333, dayStatsArray: generateFakeDayStatsArray()))
            personsArray.append(Person(id: 2, name: "FakePersonTree", total: 44444, dayStatsArray: generateFakeDayStatsArray()))
        return personsArray
    }
    
    private func generateFakeDayStatsArray() -> [DayStats] {
        var dayStatsArray = [DayStats]()
        for i in 1...30 {
            let tempDate = Date(timeIntervalSince1970: TimeInterval((20 + i)*365*24*60*60))
            
            dayStatsArray.append(DayStats(day: tempDate, total: 1 + i * 100))
            print(dayStatsArray)
        }
        return dayStatsArray
    }
}
