//
//  MainModel.swift
//  SearchStat
//
//  Created by Roman Trekhlebov on 18.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import Foundation

struct Site {
    var id: Int
    var name: String
    var personsArray: [Person]
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

struct PeriodStats {
    var startDate: Date
    var endDate: Date
    var datesArray : [Date]
    var total: Int
}


