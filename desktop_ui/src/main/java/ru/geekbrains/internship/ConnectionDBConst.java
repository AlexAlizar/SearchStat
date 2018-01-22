package ru.geekbrains.internship;

interface ConnectionDBConst {
    String DBSTRINGURL = "http://195.110.59.16:8081";
    String DBSTRINGURLAPI = "/restapi-v2/?";
    String ACTION_GET_AUTH = "action=auth";
    String ACTION_GET_AUTH_PARAMS = "&login=%s&password=%s";
    String ACTION_GET_TOTALSTATISTICS = "&action=general-statistic";
    String ACTION_GET_TOTALSTATISTICS_PARAMS = "&site=%s";
    String ACTION_GET_DAILYSTATISTICS = "&action=daily-statistic";
    String ACTION_GET_GETDAILYSTATISTICS_PARAMS = "&person=%s&date1=%s&date2=%s&site=%s";
    String ACTION_GET_SITES = "&action=get-sites";
    String ACTION_GET_PERSONS = "&action=get-persons";
    String ACTION_TOKEN = "token=%s";
    String FAKEDB = "demo";

}
