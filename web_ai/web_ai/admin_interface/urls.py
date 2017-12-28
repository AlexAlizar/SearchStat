from django.urls import path
from .views import admin_interface
from .admin_sites_views import sites_view, add_site, delete_sites
from .admin_persons_views import persons_view, persons_add, persons_delete
from .admin_keywords_views import *

urlpatterns = [
    path('', admin_interface, name='admin_interface'),
    # sites_manage_views
    path('sites', sites_view, name='sites_view'),
    path('sites/add/', add_site, name='add_site'),
    path('sites/delete', delete_sites, name='delete_site'),

    # persons_manage_views
    path('persons/', persons_view, name='persons_view'),
    path('persons/add', persons_add, name='add_person'),
    path('persons/delete', persons_delete, name='delete_person'),

    # keywords_manage_views
    path('keywords/', keywords_view, name='keywords_view'),


]