from django.shortcuts import render, HttpResponseRedirect, reverse
from .forms import KeywordsForm
from .models import ModelKeyword, ModelPerson
from django.forms import inlineformset_factory


def count_keywords(max_num, person_pk):
    max_num -= len(ModelKeyword.objects.filter(person_id=person_pk))
    return max_num


def keywords_view(request):
    form = KeywordsForm()
    if 'view' in request.POST:
        keywords = ModelKeyword.objects.filter(person__id__icontains=request.POST['dropdown'])
        person = ModelPerson.objects.get(id=request.POST['dropdown'])
        request.session['person'] = person.name
        return render(request,
                      'admin_interface/keywords_view.html',
                      {"form": form, 'keywords': keywords, 'person': person.name})
    return render(request, 'admin_interface/keywords_view.html', {"form": form})


def person_keywords_edit(request):
    person = ModelPerson.objects.get(name__iexact=request.session['person'])
    max_num = 6
    extra_fields = count_keywords(max_num, person.pk)
    KeywordsInlineFormset = inlineformset_factory(ModelPerson,
                                                  ModelKeyword,
                                                  fields=('keyword',),
                                                  extra=extra_fields,
                                                  )
    if request.method == 'POST':
        formset = KeywordsInlineFormset(request.POST, instance=person)
        if formset.is_valid():
            formset.save()
            return HttpResponseRedirect('/ai/keywords')
    else:
        formset = KeywordsInlineFormset(instance=person)
    return render(request, 'admin_interface/person_keywords_edit.html', {'formset': formset, 'person': person})


