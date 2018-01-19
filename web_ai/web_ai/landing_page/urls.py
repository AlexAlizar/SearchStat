from django.urls import path
from .views import main_view

app_name = 'landing_page'

urlpatterns = [
    path('', main_view, name='main'),
]