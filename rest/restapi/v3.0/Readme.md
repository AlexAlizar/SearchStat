REST API v3.0
URL:
  http://searchstat.cf:8081/restapi-v3/?
  ��� �� IP ������
  http://195.110.59.16:8081/restapi-v3/?
�����: GET
�����: "application/json; charset=utf-8"

1. ����� ������ � �������� ���������� ������� GET � ������������ ���������� action (��� �������� ������� ����)!
2. ������ � �������� ���������� ����� ����� �����������:
����� ������ ����������� � �������� ������������� ��������� token(����� ������ ����������� �� �����/������),
�� ������ ��������������� ���� token �� ������� users �������� ��.
��� ��������� ������ ����� ������ ����������� �� ������ � ������� , � ����� �������� �����.

������ � ��������:
action == auth
- ������:
http://195.110.59.16:8081/restapi-v3/?action=auth&login=admin&password=admin
- �����:
"9876543210"
- �������������:
�������� ������� � ������� ����� ���������� � ��


action == general-statistic
- ������:
http://195.110.59.16:8081/restapi-v3/?token=9876543210&action=general-statistic&site=lenta.ru
- ���������:
--site - ������������. ���� "name" �� ������� �� "sites"
- ������ ������:
[
  {
    "name": "��������",
    "rank": "18"
  },
  {
    "name": "���������",
    "rank": "12"
  },
  {
    "name": "�����",
    "rank": "33"
  }
]

action == daily-statistic
- ������:
http://195.110.59.16:8081/restapi-v3/?token=9876543210&action=daily-statistic&person=�����&date1=2017-12-29&date2=2017-12-30&site=meduza.io
- ���������:
-- person - ������������. ���� "name" �� ������� �� "persons"
-- date1 - ������������. ��������� ���� ������� �������
-- date2 - ������������. �������� ���� ������� �������
-- site - ������������. ���� "name" �� ������� �� "sites"
- �����:
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
- ������:
http://195.110.59.16:8081/restapi-v3/?token=9876543210&action=get-persons
- �����:
[
  {
    "id": "1",
    "name": "�����"
  },
  {
    "id": "2",
    "name": "��������"
  },
  {
    "id": "3",
    "name": "���������"
  }
]


action == add-person
- ������:
http://195.110.59.16:8081/restapi-v3/?token=9876543210&action=add-person&name=Somebody
- ���������:
-- name - ������������.

action == remove-person
- ������:
http://195.110.59.16:8081/restapi-v3/?token=9876543210&action=remove-person&id=13
- ���������:
-- id - ������������. ���� "id" �� ������� �� "persons"

action == get-sites
- ������:
http://195.110.59.16:8081/restapi-v3/?token=9876543210&action=get-sites

action == add-site
- ������:
http://195.110.59.16:8081/restapi-v3/?token=9876543210&action=add-site&name=blahblah.ru
- ���������:
-- name - ������������.

action == remove-site
- ������:
http://195.110.59.16:8081/restapi-v3/?token=9876543210&action=remove-site&id=3
- ���������:
-- id - ������������. ���� "id" �� ������� �� "sites"

action == get-keywords
- ������:
http://195.110.59.16:8081/restapi-v3/?token=9876543210&action=get-keywords

action == add-keyword
- ������:
http://195.110.59.16:8081/rest-v2/?token=1&action=add-keyword&name=blahblah&person_id=2
- ���������:
-- name - ������������.
-- id - ������������. ���� "id" �� ������� �� "persons"

action == remove-keyword{
- ������:
http://195.110.59.16:8081/rest-v2/?token=1&action=remove-keyword&id=2
- ���������:
-- id - ������������. ���� "id" �� ������� �� "keywords"

action == get-users
- ������:
http://195.110.59.16:8081/restapi-v3/?token=9876543210&action=get-users

action == add-user
- ������:
http://195.110.59.16:8081/restapi-v3/?token=9876543210&action=add-user&login=test&password=test&email=test@test.ru&role=user
- ���������:
-- login - ������������. ����������
-- password - ������������.
-- email - ������������.
-- role - ������������. "user" ��� "admin"

action == delete-user
- ������:
http://195.110.59.16:8081/restapi-v3/?token=9876543210&action=delete-user&id=9
- ���������:
-- id - ������������. ���� "id" �� ������� �� "users"

-��������� ������:
--�����: 
  { "error" : "�������� ������"}
  
What`s new:
��������� ������� ��� ������ � ��������������:
"get-users",
"add-user",
"delete-user"
