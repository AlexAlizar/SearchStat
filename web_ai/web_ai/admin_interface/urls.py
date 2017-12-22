from django.urls import path
from .admin_sites_views import admin_interface,\
                                sites_view,\
                                add_site,\
                                delete_sites
from .admin_persons_views import persons_view


urlpatterns = [
    path('', admin_interface, name='admin_interface'),
    # sites_manage_views
    path('sites', sites_view, name='sites_view'),
    path('sites/add/', add_site, name='add_site'),
    path('sites/delete', delete_sites, name='delete_site'),

    # persons_manage_views

    path('persons', persons_view, name='persons_view'),

    # keywords_manage_views

]