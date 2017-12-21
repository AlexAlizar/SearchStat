from django.urls import path
from .admin_sites_views import admin_interface, sites_view, add_site, delete_site
urlpatterns = [
    path('', admin_interface, name='admin_interface'),
    # sites_manage_views
    path('sites', sites_view, name='sites_view'),
    path('sites/add/', add_site, name='add_site'),
    path('sites/delete', delete_site, name='delete_site'),

    # persons_manage_views

    # keywords_manage_views

]