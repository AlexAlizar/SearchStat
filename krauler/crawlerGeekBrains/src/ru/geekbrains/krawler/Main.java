package ru.geekbrains.krawler;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start program");
        System.out.println("Проверка связи =)");
        //пример вызова метода download
        System.out.println(Downloader.download("lenta.ru"));

        System.out.println("Проверка от Алексей Грунтов =)");

        //тест метода parsePage - не совсем верный результат
        int counter = 0;
        counter += PageParser.parsePage(Downloader.download("http://putin.kremlin.ru/bio"), "Путин");
        System.out.println("Путин -> " + PageParser.parsePage(Downloader.download("http://putin.kremlin.ru/bio"), "Путин"));

        System.out.println();
        System.out.println();

        counter += PageParser.parsePage(Downloader.download("http://putin.kremlin.ru/bio"), "Путина");
        System.out.println("Путина -> " + PageParser.parsePage(Downloader.download("http://putin.kremlin.ru/bio"), "Путина"));

        System.out.println();
        System.out.println();

        System.out.println("Общее количество -> " + counter);
    }
}
