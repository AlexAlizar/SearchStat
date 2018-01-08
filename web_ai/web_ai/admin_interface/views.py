from django.shortcuts import get_list_or_404, render_to_response
from .models import Sites, Pages
from collections import defaultdict


def admin_interface(request):
    sites = get_list_or_404(Sites.objects.all())
    return render_to_response('base.html', {'sites': sites})


def statistic(request):
    sites = get_list_or_404(Sites.objects.all())

    x = Pages.objects.values_list('site__name', 'url')
    pages_dict = {}
    for i in x:
        if i[0] in pages_dict:
            pages_dict[i[0]].append(i[1])
        else:
            pages_dict[i[0]] = [i[1]]

    total_sites = len(sites)
    total_pages = len(x)
    return render_to_response('statistic.html', {'total_sites': total_sites,
                                                 'total_pages': total_pages,
                                                 'pages_dict': pages_dict})
