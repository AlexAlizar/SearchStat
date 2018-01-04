from django.urls import path
from .views import admin_interface
from .admin_sites_views import sites_view, sites_edit
from .admin_persons_views import persons_view, persons_edit
from .admin_keywords_views import keywords_view, person_keywords_edit
from .users_views import users, user_create, user_update, user_delete

app_name = 'admin_interface'

urlpatterns = [
    path('', admin_interface, name='admin_interface'),

    # sites_manage_views
    path('sites', sites_view, name='sites_view'),
    path('sites/edit/', sites_edit, name='sites_edit'),

    # persons_manage_views
    path('persons/', persons_view, name='persons_view'),
    path('persons/edit', persons_edit, name='persons_edit'),

    # keywords_manage_views
    path('keywords/edit', person_keywords_edit, name='person_keywords_edit'),
    path('keywords/', keywords_view, name='keywords_view'),

    # user_manage_views
    path('users/', users, name='users'),
    path('users/create/', user_create, name='user_create'),
    path('users/update/<int:pk>', user_update, name='user_update'),
    path('users/delete/<int:pk>', user_delete, name='user_delete'),


]