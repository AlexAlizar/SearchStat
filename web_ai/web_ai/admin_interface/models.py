from django.db import models


class ModelSite(models.Model):
    name = models.CharField(max_length=2048, unique=True)

    def __str__(self):
        return self.name


class ModelPerson(models.Model):
    name = models.CharField(max_length=2048, unique=True)

    def __str__(self):
        return self.name


class ModelKeyword(models.Model):
    person_id = models.ForeignKey(ModelPerson, on_delete=None)
    keyword = models.CharField(max_length=2048, unique=True)