//
//  DetailStatService.swift
//  SearchStat
//
//  Created by Roman Trekhlebov on 18.01.2018.
//  Copyright © 2018 Евгений Скилиоти. All rights reserved.
//

import Foundation

protocol DetailStatServiceDelegate {
    
    func dataArrayCompleated(array: [DayStatsV2], requestID: String)
    func dataArrayReturnZero(array: [DayStatsV2], requestID: String)
    
    func dataLoadSingleCompleated(day: DayStatsV2)
    func dataReturnNil(day: DayStatsV2)
}

struct detailRequest {
    var ident = ""
    var person = ""
    var site = ""
    var isStarting = false
    var isDone = false
    var inputArray = [Date]()
    var outputArray = [DayStatsV2]()
    
    init(site: String, person: String, dateArray: [Date]) {
        self.ident = "\(site)_\(person)_\(dateArray.first!)_\(dateArray.last!)"
        self.person = person
        self.site = site
        self.inputArray = dateArray
    }
    
    func startRequest() {
        DetailStatService.instance.requestPeriodDetail(forPerson: person, forSite: site, dateArray: inputArray)
    }
}

class DetailStatService {

    static let instance = DetailStatService()
    
    var dayStatsArray: [DayStatsV2]?
    var delegate: DetailStatServiceDelegate?
    
    var requestedDates = 0
    var respondedDates = 0
    
    fileprivate let formatter: DateFormatter = {
        let formatter = DateFormatter()
        //MARK: ADDed for fix time zone
        formatter.timeZone = .current
        formatter.calendar = Calendar.current
        formatter.dateFormat = "yyyy.MM.dd"
        return formatter
    }()
    
    //for proxy
    func requestPeriodDetail(forPerson person: String, forSite site: String, dateArray: [Date], requestID: String) {
        let dateStart = dateArray.first!
        let dateEnd = dateArray.last!
        self.requestDetailFromServiceV2(forPerson: person, forSite: site, startDate: dateStart, endDate: dateEnd) { (succes, error, data) in
            if succes {
                self.delegate?.dataArrayCompleated(array: data!, requestID: requestID)
                
            } else {
                print(error ?? "error nil")
            }
        }
    }
    
    func requestPeriodDetail(forPerson person: String, forSite site: String, dateArray: [Date]) {
        var shrinkedArray = [Date]()
        for item in dateArray {
            shrinkedArray.append(self.shrinkDate(item))
        }
        let shrinkedDateStart = self.shrinkDate(dateArray.first!)
        let shrinkedDateEnd = self.shrinkDate(dateArray.last!)
        
        if let array = self.findDetailPeriod(forDates: shrinkedArray, ofPerson: person, atSite: site){
            //отправить делегат с данными с периодом
            print("Period Will delegate")
            
            
            
        } else {
            requestedDates = shrinkedArray.count
            self.requestDetailFromService(forPerson: person, forSite: site, startDate: shrinkedDateStart, endDate: shrinkedDateEnd) { (success) in
                if success {
                    print("period detail success")
                    
                    //данные пришли, отправить делегат с данными
                    //рекурсивный вызов, тк данные уже в массиве
                    self.requestPeriodDetail(forPerson: person, forSite: site, dateArray: dateArray)
                    
                } else {
                    print("period have problems")
                    //данных нет, отправить делегат о тм что данных на сервере нет
                    //просто заглушка с датой
//                    var nilDataDay = DayStatsV2(site: site, person: person, dateString: "2017-12-29 00:00:00.0" , countOfPages: "0")
                    
                    
                    
                }
            }
        }
        
        
        
        
        
    }
    private func shrinkDate(_ date: Date) -> Date {
        let myOffset = TimeInterval(TimeZone.current.secondsFromGMT())
        let tempDate = date + myOffset
        
        let shrinkDateUnix = Int(tempDate.timeIntervalSince1970 / 86400) * 86400
        let shrinkedDate = Date(timeIntervalSince1970: TimeInterval(shrinkDateUnix))
        return shrinkedDate
    }
    
