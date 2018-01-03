package ru.geekbrains.internship;

public interface ConnectionDBConst {
    final String DBSTRINGURLAPI = "http://searchstat.cf:8081/restapi/v1.0/servlet/?";
    final String DBSTRINGURL = "http://195.110.59.16:8081/request/?";
    final String GETTOTALSTATISTICS = "statistic=general";
    final String GETTOTALSTATISTICSPARAMS = "&site=%s";
    final String GETDAILYSTATISTICS = "statistic=daily";
    final String GETDAILYSTATISTICSPARAMS = "&person=%s&date1=%s&date2=%s&site=%s";
    final String GETSITES = "request=sites";
    final String GETPERSONS = "request=persons";
}
