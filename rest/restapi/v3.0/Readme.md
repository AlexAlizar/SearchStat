REST API v3.0
URL:
  http://searchstat.cf:8081/restapi-v3/?
  или по IP адресу
  http://195.110.59.16:8081/restapi-v3/?
Метод: GET
Ответ: "application/json; charset=utf-8"

1. Любая работа с сервисом происходит методом GET с обязательным параметром action (все варианты описаны ниже)!
2. Работа с сервисом происходит через метод авторизации:
любой запрос исполняется с указание обязательного параметра token(кроме метода авторизации по логин/пароль),
он должен соответствовать полю token из таблицы users основной БД.
Для получения токена нужно пройти авторизацию по логину с паролем , в ответ получите токен.

Работа с сервисом:
action == auth
- Запрос:
http://195.110.59.16:8081/restapi-v3/?action=auth&login=admin&password=admin
- Ответ:
"9876543210"
- Дополнительно:
варианты логинов и паролей можно посмотреть в БД


action == general-statistic
- Запрос:
http://195.110.59.16:8081/restapi-v3/?token=9876543210&action=general-statistic&site=lenta.ru
- Параметры:
--site - Обязательный. Поле "name" из таблицы БД "sites"
- Пример ответа:
[
  {
    "name": "Медведев",
    "rank": "18"
  },
  {
    "name": "Навальный",
    "rank": "12"
  },
  {
    "name": "Путин",
    "rank": "33"
  }
]

action == daily-statistic
- Запрос:
http://195.110.59.16:8081/restapi-v3/?token=9876543210&action=daily-statistic&person=Путин&date1=2017-12-29&date2=2017-12-30&site=meduza.io
- Параметры:
-- person - Обязательный. Поле "name" из таблицы БД "persons"
-- date1 - Обязательный. Начальная дата периода выборки
-- date2 - Обязательный. Конечная дата периода выборки
-- site - Обязательный. Поле "name" из таблицы БД "sites"
- Ответ:
[
  {
    "date": "2017-12-29 00:00:00.0",
    "countOfPages": "2"
  },
  {
    "date": "2017-12-30 00:00:00.0",
    "countOfPages": "2"
  }
]

action == get-persons
- Запрос:
http://195.110.59.16:8081/restapi-v3/?token=9876543210&action=get-persons
- Ответ:
[
  {
    "id": "1",
    "name": "Путин"
  },
  {
    "id": "2",
    "name": "Медведев"
  },
  {
    "id": "3",
    "name": "Навальный"
  }
]


action == add-person
- Запрос:
http://195.110.59.16:8081/restapi-v3/?token=9876543210&action=add-person&name=Somebody
- Параметры:
-- name - Обязательный.

action == remove-person
- Запрос:
http://195.110.59.16:8081/restapi-v3/?token=9876543210&action=remove-person&id=13
- Параметры:
-- id - Обязательный. Поле "id" из таблицы БД "persons"

action == get-sites
- Запрос:
http://195.110.59.16:8081/restapi-v3/?token=9876543210&action=get-sites

action == add-site
- Запрос:
http://195.110.59.16:8081/restapi-v3/?token=9876543210&action=add-site&name=blahblah.ru
- Параметры:
-- name - Обязательный.

action == remove-site
- Запрос:
http://195.110.59.16:8081/restapi-v3/?token=9876543210&action=remove-site&id=3
- Параметры:
-- id - Обязательный. Поле "id" из таблицы БД "sites"

action == get-keywords
- Запрос:
http://195.110.59.16:8081/restapi-v3/?token=9876543210&action=get-keywords

action == add-keyword
- Запрос:
http://195.110.59.16:8081/rest-v2/?token=1&action=add-keyword&name=blahblah&person_id=2
- Параметры:
-- name - Обязательный.
-- id - Обязательный. Поле "id" из таблицы БД "persons"

action == remove-keyword{
- Запрос:
http://195.110.59.16:8081/rest-v2/?token=1&action=remove-keyword&id=2
- Параметры:
-- id - Обязательный. Поле "id" из таблицы БД "keywords"

action == get-users
- Запрос:
http://195.110.59.16:8081/restapi-v3/?token=9876543210&action=get-users

action == add-user
- Запрос:
http://195.110.59.16:8081/restapi-v3/?token=9876543210&action=add-user&login=test&password=test&email=test@test.ru&role=user
- Параметры:
-- login - Обязательный. Уникальный
-- password - Обязательный.
-- email - Обязательный.
-- role - Обязательный. "user" или "admin"

action == delete-user
- Запрос:
http://195.110.59.16:8081/restapi-v3/?token=9876543210&action=delete-user&id=9
- Параметры:
-- id - Обязательный. Поле "id" из таблицы БД "users"

-Обработка ошибок:
--Ответ: 
  { "error" : "Описание ошибки"}
  
What`s new:
Добавлены команды для работы с пользователями:
"get-users",
"add-user",
"delete-user"
