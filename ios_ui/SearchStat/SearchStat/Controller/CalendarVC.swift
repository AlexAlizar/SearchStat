//
//  CalendarVC.swift
//  SearchStat
//
//  Created by Roman Trekhlebov on 26.12.2017.
//  Copyright © 2017 Евгений Скилиоти. All rights reserved.
//

import UIKit

class CalendarVC: UIViewController {
    @IBOutlet weak var bgView: UIView!

    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
        
    }
    
    func setupView() {
        
        
        
        let closeTouch = UITapGestureRecognizer(target: self, action: #selector(CalendarVC.closeTap(_:)))
        bgView.addGestureRecognizer(closeTouch)
    }
    
    @objc func closeTap(_ recognizer: UITapGestureRecognizer) {
        dismiss(animated: true, completion: nil)
        NotificationCenter.default.post(name: NOTIF_CALL_DONE, object: nil)
    }
    
}
