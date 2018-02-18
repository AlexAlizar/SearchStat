import dbService.dataSets.Page;
import kraulerService.Krauler;
import kraulerService.parsingService.LogWork;

public class Main {
    public static void main(String[] args) {
        LogWork.logWrite("Start program\n");
        System.out.println("Start program\n");


        /*/////////   РАБОТА КЛАССА Krauler //////////////*/



        Krauler krauler = new Krauler();
        krauler.start();

        /*////////    КОНЕЦ РАБОТЫ Krauler //////////////*/


    }
}
