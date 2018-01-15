from django.contrib.auth.decorators import user_passes_test
from django.shortcuts import render, redirect
from django.forms import inlineformset_factory, modelformset_factory
from .forms import PersonsManageForm
from .models import Persons


def count_persons(max_num):
    max_num -= len(Persons.objects.all())
    return max_num


@user_passes_test(lambda user: user.is_staff, login_url='/auth/login')
def persons_view(request):
    persons = Persons.objects.all()
    return render(request, 'admin_interface/persons_view.html', {'persons': persons})


@user_passes_test(lambda user: user.is_staff, login_url='/auth/login')
def persons_edit(request):
    persons = Persons.objects.all()
    max_num = 10
    extra_fields = count_persons(max_num)
    PersonsModelFormset = modelformset_factory(Persons,
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


