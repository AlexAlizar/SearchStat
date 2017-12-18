from django.shortcuts import render
from .models import modelSites

def admin_interface(request):

    context = {}
    template = 'base.html'
    return render(request, template, context)


def sites_view(request):
    sites = modelSites.objects.all()
    context = {'sites':sites}
    template = 'sites_view.html'
    return render(request, template, context)