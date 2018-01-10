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
    
    public private(set) var id = ""
    public private(set) var email = ""
    public private(set) var name = ""
    
    func setUserData(id: String, email: String, name: String) {
        self.id = id
        self.email = email
        self.name = name
        
        //temp del after
        AuthService.instance.userName = name
        
    }
    
    func logoutUser() {
        id = ""
        name = ""
        email = ""
        
        AuthService.instance.isLoggedin = false
        AuthService.instance.userEmail = ""
        AuthService.instance.userName = ""
        //AuthService.instance.authToke = ""
    }
    
    
}
