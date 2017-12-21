from django.urls import path
from .admin_sites_views import admin_interface, sites_view, add_site, manage_sites, sites_edit


urlpatterns = [
    path('', admin_interface, name='admin_interface'),
    # sites_manage_views
    path('sites', sites_view, name='sites_view'),
    path('sites/add/', add_site, name='add_site'),
    path('sites/delete', manage_sites, name='delete_site'),
    path('sites/edit', sites_edit, name='sites_edit'),

    # persons_manage_views

    # keywords_manage_views

]