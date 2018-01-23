//
//  Proxy.swift
//  SearchStat
//
//  Created by Roman Trekhlebov on 22.01.2018.
//  Copyright © 2018 Евгений Скилиоти. All rights reserved.
//

import Foundation

class DetailRequest {
    var ident = ""
    var person = ""
    var site = ""
    var isStarting = false
    var isDone = false
    var isError: String?
    var inputDateArray = [Date]() {
        didSet {
            self.missedDateArray = Proxy.instance.findMissedItemFor(site: site, person: person, datesArray: inputDateArray)
        }
    }
    var missedDateArray: [Date]!
    var outputArray = [DayStatsV2]()
    
    init(site: String, person: String, dateArray: [Date]) {
        self.ident = "\(site)_\(person)_\(dateArray.first!)_\(dateArray.last!)"
        self.person = person
        self.site = site
        self.inputDateArray = dateArray
    }
    
    func startRequest() {
        self.isStarting = true
        DetailStatService.instance.requestPeriodDetail(forPerson: person, forSite: site, dateArray: missedDateArray)
    }
}

class Proxy: DetailStatServiceDelegate {
    func dataArrayReturnZero(array: [DayStatsV2], requestID: String) {
        print("PROXY:DetailStatServiceDelegate")
    }
    
    func dataArrayCompleated(array: [DayStatsV2], requestID: String) {
        print("PROXY:DetailStatServiceDelegate")
        var request = findRequest(id: requestID)
        request?.outputArray = array
        request?.isDone = true
    }
    
    func dataLoadSingleCompleated(day: DayStatsV2) {
        print("PROXY:dataLoadSingleCompleated")
    }
    
    func dataReturnNil(day: DayStatsV2) {
        print("PROXY:dataReturnNil")
    }
    
    
    
    
    static var instance = Proxy()
    
    var requests = [DetailRequest]()
    var storedData = [DayStatsV2]()
    
    init() {
        DetailStatService.instance.delegate = self
    }
    
    func reqestDataFor(site: String, person: String, datesArray: [Date]) {
        let formatedArray = Proxy.shrinkDateArray(datesArray)
        let request = DetailRequest(site: site, person: person, dateArray: formatedArray)
        request.startRequest()
        self.requests.append(request)
        
    }
    
    
    
    
    
    //MARK: Helper Functions:
    
    private func mergeRequests() {
        for item in requests {
            if item.isStarting && item.isDone {
                let resultArray = item.outputArray
                let missedDates = item.missedDateArray
                var arrayForMissed = [Date]()
                for dateItem in missedDates! {
                    
                }
            }
        }
    
    }
    
    private func generateNILdata(person: String, site: String, date: Date) -> DayStatsV2 {
        var returnItem = DayStatsV2(site: site, person: person, dateString: "2017-12-29 00:00:00.0", countOfPages: "NIB")
        returnItem.day = date
        return returnItem
    }
    private func findRequest(id: String) -> DetailRequest? {
        for item in self.requests {
            if item.ident == id {
                return item
            }
        }
        return nil
    }
    
    fileprivate func findMissedItemFor(site:String, person: String, datesArray array: [Date]) -> [Date] {
        var resultArray = [Date]()
        for item in array {
            if !isInBase(site: site, person: person, date: item){
                resultArray.append(item)
            }
        }
        return resultArray
    }
    
    private func isInBase(site: String, person: String, date: Date) -> Bool {
        var isExist = false
        for item in self.storedData {
            if item.siteName == site && item.personName == person &&
                item.day == date {
                //все совпадает, значит такое значение уже есть
                isExist = true
                break
            } else {
                isExist = false
                continue
            }
        }
        return isExist
    }
    
    //static section
    private static func shrinkDateArray(_ array: [Date]) -> [Date] {
        var formatedArray = [Date]()
        for item in array {
            formatedArray.append(Proxy.shrinkDate(item))
        }
        return formatedArray
    }
    
    private static func shrinkDate(_ date: Date) -> Date {
        let myOffset = TimeInterval(TimeZone.current.secondsFromGMT())
        let tempDate = date + myOffset
        
        let shrinkDateUnix = Int(tempDate.timeIntervalSince1970 / 86400) * 86400
        let shrinkedDate = Date(timeIntervalSince1970: TimeInterval(shrinkDateUnix))
        return shrinkedDate
    }
}
