from django.urls import path
from .views import admin_interface, sites_view
urlpatterns = [
    path('', admin_interface, name='web_ai'),
    path('sites', sites_view, name='sites_view'),
]