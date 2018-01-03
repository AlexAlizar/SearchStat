from django.urls import path
from .views import admin_interface
from .admin_sites_views import sites_view, add_site, delete_sites
from .admin_persons_views import persons_view, person_keyword_edit, persons_edit
from .admin_keywords_views import keywords_view, keywords_add, keywords_delete, keywords_edit

app_name = 'admin_interface'

urlpatterns = [
    path('', admin_interface, name='admin_interface'),
    # sites_manage_views
    path('sites', sites_view, name='sites_view'),
    path('sites/add/', add_site, name='add_site'),
    path('sites/delete', delete_sites, name='delete_site'),

    # persons_manage_views
    path('persons/', persons_view, name='persons_view'),
    path('persons/<int:pk>', person_keyword_edit, name='person_keyword_edit'),
    path('persons/edit', persons_edit, name='persons_edit'),

    # keywords_manage_views
    path('keywords/', keywords_view, name='keywords_view'),
    path('keywords/add', keywords_add, name='keywords_add'),
    path('keywords/delete', keywords_delete, name='keywords_delete'),

]