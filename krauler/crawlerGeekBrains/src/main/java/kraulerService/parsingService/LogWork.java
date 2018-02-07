package kraulerService.parsingService;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogWork {

    private static SimpleDateFormat formatForCurDate = new SimpleDateFormat("yyyy.MM.dd kk:mm:ss\"SSS");
    private static SimpleDateFormat formatForLogName = new SimpleDateFormat("yyyy.MM.dd");
    private static String pathLog = "logs";
    private static File folderPathLog = new File(pathLog);
    private static String nameLogFile = "SearchStat";
    private static File logFile = new File(pathLog + "/" +nameLogFile + "_" + formatForLogName.format(new Date()) + ".log");

    /**
     * It checks if the file exists and if it does not exist, it creates.
     * @param logFile
     * @return false if the file can not be created
     */
    private static boolean checkLogExist(File logFile) {

        if (!folderPathLog.exists()) folderPathLog.mkdir();

        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                System.out.println("vvaMessage - Can't create log file.");
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * Write message in log file
     * @param logMsg
     */
    public static void logWrite (String logMsg) {

        // Получаем массив stak trace элементов
        StackTraceElement[] ste = Thread.currentThread().getStackTrace();

        // Получаем номер строки в которой вызван метод logWrite
        int callString = ste[ste.length - 2].getLineNumber();

        // Получаем имя класса в котором вызван метод logWrite (берём только имя класса, без полного пути до него)
        String className = ste[ste.length - 2].getClassName().split("[.]")[ste[ste.length - 2].getClassName().split("[.]").length - 1];

        // Получаем имя метода в отором вызван метод logWrite
        String methodName = ste[ste.length - 2].getMethodName();

        // Собираем сообщение в одну строку
        if (checkLogExist(logFile)) {
            try (FileWriter fileWriter = new FileWriter(logFile,true)) {
                fileWriter.write(formatForCurDate.format(new Date()) + "    ");
                fileWriter.write("(" + className + " " + methodName + " str: " + callString + ")" + "    ");
                fileWriter.write(logMsg + "\n");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(logMsg);
        } else {
            System.out.println("Что то пошло не так с лог файлом kraulerService.parsingService.LogWork -> logWrite");
        }
    }

    public static void myPrintStackTrace (Exception e) {
        LogWork.logWrite("Atention  --  " + e.toString());
        for (StackTraceElement s: e.getStackTrace()) {
            LogWork.logWrite("      " + s);
        }
        e.printStackTrace();
    }
}
