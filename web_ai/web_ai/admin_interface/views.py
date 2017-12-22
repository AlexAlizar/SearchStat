from django.shortcuts import render, HttpResponseRedirect, get_list_or_404
from .models import ModelSite, ModelPerson

from urllib import request as urlrequest, error as urlerror


def admin_interface(request):
    sites = get_list_or_404(ModelSite.objects.all())
    persons = get_list_or_404(ModelPerson.objects.all())
    context = {'sites': sites, 'persons': persons}
    template = 'base.html'
    return render(request, template, context)
