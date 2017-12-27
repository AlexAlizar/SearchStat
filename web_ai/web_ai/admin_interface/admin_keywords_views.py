from django.shortcuts import render, HttpResponseRedirect, get_object_or_404, get_list_or_404
from .forms import KeywordsForm
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
            result.append(class_name(keyword=str(i), person_id_id=int(person_id)))
        return result


def keywords_view(request):
    form = KeywordsForm()
    if request.method == 'POST':
        keywords = ModelKeyword.objects.filter(person_id__name__icontains=request.POST['dropdown'])
        if 'view' in request.POST:
            return render(request,
                          'keywords_view.html',
                          {"form": form, 'keywords': keywords, 'name': request.POST['dropdown']})
        if 'add' in request.POST:
             keywords_add(request)
    return render(request, 'keywords_view.html', {"form": form})


def keywords_add(request):
    form = KeywordsForm(request.POST)
    if form.is_valid():
        keywords_list = set(clear_kw(request.POST['keywords_add']))
        person = ModelPerson.objects.get(name__icontains=request.POST['dropdown'])
        obj = objects_bulk(ModelKeyword, keywords_list, person.id)
        ModelKeyword.objects.bulk_create(objs=obj)
        return HttpResponseRedirect('/keywords')



def keywords_delete(request):
    pass


def keywords_edit(request):
    pass
