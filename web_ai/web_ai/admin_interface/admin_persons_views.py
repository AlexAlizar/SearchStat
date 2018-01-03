from django.shortcuts import render, redirect
from django.forms import inlineformset_factory, modelformset_factory
from .forms import PersonsManageForm
from .models import ModelPerson, ModelKeyword


def count_persons(max_len):
    max_len -= len(ModelPerson.objects.all())
    return max_len


def persons_view(request):
    persons = ModelPerson.objects.all()
    return render(request, 'persons_view.html', {'persons': persons})


def persons_edit(request):
    persons = ModelPerson.objects.all()
    max_len = 5
    extra_fields = count_persons(max_len)
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
    return render(request, 'persons_edit.html', {'formset': formset, 'persons':persons})


def person_keyword_edit(request, pk):
    person = ModelPerson.objects.get(id=pk)
    KeywordsInlineFormset = inlineformset_factory(ModelPerson,
                                                  ModelKeyword,
                                                  fields=('keyword',),
                                                  extra=1,
                                                  )
    if request.method == 'POST':
        formset = KeywordsInlineFormset(request.POST, instance=person)
        if formset.is_valid():
            formset.save()
            return redirect('/ai/persons')
    else:
        formset = KeywordsInlineFormset(instance=person)
    return render(request, 'persons_edit.html', {'formset': formset, 'person': person})

