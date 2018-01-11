package ru.geekbrains.internship;

interface ConnectionDBConst {
    String DBSTRINGURL = "http://searchstat.cf:8081";
    String DBSTRINGURLAPI = "/restapi/v1.0/servlet/?";
    String DBSTRINGURLREQUEST = "/request/?";
    String GETTOTALSTATISTICS = "statistic=general";
    String GETTOTALSTATISTICSPARAMS = "&site=%s";
    String GETDAILYSTATISTICS = "statistic=daily";
    String GETDAILYSTATISTICSPARAMS = "&person=%s&date1=%s&date2=%s&site=%s";
    String GETSITES = "request=sites";
    String GETPERSONS = "request=persons";
    String FAKEDB = "fake";
}
