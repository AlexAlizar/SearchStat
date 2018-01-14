//
//  UserDataService.swift
//  SearchStat
//
//  Created by Roman Trekhlebov on 10.01.2018.
//  Copyright © 2018 Евгений Скилиоти. All rights reserved.
//

import Foundation

class UserDataService {
    static let instance = UserDataService()
    
    public private(set) var name = ""
    
    func setUserData(name: String) {
        self.name = name
        
    }
    
    func logoutUser() {
        name = ""
        
        AuthService.instance.isLoggedin = false
        AuthService.instance.userName = ""
        AuthService.instance.authToke = ""
    }
    
    
}
