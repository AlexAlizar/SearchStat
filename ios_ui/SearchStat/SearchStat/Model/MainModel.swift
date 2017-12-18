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
}

struct DayStats {
    var day: Date
    var total: Int
}


