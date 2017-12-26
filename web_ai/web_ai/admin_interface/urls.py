from django.urls import path
from .views import admin_interface
from .admin_sites_views import *
from .admin_persons_views import *
from .admin_keywords_views import *

urlpatterns = [
    path('', admin_interface, name='admin_interface'),
    # sites_manage_views
    # path('sites', sites_view, name='sites_view'),
    path('sites/add/', add_site, name='add_site'),
    path('sites/delete', delete_sites, name='delete_site'),

    # persons_manage_views
    path('persons/add', persons_add, name='add_person'),
    path('persons/delete', persons_delete, name='delete_person'),

    # keywords_manage_views
    path('keywords/', keywords_view, name='keywords_view'),


]