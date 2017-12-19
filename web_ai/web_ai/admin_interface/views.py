from django.shortcuts import render, HttpResponseRedirect
from .models import ModelSites
from .forms import SitesManageForm

def admin_interface(request):

    context = {}
    template = 'base.html'
    return render(request, template, context)


def sites_view(request):
    form = SitesManageForm()
    context = {'form': form}
    template = 'sites_view.html'
    if request.POST.get('action') == 'save':
        form = SitesManageForm(request.POST)
        if form.is_valid():
            form.name = request.POST.get('name')
            form.save()
            return render(request, template, context)
        return render(request, template, context)
    return render(request, template, context)