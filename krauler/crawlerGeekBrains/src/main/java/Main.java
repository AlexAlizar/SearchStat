import kraulerService.Krauler;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start program");
        System.out.println("Проверка связи =)");

        /**
         * чтобы всё работало, надо:
         * 1.1) создать connection в MySQL
         * 1.2) проврить чтобы пароль от созданного connection совпадал с тем под которым вы входите туда
         * 2) создать schema "test"
         * 3) если программа запускается в первый раз, то надо поменять параметр hbm2ddl.auto на create; в последующие запуски надо чтобы этот параметр был равен update
         * 4) ВУАЛЯ - можно использовать методы add/insert Person
         */

        Krauler krauler = new Krauler();
        krauler.Work();

    }
}
