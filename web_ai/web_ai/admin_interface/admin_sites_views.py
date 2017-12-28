from django.shortcuts import render, HttpResponseRedirect, get_list_or_404
from .models import ModelSite
from .forms import SitesManageForm

from urllib import request as urlrequest, error as urlerror


def sites_view(request):
    sites = ModelSite.objects.all()
    return render(request, 'sites_view.html', {'sites': sites})


def delete_sites(request):
    form = SitesManageForm()
    if request.method == 'POST':
        multiple_select = request.POST.getlist('multiple_select')
        if request.POST.getlist('delete'):
            ModelSite.objects.filter(id__in=multiple_select).delete()
            return HttpResponseRedirect('/sites')
    else:
        return render(request, 'sites_delete.html', {'form': form})


def add_site(request):
    if request.method == 'POST':
        form = SitesManageForm(request.POST)
        try:
            url = urlrequest.urlopen(request.POST.get('name'))
            if form.is_valid() and url.getcode() == 200:
                form.save()
                return HttpResponseRedirect('/sites')
            else:
                return render(request, 'sites_add.html', {'form': form})
        except urlerror.URLError:
            form = SitesManageForm()
            return render(request, 'sites_add.html', {'form': form})
    else:
        form = SitesManageForm()
        return render(request, 'sites_add.html', {'form': form})

