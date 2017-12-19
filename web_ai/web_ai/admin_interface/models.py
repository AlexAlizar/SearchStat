from django.db import models


class ModelSites(models.Model):
    name = models.CharField(max_length=2048, unique=True)

    def __str__(self):
        return self.name
