//
//  DetailStatService.swift
//  SearchStat
//
//  Created by Roman Trekhlebov on 18.01.2018.
//  Copyright © 2018 Евгений Скилиоти. All rights reserved.
//

import Foundation

protocol DetailStatServiceDelegate {
    
    func dataLoadSingleCompleated(day: DayStatsV2)
    func dataReturnNil(day: DayStatsV2)
}

class DetailStatService {

    static let instance = DetailStatService()
    
    var dayStatsArray: [DayStatsV2]?
    var delegate: DetailStatServiceDelegate?
    
    fileprivate let formatter: DateFormatter = {
        let formatter = DateFormatter()
        //MARK: ADDed for fix time zone
        formatter.timeZone = .current
        formatter.calendar = Calendar.current
        formatter.dateFormat = "yyyy.MM.dd"
        return formatter
    }()
    
    func requestSingleDetail(forPerson person: String, forSite site: String, date: Date) {
        // Just for test
        let myOffset = TimeInterval(TimeZone.current.secondsFromGMT())
        let dateOne = date + myOffset
        
        let shrinkDateUnix = Int(dateOne.timeIntervalSince1970 / 86400) * 86400
        let shrinkedDate = Date(timeIntervalSince1970: TimeInterval(shrinkDateUnix))

        
        if let dayData = findDetailInArray(forDate: shrinkedDate, ofPerson: person, atSite: site) {
            //отправить делегат с данными
            self.delegate?.dataLoadSingleCompleated(day: dayData)
        } else {
            self.requestDetailFromService(forPerson: person, forSite: site, startDate: shrinkedDate, endDate: shrinkedDate, completionHandler: { (success) in
                if success {
                    //данные пришли, отправить делегат с данными
                    //рекурсивный вызов, тк данные уже в массиве
                    self.requestSingleDetail(forPerson: person, forSite: site, date: shrinkedDate)
                    
                } else {
                    //данных нет, отправить делегат о тм что данных на сервере нет
                    //просто заглушка с датой
                    var nilDataDay = DayStatsV2(site: site, person: person, dateString: "2017-12-29 00:00:00.0" , countOfPages: "0")
                    nilDataDay.day = date
                    
                    self.delegate?.dataReturnNil(day: nilDataDay)
                }
            })
        }
        
    }
    
    private func findDetailInArray(forDate date: Date, ofPerson person: String, atSite site: String) -> DayStatsV2? {
        guard let array = self.dayStatsArray else {
            return nil
        }
        var returnItem: DayStatsV2?
        for item in array {
            
            if item.personName == person && item.siteName == site && item.day == date {
                returnItem = item
                break
            }
        }
        return returnItem
    }
    
    private func requestDetailFromService(forPerson person: String, forSite site: String, startDate: Date, endDate: Date, completionHandler: @escaping CompletionHandler) {
        
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
                if detailData.count == 0 {
                    //zero for this date
                    print("no data for selected dates")
                    completionHandler(false)
                } else {
                    let convertedArray = self.convertJsonToDayStats(siteName: site, personName: person, array: detailData)
                    if self.dayStatsArray == nil {
                        self.dayStatsArray = convertedArray
                    } else {
                        self.dayStatsArray?.append(contentsOf: convertedArray)
                    }
                    
                    completionHandler(true)
                    
                }
                
            } catch let error {
                print(error)
                
                completionHandler(false)
            }
            }.resume()
    }

    private func convertJsonToDayStats(siteName: String, personName: String, array: [DaylyStatsV2]) -> [DayStatsV2] {
        var outputArray = [DayStatsV2]()
        for item in array {
            let tempItem = DayStatsV2(site: siteName, person: personName, dateString: item.date, countOfPages: item.countOfPages)
            outputArray.append(tempItem)
        }
        return outputArray
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
