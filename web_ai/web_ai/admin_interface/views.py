from django.shortcuts import get_list_or_404, render, HttpResponse
from .models import Sites, Pages
from django.db.models import Count
from django.contrib.auth.decorators import user_passes_test
import csv


@user_passes_test(lambda user: user.role == 'admin', login_url='/auth/login')
def statistic(request):
    sites = get_list_or_404(Sites.objects.all())
    agg_pages = Pages.objects.values('site__name').annotate(agg=Count('url'))
    get_urls_from_db = Pages.objects.values_list('site__name', 'url')
    pages_dict = {}
    for i in get_urls_from_db:
        if i[0] in pages_dict:
            pages_dict[i[0]].append(i[1])
        else:
            pages_dict[i[0]] = [i[1]]
    total_sites = len(sites)
    total_pages = len(get_urls_from_db)
    request.session['pages_dict'] = pages_dict
    return render(request, 'admin_interface/statistic.html', {'total_sites': total_sites,
                                                 'total_pages': total_pages,
                                                 'pages_dict': pages_dict,
                                                 'agg_pages': agg_pages})


@user_passes_test(lambda user: user.role == 'admin', login_url='/auth/login')
def export_as_csv(request):
    response = HttpResponse(content_type='text/csv')
    response['Content-Disposition'] = 'attachment; filename="pages_links_table.csv"'
    writer = csv.writer(response)
    writer.writerow(['site_name', 'urls'])

    for site, urls in request.session['pages_dict'].items():
        data = []
        data.append(site)
        for item in urls:
            data.append(item)
        writer.writerow(data)

    return response
