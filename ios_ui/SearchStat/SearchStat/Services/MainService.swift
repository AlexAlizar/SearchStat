//
//  MainService.swift
//  SearchStat
//
//  Created by Roman Trekhlebov on 20.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import Foundation

protocol MainServiceDelegate {
    func initCompleated()
}

class MainService {
    
    static let instance = MainService()
    
    var delegate: MainServiceDelegate?
    
    public private(set) var siteArray: [Site]?
    
    
    
    private var personsArray: [Person]?
    
    var lasSiteIndex: Int = 0
    
    func beginInit() {
        self.getSites { (sitesSucces) in
            if sitesSucces {
                debugPrint("Sites Downloaded")
                self.getPersons(completionHandler: { (personsSucces) in
                    if personsSucces {
                        debugPrint("Persons Downloaded")
                        self.getGeneralStat(forSite: "lenta.ru", completionHandler: { (generalSucces) in
                            if generalSucces {
                                debugPrint("general success")
                            } else {
                                debugPrint("Gneral fail")
                            }
                        })
                    } else {
                        debugPrint("Persons Communication error")
                    }
                })
                
            }else {
                debugPrint("Sites Communication error")
            }
        }
    }
    func getGeneralStat(forSite site: String, completionHandler: @escaping CompletionHandler) {
        
        // Request
        
        let urlString = GENEARAL_STATS_URL.replacingOccurrences(of: "<TOKEN>", with: AuthService.instance.authToke).replacingOccurrences(of: "<SITE>", with: site)
        print(urlString)
        guard let url = URL(string: urlString) else {return}
        
        //GeneralPersonV2
        URLSession.shared.dataTask(with: url) { (data, response, error) in
            guard let data = data else {
                print("No Internet Connection")
                self.siteArray = self.generateFakeSites()
                completionHandler(false)
                return
            }
            guard error == nil else {return}
            
            do {
                let generalPersons = try JSONDecoder().decode([GeneralPersonV2].self, from: data)
                print(generalPersons)
                completionHandler(true)
                
            } catch let error {
                print(error)
                
                completionHandler(false)
            }
        } .resume()
    }
    private func addPersonsTo(siteName: String, personsJson: [GeneralPersonV2]) {
        for site in self.siteArray! {
            if site.name == siteName {
                for jsonPerson in personsJson {
                    let tempPerson = Person(id: 0, name: jsonPerson.name, total: Int(jsonPerson.rank)!, dayStatsArray: [DayStats]())
                    site.addPerson(person: tempPerson)
                }
            }
        }
    }
    
    func getSites(completionHandler: @escaping CompletionHandler) {
        
        // Request
        
        let urlString = SITE_LIST_URL + AuthService.instance.authToke
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
                let siteForSearch = try JSONDecoder().decode([SiteV2].self, from: data)
                self.siteArray = self.generateSiteArrayV2(siteForSearch)
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
    
    private func generateSiteArrayV2(_ json: [SiteV2] ) -> [Site] {
        var siteArray = [Site]()
        for site in json {
            let tempSite = Site(id: Int(site.id)!, name: site.name, personsArray: [Person]())
            siteArray.append(tempSite)
            
        }
        return siteArray
        
    }
    private func getPersons (completionHandler: @escaping CompletionHandler) {
//        PersonV2
        // Request
        
        let urlString = PERSONS_LIST_URL + AuthService.instance.authToke
        guard let url = URL(string: urlString) else {return}
        
        URLSession.shared.dataTask(with: url) { (data, response, error) in
            guard let data = data else {
                print("No Internet Connection")
                self.personsArray = self.generateFakePersonArray()
                completionHandler(false)
                return
            }
            guard error == nil else {return}
            
            do {
                let personsForSearch = try JSONDecoder().decode([PersonV2].self, from: data)
                self.personsArray = self.generatePersonsV2(personsForSearch)
                completionHandler(true)
                
            } catch let error {
                print(error)
                self.personsArray = self.generateFakePersonArray()
                completionHandler(false)
            }
            } .resume()
        
    }
    
    private func generatePersonsV2(_ json: [PersonV2]) -> [Person] {
        
        var personsArray = [Person]()
        for person in json {
            let tempPerson = Person(id: Int(person.id)!, name: person.name, total: 0, dayStatsArray: self.generateFakeDayStatsArray())
            personsArray.append(tempPerson)
            
            //fake data for person array!
        }
        return personsArray
        
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
        let day = daysBack - 1
        let hour = 0
        let minute = 0
        
        let today = Date(timeIntervalSince1970: 1514764800)
        let gregorian  = NSCalendar(calendarIdentifier: NSCalendar.Identifier.gregorian)
        var offsetComponents = DateComponents()
        offsetComponents.day = Int(day)
        offsetComponents.hour = Int(hour)
        offsetComponents.minute = Int(minute)
        
        let randomDate = gregorian?.date(byAdding: offsetComponents, to: today, options: .init(rawValue: 0) )
        return randomDate
    }
}







