# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey has `on_delete` set to the desired behavior.
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from django.db import models


class Keywords(models.Model):
    name = models.CharField(max_length=2048, blank=True, null=True)
    person = models.ForeignKey('Persons', models.DO_NOTHING, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'keywords'

    def __str__(self):
        return self.name


class Pages(models.Model):
    url = models.CharField(max_length=2048)
    site = models.ForeignKey('Sites', models.DO_NOTHING)
    found_date_time = models.DateTimeField()
    last_scan_date = models.DateTimeField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'pages'


class PersonPageRank(models.Model):
    person = models.ForeignKey('Persons', models.DO_NOTHING)
    page = models.ForeignKey(Pages, models.DO_NOTHING, blank=True, null=True)
    rank = models.IntegerField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'person_page_rank'


class Persons(models.Model):
    name = models.CharField(max_length=2048, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'persons'

    def __str__(self):
        return self.name


class Sites(models.Model):
    name = models.CharField(max_length=2048, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'sites'

    def __str__(self):
        return self.name


class Users(models.Model):
    login = models.CharField(max_length=256)
    password = models.CharField(max_length=2048)
    token = models.CharField(max_length=2048)
    creation_date = models.DateTimeField()
    last_login_date = models.DateTimeField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'users'
