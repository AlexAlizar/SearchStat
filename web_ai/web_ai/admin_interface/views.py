from django.shortcuts import get_list_or_404, render_to_response
from .models import Sites, Pages
from django.db.models import Count

from django.contrib.auth.decorators import user_passes_test


def admin_interface(request):
    sites = get_list_or_404(Sites.objects.all())
    return render_to_response('base.html', {'sites': sites})


@user_passes_test(lambda user: user.is_staff, login_url='/auth/login')
def statistic(request):
    sites = get_list_or_404(Sites.objects.all())
    agg_pages = Pages.objects.values('site__name').annotate(agg=Count('url'))
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
                                                 'pages_dict': pages_dict,
                                                 'agg_pages': agg_pages})
