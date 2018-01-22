REST API v1.0
URL:
  http://searchstat.cf:8081/restapi/v1.0/servlet/?
  или по IP адресу
  http://195.110.59.16:8081/restapi/v1.0/servlet/?
Метод: GET
Ответ: "application/json; charset=utf-8"
  
----------
Параметры для "Общей статистики":
  Название: statistic
  Значение: statistic_type
  Обязательный: ДА
  Описание: Для отображения "общей статистики" необходимо, чтобы значение равнялось "general"
  
  Название: site
  Значение: site_name
  Обязательный: ДА
  Описание: Параметр должен соответствовать наименованию сайта из таблицы БД "sites"
  
  Пример:
  http://searchstat.cf:8081/restapi/v1.0/servlet/?statistic=general&site=lenta.ru
 
Ответ:
  Пример ответа:
  {"general_statistic": [{ 
                     "name" : "Медведев",
                     "rank" : "18"},
                     "name" : "Навальный",
                     "rank" : "12"},
                     "name" : "Путин",
                     "rank" : "33"}]

----------
Параметры для "Ежедневной статистики":
  Название: statistic
  Значение: statistic_type
  Обязательный: ДА
  Описание: Для отображения "Ежедневной статистики" необходимо, чтобы значение равнялось "daily"
  
  Название: person
  Значение: person_name
  Обязательный: ДА
  Описание: Параметр должен соответствовать имени персоны из таблицы БД "persons"
  
  Название: date1
  Значение: start_date
  Формат: "YYYY-MM-DD" или "YYYY-MM-DD HH:MM:SS"
  Обязательный: ДА
  Описание: Начальная дата выборки
  
  
  Название: date2
  Значение: start_date
  Формат: "YYYY-MM-DD" или "YYYY-MM-DD HH:MM:SS"
  Обязательный: ДА
  Описание: Конечная дата выборки
  
  Название: site
  Значение: site_name
  Обязательный: ДА
  Описание: Параметр должен соответствовать наименованию сайта из таблицы БД "sites"

  Пример:
  http://searchstat.cf:8081/restapi/v1.0/servlet/?statistic=daily&person=Путин&date1=2017-12-29&date2=2017-12-30&site=meduza.io

Ответ:  
  Пример ответа:
  {"daily_statistic": [{ 
                     "date" : "2017-12-30 00:00:00.0",
                     "count" : "2"},
                     "date" : "2017-12-30 00:00:00.0",
                     "count" : "2"}]

----------
Обработка ошибок:
Ответ: 
  { "error" : "Empty request"}
  или
  { "error" : "Unknown type of statistic"}
  или
  { "error" : "Parameter "date1" does not exist."}
