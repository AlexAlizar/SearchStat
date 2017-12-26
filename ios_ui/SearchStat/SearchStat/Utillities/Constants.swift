//
//  Constants.swift
//  SearchStat
//
//  Created by Roman Trekhlebov on 19.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import Foundation
import UIKit

typealias CompletionHandler = (_ Success: Bool) -> ()

//URL Constants
let BASE_URL = "http://195.110.59.16:8081/rest-api-servlet/"
let URL_REGISTER = "\(BASE_URL)account/register"
let URL_LOGIN = "\(BASE_URL)account/login"
let URL_USER_ADD = "\(BASE_URL)user/add"

//Colors
//let redColor = #colorLiteral(red: 0.7450980544, green: 0.1568627506, blue: 0.07450980693, alpha: 1)
struct Colors {
    
    static let gradientColorOne = UIColor(red: 100.0/255.0, green: 143.0/255.0, blue: 203.0/255.0, alpha: 1.0)
    static let gradientColorTwo = UIColor(red: 29.0/255.0, green: 64.0/255.0, blue: 100.0/255.0, alpha: 1.0)
    
    
}

//Notification Constants
let NOTIF_CALL_DONE = Notification.Name("notifCallDone")

//Segues
let TO_LOGIN = "toLogin"
let TO_CREATE_ACCOUNT = "toCreateAccount"
let UNWIND = "unwindToChannel"
let TO_AVATAR_PICKER = "toAvatarPicker"


//User Defaults
let TOKEN_KEY = "token"
let LOGGED_IN_KEY = "loggedin"
let USER_EMAIL = "userEmail"

//Headers
let HEADER = [
    "Content-Type" : "application/json; charset=utf-8"
]
