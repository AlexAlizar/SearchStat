from django.contrib.auth.decorators import user_passes_test
from django.shortcuts import render, render_to_response, redirect
from django.forms import modelformset_factory
from .models import Sites
from .forms import SitesManageForm


def count_sites(max_num):
    max_num -= len(Sites.objects.all())
    return max_num


@user_passes_test(lambda user: user.is_staff, login_url='/auth/login')
def sites_view(request):
    sites = Sites.objects.all()
    if sites:
        return render_to_response('admin_interface/sites_view.html', {'sites': sites})
    else:
        message = 'Nothing to display'
        return render_to_response('admin_interface/sites_view.html', {'message': message})


@user_passes_test(lambda user: user.is_staff, login_url='/auth/login')
def sites_edit(request):
    sites = Sites.objects.all()
    max_num = 3
    extra_fields = count_sites(max_num)
    SitesModelFormset = modelformset_factory(Sites,
                                             SitesManageForm,
                                             can_delete=True,
                                             extra=extra_fields,
                                             validate_min=1,
                                             min_num=1
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