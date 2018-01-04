from django.shortcuts import render, redirect
from django.forms import inlineformset_factory, modelformset_factory
from .forms import PersonsManageForm
from .models import ModelPerson, ModelKeyword


def count_persons(max_num):
    max_num -= len(ModelPerson.objects.all())
    return max_num



def persons_view(request):
    persons = ModelPerson.objects.all()
    return render(request, 'admin_interface/persons_view.html', {'persons': persons})


def persons_edit(request):
    persons = ModelPerson.objects.all()
    max_num = 5
    extra_fields = count_persons(max_num)
    PersonsModelFormset = modelformset_factory(ModelPerson,
                                               PersonsManageForm,
                                               can_delete=True,
                                               extra=extra_fields)
    if request.method == 'POST':
        formset = PersonsModelFormset(request.POST)
        if formset.is_valid():
            formset.save()
            return redirect('/ai/persons')
    else:
        formset = PersonsModelFormset()
    return render(request, 'admin_interface/persons_edit.html', {'formset': formset, 'persons': persons})


