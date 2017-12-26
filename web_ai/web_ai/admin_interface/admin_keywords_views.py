from django.shortcuts import render, HttpResponseRedirect, get_object_or_404
from .forms import PersonDropDown
from .models import ModelKeyword

def keywords_view(request):
    form = PersonDropDown()
    if request.method == 'POST':
        keywords = ModelKeyword.objects.filter(person_id__name__icontains=request.POST['dropdown'])
        return render(request, 'keywords_view.html', {"form": form, 'keywords': keywords, 'name':request.POST['dropdown']})

    return render(request, 'keywords_view.html', {"form": form})


def keywords_delete(request):
    pass


def keywords_add(request):
    pass


def keywords_edit(request):
    pass
