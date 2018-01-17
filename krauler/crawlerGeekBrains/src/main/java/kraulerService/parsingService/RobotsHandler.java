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

    /**
     * Конструктор RobotsHandler
     * @param robots
     */
    public RobotsHandler(File robots, String siteAddr) {
        this.robots = robots;
        this.siteAddr = siteAddr;
        this.siteMaps = getSitemap();
        this.userAgentStr = searchUserAgent();
        this.searchDirective();

        // robots.txt после обработки удалить
//        robots.delete();
    }

    public String getPattern() {

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

    public boolean checkLink(String link) {
        String regExp = getPattern();

/* debug * /
        System.out.println("   ---   link - - -    " + link);
        System.out.println("   ---   regExp ---    " + regExp);

/**/
//        Pattern pattern = Pattern.compile("^(https://tlgrm\\.ru)(?! |/about|/language|/manifest\\.json|/changelog)(/|/sources.*|/web.*|/apps.*|/faq.*|/press.*|/blog.*|/privacy.*|/techfaq.*|/stickers.*|/sitemap\\.xml.*)$");
//        Pattern pattern = Pattern.compile("^(https://tlgrm\\.ru)(?! |/about|/language|/manifest\\.json|/changelog)(/|/sources.*|/web.*|/apps.*|/faq.*|/press.*|/blog.*|/privacy.*|/techfaq.*|/stickers.*|/sitemap\\.xml.*)$");
//                                         ^(https://tlgrm\\.ru)(?! |/about|/language|/manifest\\.json|/changelog)(/|/sources.*|/web.*|/apps.*|/faq.*|/press.*|/blog.*|/privacy.*|/techfaq.*|/stickers.*|/sitemap\\.xml.*)$
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(link);
        return matcher.matches();
    }

    /**
     * Поиск деректив (allow / disallow) для User-Agent найденного в searchUserAgent
     */
    public void searchDirective() {
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
    private int searchUserAgent() {
        String keyUserAgent = "user-agent:";
        String userAgentSearchStat = "searchstat";
                                   // SearchStat
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
//                        System.out.println("str 45 --- " + o);
                        // Если нашли агента searchstat (что очень мало вероятно, но на всякий случай)
//                        if(o.startsWith("searchstat")) {
//                            userAgentStr = i;
//                        }

/* debug* /
                        System.out.println("o - " + o + "|");
                        System.out.println("userAgentSearchStat - " + userAgentSearchStat + "|");
                        System.out.println("userAgentAny - " + userAgentAny + "|\n");
/**/

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
