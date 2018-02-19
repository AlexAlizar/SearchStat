//
//  AuthService.swift
//  SearchStat
//
//  Created by Roman Trekhlebov on 10.01.2018.
//  Copyright © 2018 Евгений Скилиоти. All rights reserved.
//

import Foundation
//commit
//{
//    "error": "Authentication failed"
//}
struct AuthError: Codable {
    var error: String
}

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
    
    func registerUser(user: String, email: String, password: String, completion: @escaping CompletionHandler) {
        
        let lowerCaseEmail = email.lowercased()
        let lowerCaseUser = user.lowercased()

        // Request
        
        let urlString = "\(REG_URL)login=\(lowerCaseUser)&password=\(password)&email=\(lowerCaseEmail)"
        
        guard let url = URL(string: urlString) else {return}
        
        URLSession.shared.dataTask(with: url) { (data, response, error) in
            guard let data = data else {
                print("No Internet Connection")
                
                completion(false)
                return
            }
            guard error == nil else {
                print("Error in response")
                
                completion(false)
                return
            }
            //"Updated"
            var resultString = String(data: data, encoding: String.Encoding.utf8)
            if resultString!.count > 0 {
                if (resultString?.contains("Updated"))! {
                    debugPrint("User created")
                    completion(true)
                } else {
                    debugPrint("userCreate Error")
                    completion(false)
                }
                
            } else {
                debugPrint("Authorisation Fail")
                completion(false)
            }
            
        }.resume()
    }
    
    func loginUser(user: String, password: String, completion: @escaping CompletionHandler) {
        let lowerCaseUser = user.lowercased()
        
        // Request
        
        let urlString = "\(AUTH_URL)login=\(lowerCaseUser)&password=\(password)"
        guard let url = URL(string: urlString) else {return}
        
        URLSession.shared.dataTask(with: url) { (data, response, error) in
            guard let data = data else {
                print("No Internet Connection")
                
                completion(false)
                return
            }
            guard error == nil else {
                print("Error in response")
                
                completion(false)
                return
            }
            
            
            var tokenString = String(data: data, encoding: String.Encoding.utf8)
            if tokenString!.count > 0 {
                tokenString?.removeLast(2)
                tokenString?.removeFirst()
                if tokenString != nil && !(tokenString?.contains("error"))! {
                    debugPrint("Authorised token: \(tokenString!)")
                    self.authToke = tokenString!
                    self.userName = lowerCaseUser
                    self.isLoggedin = true
                    completion(true)
                } else {
                    debugPrint("Authorisation Fail")
                    completion(false)
                }
                
            } else {
                debugPrint("Authorisation Fail")
                completion(false)
            }

        } .resume()
    }
}
