from django.shortcuts import render, HttpResponseRedirect, get_object_or_404
from .models import ModelSites
from .forms import SitesManageForm

from urllib import request as urlrequest, error as urlerror


def admin_interface(request):
    context = {}
    template = 'base.html'
    return render(request, template, context)


def sites_view(request):
    sites = ModelSites.objects.all()
    context = {'sites': sites}
    template = 'sites_view.html'
    return render(request, template, context)


def delete_site(request, id):
    site = get_object_or_404(ModelSites, id=id)
    if request.method == 'POST':
        site.delete()
        return HttpResponseRedirect('sites_view')
    else:
        pass


def edit_site(request):
    pass


def add_site(request):
    if request.method == 'POST':
        form = SitesManageForm(request.POST)
        try:
            url = urlrequest.urlopen(request.POST.get('name'))
            if form.is_valid() and url.getcode() == 200:
                form.save()
                return HttpResponseRedirect('sites_view')
            else:
                return render(request, 'sites_view.html', {'form': form})
        except urlerror.URLError:
            form = SitesManageForm()
            return render(request, 'sites_add.html', {'form': form})
    else:
        form = SitesManageForm()
        return render(request, 'sites_add.html', {'form': form})
