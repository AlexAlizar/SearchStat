package ru.geekbrains.krawler;

import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start program");
        System.out.println("Проверка связи =)");
        //пример вызова метода download
        System.out.println(Downloader.download("lenta.ru"));

        System.out.println("Проверка от Алексей Грунтов =)");
        
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    }
}
