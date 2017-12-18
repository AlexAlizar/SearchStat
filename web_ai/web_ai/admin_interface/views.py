from django.shortcuts import render


def admin_interface(request):

    context = {}
    template = 'base.html'
    return render(request, template, context)