import dbService.dataSets.Page;
import kraulerService.Krauler;
import kraulerService.parsingService.LogWork;

public class Main {
    public static void main(String[] args) {

        //устанавливаем чувствительность логгера
        //0 - только ошибки
        //1 - только важные
        //2 - средние по важности
        //3 - все
        LogWork.setLogLevel(2);
        LogWork.logWrite("Start program\n",1);

        /*/////////   РАБОТА КЛАССА Krauler //////////////*/

        Krauler krauler = new Krauler();
        krauler.start();

        /*////////    КОНЕЦ РАБОТЫ Krauler //////////////*/


    }
}
