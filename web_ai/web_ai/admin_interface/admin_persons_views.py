from django.shortcuts import render, HttpResponseRedirect

from .forms import PersonsManageForm
from .models import ModelPerson


def persons_view(request):
    persons = ModelPerson.objects.all()
    return render(request, 'persons_view.html', {'persons': persons})


def persons_delete(request):
    form = PersonsManageForm()
    if request.method == 'POST':
        multiple_select = request.POST.getlist('multiple_select')
        if request.POST.getlist('delete'):
            ModelPerson.objects.filter(id__in=multiple_select).delete()
            return HttpResponseRedirect('/persons')
    else:
        return render(request, 'persons_delete.html', {'form': form})


def persons_add(request):
    if request.method == 'POST':
        form = PersonsManageForm(request.POST)
        if form.is_valid():
            form.save()
            return HttpResponseRedirect('/persons')
        else:
            return render(request, 'persons_add.html', {'form': form})
    else:
        form = PersonsManageForm()
        return render(request, 'persons_add.html', {'form': form})


def persons_edit(request):
    pass
