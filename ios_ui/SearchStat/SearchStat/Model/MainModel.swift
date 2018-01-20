//
//  MainModel.swift
//  SearchStat
//
//  Created by Roman Trekhlebov on 18.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import Foundation
//Model v2
struct SiteModel {
    var name: String
    var personsArray: [GeneralPersonV2]
    var isVissible = true
    
    init(name: String, perArray: [GeneralPersonV2]) {
        self.name = name
        self.personsArray = perArray
    }
}

//model v1

struct Site {
    var id: Int
    var name: String
    var personsArray: [Person]
    
    mutating func addPerson(person: Person) {
        self.personsArray.append(person)
    }
}

struct Person {
    var id: Int
    var name: String
    var total: Int
    var dayStatsArray: [DayStats]
    
    func filteredStats(filteredDate: Date) -> DayStats? {
        var filteredStats: DayStats?
        
        for item in self.dayStatsArray {
            if isDatesEqual(dateOne: filteredDate, dateTwo: item.day) {
                //sovpodaet
                filteredStats = item
            }
            
        }
        return filteredStats
    }
    
    func filteredPeriodStats(startDate: Date, endDate: Date) -> PeriodStats? {
        var endPeriodObject: PeriodStats?
        var isStartFound = false
        var isEndFound = false
        
        var lastItem: DayStats?
        
        for item in self.dayStatsArray {
            if !isStartFound { //Если начало еще не найдено
                
                if isDatesEqual(dateOne: startDate, dateTwo: item.day) { //Нашли начало
                    isStartFound = true
                    var tempDateArray = [Date]()
                    tempDateArray.append(item.day)
                    endPeriodObject = PeriodStats(startDate: item.day, endDate: item.day, datesArray: tempDateArray, total: item.total)
                    lastItem = item
                }
            } else { //Начало уже найдено
                if isDatesEqual(dateOne: endDate, dateTwo: item.day) { //Если конец найден
                    isEndFound = true
                    endPeriodObject!.datesArray.append(item.day)
                    endPeriodObject!.total += item.total
                    break
                } else {
                    endPeriodObject!.total += item.total
                    endPeriodObject!.datesArray.append(item.day)
                    lastItem = item
                }
            }
        }
        if isStartFound && !isEndFound {
            endPeriodObject!.endDate = lastItem!.day
        }
        return endPeriodObject
    }
    
    
    func isDatesEqual(dateOne: Date, dateTwo: Date) -> Bool {
    
        let unixDateOne = Int(dateOne.timeIntervalSince1970 / 86400)
        let unixDateTwo = Int(dateTwo.timeIntervalSince1970 / 86400)
       
        return unixDateOne == unixDateTwo ? true : false
    }
}

struct DayStats {
    var day: Date
    var total: Int
    
    
}

struct DayStatsV2 {
    var siteName: String
    var personName: String
    var day: Date
    var total: Int
    
    init(site: String, person: String ,dateString: String, countOfPages: String) {
        //(date: "2017-12-29 00:00:00.0", countOfPages: "2")
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss.s"
        dateFormatter.timeZone = TimeZone(abbreviation: "UTC") //Current time zone of server
//        dateFormatter.timeZone = TimeZone(abbreviation: "GMT-5:00") //Current time zone of server
        let date = dateFormatter.date(from: dateString)
        let shrinkDateUnix = Int(date!.timeIntervalSince1970 / 86400) * 86400
        self.siteName = site
        self.personName = person
        self.day = Date(timeIntervalSince1970: TimeInterval(shrinkDateUnix))
        self.total = Int(countOfPages)!
        
    }
}


struct PeriodStats {
    var startDate: Date
    var endDate: Date
    var datesArray : [Date]
    var total: Int
}


