from django.contrib import admin
from .models import ModelSite, ModelPerson, ModelKeyword

# Register your models here.
admin.site.register(ModelSite)
admin.site.register(ModelPerson)
admin.site.register(ModelKeyword)