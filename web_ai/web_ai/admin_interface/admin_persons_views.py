from django.shortcuts import render, HttpResponseRedirect, get_list_or_404
from .models import ModelPerson


def persons_view(request):
    persons = get_list_or_404(ModelPerson.objects.all())
    context = {'persons': persons}
    return render(request, 'persons_view.html', context)


def persons_delete(request):
    pass


def persons_add(request):
    pass


def persons_edit(request):
    pass
