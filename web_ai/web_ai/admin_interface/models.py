from django.db import models


class modelSites(models.Model):
    name = models.CharField(max_length=2048)

    def __str__(self):
        return self.name