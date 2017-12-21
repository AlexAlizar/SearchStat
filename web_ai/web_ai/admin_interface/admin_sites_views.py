from django.shortcuts import render, HttpResponseRedirect, get_list_or_404
from .models import ModelSites
from .forms import SitesManageForm

from urllib import request as urlrequest, error as urlerror


def admin_interface(request):
    context = {}
    template = 'base.html'
    return render(request, template, context)


def sites_view(request):
    sites = get_list_or_404(ModelSites.objects.all())
    context = {'sites': sites}
    template = 'sites_view.html'
    return render(request, template, context)


def manage_sites(request):
    form = SitesManageForm()
    context = {'form': form}
    template = 'sites_delete.html'
    if request.method == 'POST':
        multiple_select = request.POST.getlist('multiple_select')
        if request.POST.getlist('delete'):
            ModelSites.objects.filter(id__in=multiple_select).delete()
            return HttpResponseRedirect('/sites')
        else:
            request.session['sites'] = multiple_select
            return HttpResponseRedirect('/sites/edit', )
    else:
        return render(request, template, context)


def sites_edit(request):
    print(request.session.get('sites'))
    sites = ModelSites.objects.filter(id__in=request.session.get('sites'))
    context = {'sites':sites}
    template = 'sites_edit.html'
    return render(request, template, context)




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

