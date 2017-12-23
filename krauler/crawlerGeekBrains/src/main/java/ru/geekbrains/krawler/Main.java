package ru.geekbrains.krawler;

import dbService.DBService;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start program");
        System.out.println("Проверка связи =)");
        //пример вызова метода download
        System.out.println(Downloader.download("lenta.ru"));

        System.out.println("Проверка от Алексей Грунтов =)");

        /**
         * чтобы всё работало, надо:
         * 1.1) создать connection в MySQL
         * 1.2) проврить чтобы пароль от созданного connection совпадал с тем под которым вы входите туда
         * 2) создать schema "test"
         * 3) если программа запускается в первый раз, то надо поменять параметр hbm2ddl.auto на create; в последующие запуски надо чтобы этот параметр был равен update
         * 4) ВУАЛЯ - можно использовать методы add/insert Person
         */

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        DBService dbService = new DBService(sessionFactory);
        dbService.addPerson("Peeeetya");
        dbService.getPersonByName("Petya");
        dbService.getPersonByName("Petttttya");

//        Date dt = new Date();
//
//        SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
//
//        try {
//            //System.out.println(dbService.getPersonById(2).toString());
//            System.out.println(dbService.getPersonByName("Petya").toString());
//            System.out.println(dbService.getPersonByName("Petya").toString());
//            System.out.println(dbService.getPersonByName("Vasya").toString());
//            System.out.println(dbService.getPersonByName("Vas").toString());
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }

    }
}
