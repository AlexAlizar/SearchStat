//
//  AuthService.swift
//  SearchStat
//
//  Created by Roman Trekhlebov on 10.01.2018.
//  Copyright © 2018 Евгений Скилиоти. All rights reserved.
//

import Foundation
//commit

class AuthService {
    
    static let instance = AuthService()
    
    var usersArray: [String : String] = ["admin":"123"]
    
    let defaults = UserDefaults.standard
    
    var isLoggedin: Bool {
        get {
            return defaults.bool(forKey: LOGGED_IN_KEY)
        }
        set {
            defaults.set(newValue, forKey: LOGGED_IN_KEY)
        }
    }
    
    var userEmail: String {
        get {
            return defaults.value(forKey: USER_EMAIL) as! String
        }
        set {
            defaults.set(newValue, forKey: USER_EMAIL)
        }
    }
    
    var userName: String {
        get {
            return defaults.value(forKey: USER_NAME) as! String
        }
        set {
            defaults.set(newValue, forKey: USER_NAME)
        }
    }
    
    var authToke: String {
        get {
            return defaults.value(forKey: TOKEN_KEY) as! String
        }
        set {
            defaults.set(newValue, forKey: TOKEN_KEY)
        }
    }
    
    func registerUser(email: String, password: String, completion: @escaping CompletionHandler) {
        
        let lowerCaseEmail = email.lowercased()

        if usersArray[lowerCaseEmail] == nil {
            usersArray[lowerCaseEmail] = password
            
            completion(true)
        } else {
            completion(false)
        }
    }
    
    func loginUser(email: String, password: String, completion: @escaping CompletionHandler) {
        let lowerCaseEmail = email.lowercased()
        
        if usersArray[lowerCaseEmail] == password {
            self.isLoggedin = true
            completion(true)
        } else {
            completion(false)
        }
    }
}
