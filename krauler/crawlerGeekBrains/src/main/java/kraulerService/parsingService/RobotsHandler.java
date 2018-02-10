package kraulerService.parsingService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RobotsHandler {

    private File robots;
    private String siteAddr;
    private List<String> siteMaps = new ArrayList<>();
    private int userAgentStr = -1;
    private List<String> allowDirective = new ArrayList<>();
    private List<String> disallowDirective = new ArrayList<>();
    private String userAgentSearchStat = "searchstat";

    private String verificationTemplateForLinks;

    /**
     * Конструкторы RobotsHandler
     * Передаваемый файл, после обработки, по умолчанию, удаляется
     * @param robots
     */
    public RobotsHandler(File robots, String siteAddr) {
        this.robots = robots;
        this.siteAddr = siteAddr;
        this.siteMaps = getSitemap();
        this.userAgentStr = searchUserAgent(this.userAgentSearchStat);
        this.searchDirective();
        this.verificationTemplateForLinks = buildPattern();

        // robots.txt после обработки удалить
        robots.delete();
    }

    public RobotsHandler(File robots, String siteAddr, boolean delFile) {
        this(robots,siteAddr);

        // robots.txt после обработки удалить
        if (delFile)
            robots.delete();
    }

    /**
     * Метод строит регулярное вырожение на основе деректив allow / disallow из секции с найденным User-Agent
     * для проверки ссылок (разрешил ли robots.txt обращаться к этим ссылкам)
     * @return
     */
    private String buildPattern() {

        StringBuilder sb = new StringBuilder();

        // Собираем начало регулярки
        sb.append("^(");
        sb.append(siteAddr.replace(".","\\."));
        sb.append(")");

        // Собираем запрещающую часть регулярки
        if(disallowDirective.size() > 0) {
            sb.append("(?! ");
            for (String s : disallowDirective) {
                if(s.equalsIgnoreCase("/")) continue;
                sb.append("|");
                sb.append(s.replace(".","\\."));
            }
            sb.append(")");
        }

        // Собираем разрешающую часть регулярки
        if(allowDirective.size() > 0) {
            sb.append("(/");
            for (String s : allowDirective) {
                if(s.equalsIgnoreCase("/")) continue;
                sb.append("|");
                sb.append(s.replace(".","\\."));
                sb.append(".*");
            }
            sb.append(")$");
        }

        return sb.toString();
    }

    /**
     * Проверка передоваемой ссылки на соответсвие дерективам allow / disallow из секции с найденным User-Agent
     * @param link
     * @return true / false
     */
    public boolean checkLink(String link) {
        String regExp = this.verificationTemplateForLinks;
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(link);
        return matcher.matches();
    }

    /**
     * Поиск деректив (allow / disallow) для User-Agent найденного в searchUserAgent
     * Метод заполняет внутренние списки объекта класса найденными дерективами
     */
    private void searchDirective() {
        String allow = "allow";
        String disallow = "disallow";

        // Читаем файл robots.txt построчно
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(robots))) {
            String tmpLine;
            for (int i = 1; (tmpLine = bufferedReader.readLine()) != null; i++) {
                // Ищем строку с нашим юзерагентом
                if(i > userAgentStr) {
                    if(tmpLine.toLowerCase().startsWith(allow)) {
                        allowDirective.add(tmpLine.split(" ")[1]);
                    } else if(tmpLine.toLowerCase().startsWith(disallow)) {
                        disallowDirective.add(tmpLine.split(" ")[1]);
                    } else {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            LogWork.myPrintStackTrace(e);
        }
    }

    /**
     * Поиск строки с юзер агентом
     * @return номер строки
     */
    private int searchUserAgent(String userAgentSearchStat) {
        String keyUserAgent = "user-agent:";
        String userAgentAny = "*";
        int userAgentStr = -1;

        // Читаем файл robots.txt построчно
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(robots))) {
            String tmpLine;
            for (int i = 1; (tmpLine = bufferedReader.readLine()) != null; i++) {
                tmpLine = tmpLine.toLowerCase().trim();
                String[] tmpArr = tmpLine.split("[ #]", 3);

                // Ищим UserAgent
                if(tmpLine.startsWith(keyUserAgent)){
                    for (String o: tmpArr) {

                        // Если нашли агента searchstat (что очень мало вероятно, но на всякий случай)
                        if(o.trim().equalsIgnoreCase(userAgentSearchStat)) {
                            userAgentStr = i;
                            break;
                        }

                        // Если агента searchstat не нашли, но нашли * (звёздочку)
                        if(userAgentStr == -1 && o.equalsIgnoreCase(userAgentAny)) {
                            userAgentStr = i;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            LogWork.myPrintStackTrace(e);
        } catch (IOException e) {
            LogWork.myPrintStackTrace(e);
        }
        return userAgentStr;
    }

    /**
     * Метод возвращает список сайтмапов из файла robots.txt
     * @return
     */
    private List<String> getSitemap () {
        String keySiteMap = "sitemap:";
        List<String> result = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(robots))) {
            String tmpLine;
            for (int i = 1; (tmpLine = bufferedReader.readLine()) != null; i++) {
                tmpLine = tmpLine.toLowerCase().trim();
                String[] tmpArr = tmpLine.split("[ #]", 3);

                // Ищем Sitemap
                if (tmpLine.startsWith(keySiteMap)) {
                    for (String o: tmpArr) {
                        if(o.startsWith("http")) result.add(o);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            LogWork.myPrintStackTrace(e);
        } catch (IOException e) {
            LogWork.myPrintStackTrace(e);
        }
        return result;
    }

    public List<String> getSiteMaps() {
        return siteMaps;
    }

    public int getUserAgentStr() {
        return userAgentStr;
    }

    public List<String> getAllowDirective() {
        return allowDirective;
    }

    public List<String> getDisallowDirective() {
        return disallowDirective;
    }

}
