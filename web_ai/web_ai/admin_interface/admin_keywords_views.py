from django.shortcuts import render, HttpResponseRedirect, get_object_or_404, get_list_or_404
from .forms import KeywordsForm, KeywordsAddForm
from .models import ModelKeyword, ModelPerson


def clear_kw(keywords):
    keywords = keywords.replace(' ', '')
    keywords = keywords.split(',')
    output = []
    for item in keywords:
        if len(item) > 1:
            output.append(str(item))
    return output


def objects_bulk(class_name, lst, person_id):
        result = []
        for i in lst:
            result.append(class_name(keyword=str(i), person_id=int(person_id)))
        return result


def keywords_view(request):
    form = KeywordsForm()
    if 'view' in request.POST:
        keywords = ModelKeyword.objects.filter(person__id__icontains=request.POST['dropdown'])
        person = ModelPerson.objects.get(id=request.POST['dropdown'])
        request.session['person'] = person.name
        return render(request,
                      'keywords_view.html',
                      {"form": form, 'keywords': keywords, 'person': person.name})
    return render(request, 'keywords_view.html', {"form": form})


def keywords_add(request):
    person = ModelPerson.objects.get(name__icontains=request.session['person'])
    keywords = ModelKeyword.objects.filter(person__name__icontains=person.name)
    form = KeywordsAddForm()
    if request.method == 'POST':
        form = KeywordsAddForm(request.POST)
        if form.is_valid():
            keywords_list = set(clear_kw(request.POST['keywords_add']))
            obj = objects_bulk(ModelKeyword, keywords_list, person.id)
            ModelKeyword.objects.bulk_create(objs=obj)
            return HttpResponseRedirect('/keywords')
    return render(request, 'keywords_add.html', {'form': form, 'person': person, 'keywords': keywords})


def keywords_delete(request):
    person = ModelPerson.objects.get(name__icontains=request.session['person'])
    form = KeywordsForm()
    form.fields['multiple_select'].queryset = ModelKeyword.objects.filter(person__name__icontains=person.name)
    if request.method == 'POST':
        keywords_id = request.POST.getlist('multiple_select')
        ModelKeyword.objects.filter(id__in=keywords_id).delete()
        return HttpResponseRedirect('/keywords')
    return render(request, 'keywords_delete.html', {'form': form})


def keywords_edit(request):
    pass
