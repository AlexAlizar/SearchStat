//
//  DetailStatService.swift
//  SearchStat
//
//  Created by Roman Trekhlebov on 18.01.2018.
//  Copyright © 2018 Евгений Скилиоти. All rights reserved.
//

import Foundation

class DetailStatService {
    

//http://195.110.59.16:8081/restapi-v2/?token=0123456789&action=daily-statistic&person=Путин&date1=2017-12-29&date2=2017-12-30&site=meduza.io

    static let instance = DetailStatService()
    
    fileprivate let formatter: DateFormatter = {
        let formatter = DateFormatter()
        //MARK: ADDed for fix time zone
        formatter.timeZone = .current
        formatter.calendar = Calendar.current
        formatter.dateFormat = "yyyy.MM.dd"
        return formatter
    }()
    
    func requestDetail(forPerson person: String, forSite site: String, startDate: Date, endDate: Date, completionHandler: @escaping CompletionHandler) {
        
        // Request
        
        let formatedDateOneString = self.formatter.string(from: startDate)
        let formatedDateTwoString = self.formatter.string(from: endDate)
        
        guard let url = encodeUrl(person: person, dateOne: formatedDateOneString, dateTwo: formatedDateTwoString, site: site) else {return}
        print(url)
        
        URLSession.shared.dataTask(with: url) { (data, response, error) in
            guard let data = data else {
                print("No Internet Connection")
                
                completionHandler(false)
                return
            }
            guard error == nil else {return}
            
            do {
                let detailData = try JSONDecoder().decode([DaylyStatsV2].self, from: data)
                print(detailData)
                completionHandler(true)
                
            } catch let error {
                print(error)
                
                completionHandler(false)
            }
            }.resume()
    }

    func encodeUrl(person: String, dateOne: String, dateTwo: String, site: String) -> URL? {
        
        var queryItems = [URLQueryItem]()
        queryItems.append(URLQueryItem(name: "action", value: "daily-statistic"))
        queryItems.append(URLQueryItem(name: "person", value: person))
        queryItems.append(URLQueryItem(name: "date1", value: dateOne))
        queryItems.append(URLQueryItem(name: "date2", value: dateTwo))
        queryItems.append(URLQueryItem(name: "token", value: AuthService.instance.authToke))
        queryItems.append(URLQueryItem(name: "site", value: site))
        
        var urlComponents = URLComponents()
        urlComponents.scheme = URL_SCHEME
        urlComponents.host = URL_HOST
        urlComponents.port = URL_PORT
        urlComponents.path = URL_PATH
        urlComponents.queryItems = queryItems
        
        if let url = urlComponents.url {
            return url
        }
        return nil
        
    }
    
}
