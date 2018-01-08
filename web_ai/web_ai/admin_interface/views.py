from django.shortcuts import get_list_or_404, render_to_response
from .models import Sites, Pages


def admin_interface(request):
    sites = get_list_or_404(Sites.objects.all())
    return render_to_response('base.html', {'sites': sites})


def statistic(request):
    sites = get_list_or_404(Sites.objects.all())
    pages = get_list_or_404(Pages.objects.all())
    total_sites = len(sites)
    total_pages = len(pages)
    return render_to_response('statistic.html', {'total_sites': total_sites, 'total_pages': total_pages})
