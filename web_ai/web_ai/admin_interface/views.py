from django.shortcuts import render


def admin_interface(request):
    context = {}
    template = 'web_ai.html'
    return render(request, template, context)