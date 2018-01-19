"""web_ai URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/2.0/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path, include, re_path
from django.conf import settings
from django.contrib.auth import views as auth_views


# 3rd party imports
import debug_toolbar


urlpatterns = [
    path('admin/', admin.site.urls),
    path('', include('landing_page.urls', namespace='main')),
    path('ai/', include('admin_interface.urls', namespace='ai')),
    path('auth/', include('authapp.urls', namespace='auth')),
    path('contacts/', include('contacts.urls', namespace='contacts')),
    path('password_reset/', auth_views.password_reset,  {'template_name': 'authapp/password_reset_form.html'}, name='password_reset'),
    path('password_reset/done/', auth_views.password_reset_done, {'template_name': 'authapp/password_reset_done.html'}, name='password_reset_done'),
    re_path(r'^reset/(?P<uidb64>[0-9A-Za-z_\-]+)/(?P<token>[0-9A-Za-z]{1,13}-[0-9A-Za-z]{1,20})/$',
        auth_views.password_reset_confirm, {'template_name': 'authapp/password_reset_confirm.html'}, name='password_reset_confirm'),
    path('reset/done/', auth_views.password_reset_complete, {'template_name': 'authapp/password_reset_complete.html'}, name='password_reset_complete'),
]

if settings.DEBUG:
    urlpatterns = [
        path('__debug__/', include(debug_toolbar.urls))
    ] + urlpatterns
