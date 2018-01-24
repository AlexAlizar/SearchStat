# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey has `on_delete` set to the desired behavior.
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from django.db import models
from django.contrib.auth.models import AbstractBaseUser, UserManager
import datetime


class AliasField(models.Field):
    def contribute_to_class(self, cls, name, virtual_only=False):
        super(AliasField, self).contribute_to_class(cls, name, private_only=True)
        setattr(cls, name, self)

    def __get__(self, instance, instance_type=None):
        return getattr(instance, self.db_column)


class Users(AbstractBaseUser):

    login = models.CharField(max_length=255, unique=True)
    password = models.CharField(max_length=2048)
    email = models.CharField(max_length=255, blank=True, null=True, unique=True)
    token = models.CharField(max_length=2048)
    role = models.CharField(max_length=255, default='user')
    creation_date = models.DateTimeField(default=datetime.datetime.now)
    last_login_date = models.DateTimeField(default=datetime.datetime.now, blank=True, null=True)

    objects = UserManager()

    username = AliasField(db_column='login')
    last_login = AliasField(db_column='last_login_date')

    USERNAME_FIELD = 'login'
    REQUIRED_FIELDS = ('password',)

    class Meta:
        managed = False
        db_table = 'users'

    def __str__(self):
        return str(self.login)

    def set_password(self, raw_password):
        self.password = raw_password



class Persons(models.Model):
    name = models.CharField(max_length=2048, blank=True, null=True)
    user = models.ForeignKey('Users', models.DO_NOTHING, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'persons'

    def __str__(self):
        return self.name


class Sites(models.Model):
    name = models.CharField(max_length=2048, blank=True, null=True)
    user = models.ForeignKey('Users', models.DO_NOTHING, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'sites'

    def __str__(self):
        return self.name


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

    def __str__(self):
        return self.url


class PersonPageRank(models.Model):
    person = models.ForeignKey('Persons', models.DO_NOTHING)
    page = models.ForeignKey(Pages, models.DO_NOTHING, blank=True, null=True)
    rank = models.IntegerField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'person_page_rank'


