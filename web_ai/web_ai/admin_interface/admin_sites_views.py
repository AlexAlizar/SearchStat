from django.core.validators import URLValidator
from django.core.exceptions import ValidationError
from django.shortcuts import render, render_to_response, redirect
from django.forms import modelformset_factory
from .models import ModelSite
from .forms import SitesManageForm


def count_sites(max_num):
    max_num -= len(ModelSite.objects.all())
    return max_num


def sites_view(request):
    sites = ModelSite.objects.all()
    if sites:
        return render_to_response('admin_interface/sites_view.html', {'sites': sites})
    else:
        message = 'Nothing to display'
        return render_to_response('admin_interface/sites_view.html', {'message': message})


def sites_edit(request):
    sites = ModelSite.objects.all()
    max_num = 3
    extra_fields = count_sites(max_num)
    SitesModelFormset = modelformset_factory(ModelSite,
                                             SitesManageForm,
                                             can_delete=True,
                                             extra=extra_fields,
                                             )
    if request.method == 'POST':
        formset = SitesModelFormset(request.POST)
        if formset.is_valid():
            formset.save()
            return redirect('/ai/sites')
        else:
            formset = SitesModelFormset(request.POST)
            return render(request, 'admin_interface/sites_edit.html', {'formset': formset})
    else:
        formset = SitesModelFormset()
        return render(request, 'admin_interface/sites_edit.html', {'sites': sites, 'formset': formset})