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
        
        let calendar = NSCalendar.current
        let components = calendar.dateComponents([.year, .month, .day], from: filteredDate)
        
        let filteredDay = components.day
        let filteredMonth = components.month
        let filteredYear = components.year
        
        for item in self.dayStatsArray {
            let tempCalendar = NSCalendar.current
            let itemComponent = tempCalendar.dateComponents([.year, .month, .day], from: item.day)
            let itemYear = itemComponent.year
            let itemMonth = itemComponent.month
            let itemDay = itemComponent.day
            if itemYear! == filteredYear! && itemMonth! == filteredMonth! && itemDay! == filteredDay! {
                //sovpodaet
                filteredStats = item
            }
            
        }
        return filteredStats
    }
}

struct DayStats {
    var day: Date
    var total: Int
}


