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
    
    var lasSiteIndex: Int = 0

    //userdDefExample
//    UserDefaults.standard.set(true, forKey: "Key") //Bool
//    UserDefaults.standard.set(1, forKey: "Key")  //Integer
//    UserDefaults.standard.set("TEST", forKey: "Key") //setObject
//    Retrieve
//
//    UserDefaults.standard.bool(forKey: "Key")
//    UserDefaults.standard.integer(forKey: "Key")
//    UserDefaults.standard.string(forKey: "Key")
//    Remove
//
//    UserDefaults.standard.removeObject(forKey: "Key")
    //

    func getSites(completionHandler: @escaping CompletionHandler) {
        
        // Request
        
        let urlString = SITE_LIST_URL
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
        
        
        
        let lastUpdateDateString = DateFormatter.localizedString(from: Date(), dateStyle: .long, timeStyle: .short)
        
        UserDefaults.standard.set(lastUpdateDateString, forKey: DATA_UPDATE_STRING)
        
        
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
//            let tempDate = Date(timeIntervalSince1970: TimeInterval((20 + i)*365*24*60*60))
            
            let tempDate = generateDate(daysBack: i)
            
            dayStatsArray.append(DayStats(day: tempDate!, total: 1 + i * 100))

//            print(dayStatsArray)
        }
        return dayStatsArray
    }
    
    
    func generateDate(daysBack: Int) -> Date?{
        let day = daysBack
        let hour = 0
        let minute = 0
        
        let today = Date(timeIntervalSince1970: 1514709054)
        let gregorian  = NSCalendar(calendarIdentifier: NSCalendar.Identifier.gregorian)
        var offsetComponents = DateComponents()
        offsetComponents.day = Int(day - 1)
        offsetComponents.hour = Int(hour)
        offsetComponents.minute = Int(minute)
        
        let randomDate = gregorian?.date(byAdding: offsetComponents, to: today, options: .init(rawValue: 0) )
        return randomDate
    }
}







