from django.shortcuts import render, HttpResponseRedirect, reverse
from .forms import KeywordsForm
from .models import Keywords, Persons
from django.forms import inlineformset_factory


def count_keywords(max_num, person_pk):
    max_num -= len(Keywords.objects.filter(person_id=person_pk))
    return max_num


def keywords_view(request):
    form = KeywordsForm()
    if 'view' in request.POST:
        keywords = Keywords.objects.filter(person_id=request.POST['dropdown'])
        person = Persons.objects.get(id=request.POST['dropdown'])
        request.session['person'] = person.name
        return render(request,
                      'admin_interface/keywords_view.html',
                      {"form": form, 'keywords': keywords, 'person': person.name})
    return render(request, 'admin_interface/keywords_view.html', {"form": form})


def person_keywords_edit(request):
    person = Persons.objects.get(name__iexact=request.session['person'])
    print(person.name)
    max_num = 6
    extra_fields = count_keywords(max_num, person.pk)
    KeywordsInlineFormset = inlineformset_factory(Persons,
                                                  Keywords,
                                                  fields=('name',),
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


