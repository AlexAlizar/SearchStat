//
//  Struct.swift
//  SearchStat
//
//  Created by Timofey on 21.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import Foundation

struct SiteForSearch: Codable {
    var sites: [SitesDescription]
}

struct SitesDescription: Codable {
    var SiteID: String
    var SiteName: String
    var persons: [PersonsDescription]
}

struct PersonsDescription: Codable {
    var PersonID: String
    var PersonName: String
    var PersonRank: String
}