    func requestSingleDetail(forPerson person: String, forSite site: String, date: Date) {
        // Just for test TIMEZONES
        let shrinkedDate = self.shrinkDate(date)
        
        self.requestDetailFromService(forPerson: person, forSite: site, startDate: shrinkedDate, endDate: shrinkedDate, completionHandler: { (success) in
                if success {
//                    self.delegate?.dataLoadSingleCompleated(day: <#T##DayStatsV2#>)
                } else {
                    //просто заглушка с датой
                    var nilDataDay = DayStatsV2(site: site, person: person, dateString: "2017-12-29 00:00:00.0" , countOfPages: "0")
                    nilDataDay.day = date
                    
                    self.delegate?.dataReturnNil(day: nilDataDay)
                }
            })
        
    }
    private func findDetailPeriod(forDates datesInputArray: [Date], ofPerson person: String, atSite site: String) -> [DayStatsV2]? {
        var returnArray = [DayStatsV2]()
        guard self.dayStatsArray != nil else {
            return nil
        }
        
        let inputArrayCount = datesInputArray.count
        var i = 0
        
        for date in datesInputArray {
            
            if let item = findDetailInArray(forDate: date, ofPerson: person, atSite: site) {
                returnArray.append(item)
                i += 1
            }
            
        }
        let delta = requestedDates - respondedDates
        if inputArrayCount == i + delta {
            //zna4it vse dannie est v massive
            return returnArray
        } else {
            //nushno zaprashivat
            return nil
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
                        self.dayStatsArrayTryToAppend(convertedArray)
//                        self.dayStatsArray?.append(contentsOf: convertedArray)
                    }
                    self.respondedDates = convertedArray.count
                    completionHandler(true)
                    
                }
                
            } catch let error {
                print(error)
                
                completionHandler(false)
            }
            }.resume()
    }
//for proxy
    func requestDetailFromServiceV2(forPerson person: String, forSite site: String, startDate: Date, endDate: Date, compleation: @escaping DetailStatCompletion ) {
        
        // Request
        
        let dateOne = self.shrinkDate(startDate)
        let dateTwo = self.shrinkDate(endDate)
        
        let formatedDateOneString = self.formatter.string(from: dateOne)
        let formatedDateTwoString = self.formatter.string(from: dateTwo)
        
        guard let url = encodeUrl(person: person, dateOne: formatedDateOneString, dateTwo: formatedDateTwoString, site: site) else {return}
        print(url)
        
        URLSession.shared.dataTask(with: url) { (data, response, error) in
            guard let data = data else {
                print("No Internet Connection")
                let response = DetailStatResponse(false, "No Internet Connection", nil)
                compleation(response)
                return
            }
            guard error == nil else {return}
            
            do {
                let detailData = try JSONDecoder().decode([DaylyStatsV2].self, from: data)
                print(detailData)
                if detailData.count == 0 {
                    //zero for this date
                    print("no data for selected dates")
                    let response = DetailStatResponse(true, nil, [DayStatsV2]())
                    compleation(response)
                } else {
                    let convertedArray = self.convertJsonToDayStats(siteName: site, personName: person, array: detailData)
                    
                    self.respondedDates = convertedArray.count
                    let response = DetailStatResponse(true, nil, convertedArray)
                    compleation(response)
                    
                }
                
            } catch let error {
                print(error)
                
                let response = DetailStatResponse(false, error.localizedDescription, nil)
                compleation(response)
            }
        }.resume()
    }
    
    private func isDataExistInArray(day: DayStatsV2) -> Bool {
        var isExist = false
        for item in self.dayStatsArray! {
            if item.siteName == day.siteName && item.personName == day.personName &&
                item.day == day.day {
                //все совпадает, значит такое значение уже есть
                isExist = true
                break
            } else {
                isExist = false
                continue
            }
        }
        return isExist
    }
    
    private func dayStatsArrayTryToAppend(_ inputArray: [DayStatsV2]) {
        for inputItem in inputArray {
            
            if isDataExistInArray(day: inputItem) {
                continue
            } else {
                self.dayStatsArray?.append(inputItem)
            }
        }
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
   
    private static func shrinkDate(_ date: Date) -> Date {
        let myOffset = TimeInterval(TimeZone.current.secondsFromGMT())
        let tempDate = date + myOffset
        
        let shrinkDateUnix = Int(tempDate.timeIntervalSince1970 / 86400) * 86400
        let shrinkedDate = Date(timeIntervalSince1970: TimeInterval(shrinkDateUnix))
        return shrinkedDate
    }
}
