from django.shortcuts import render, HttpResponseRedirect

from .forms import PersonsManageForm
from .models import ModelPerson


def persons_delete(request):
    form = PersonsManageForm()

    if request.method == 'POST':
        multiple_select = request.POST.getlist('multiple_select')
        if request.POST.getlist('delete'):
            ModelPerson.objects.filter(id__in=multiple_select).delete()
            return HttpResponseRedirect('/')
    else:
        print('here')
        return render(request, 'persons_delete.html', {'form': form})


def persons_add(request):
    print(request)
    if request.method == 'POST':
        form = PersonsManageForm(request.POST)
        if form.is_valid():
            form.save()
            return HttpResponseRedirect('/')
        else:
            return render(request, 'sites_add.html', {'form': form})
    else:
        print('here')
        form = PersonsManageForm()
        return render(request, 'sites_add.html', {'form': form})


def persons_edit(request):
    pass
