from django.urls import path
from .views import admin_interface, sites_view, add_site
urlpatterns = [
    path('', admin_interface, name='admin_interface'),
    path('sites', sites_view, name='sites_view'),
    path('sites/add/', add_site, name='add_site')
]