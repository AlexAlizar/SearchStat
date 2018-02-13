import csv
import datetime
from django.contrib.auth.decorators import user_passes_test, login_required
from django.db.models import Count, Q
from django.shortcuts import get_list_or_404, render, HttpResponse
from django.http import JsonResponse

from .models import Sites, Pages
from .forms import ChoiceSiteForm


@login_required(login_url='/')
def statistic(request):
    sites = get_list_or_404(Sites.objects.all())
    agg_pages = Pages.objects.values('site__name').annotate(agg=Count('url'))
    get_urls_from_db = Pages.objects.values_list('site__name', 'url')
    total_sites = len(sites)
    total_pages = len(get_urls_from_db)
    site_choice = ChoiceSiteForm
    context = {'total_sites': total_sites,
               'total_pages': total_pages,
               'site_choice': site_choice,
               'agg_pages': agg_pages}
    return render(request, 'admin_interface/statistic.html', context)


@user_passes_test(lambda user: user.role == 'admin', login_url='/auth/login')
def export_as_csv(request):
    response = HttpResponse(content_type='text/csv')
    response['Content-Disposition'] = 'attachment; filename="pages_links_table.csv"'
    writer = csv.writer(response)
    writer.writerow(['site_name', 'urls'])
    pages_dict = {}
    for i in Pages.objects.values_list('site__name', 'url'):
        if i[0] in pages_dict:
            pages_dict[i[0]].append(i[1])
        else:
            pages_dict[i[0]] = [i[1]]

    for site, urls in pages_dict.items():
        data = list()
        data.append(site)
        for item in urls:
            data.append(item)
        writer.writerow(data)

    return response


def get_data(request):
    response = dict()
    error = 'Неверно указана дата'
    site = Sites.objects.get(id=request.POST['site_name'])
    date = request.POST['date'].split(' - ')
    date = [datetime.datetime.strptime(x, '%d.%m.%Y') for x in date]
    # sites = Pages.objects.extra(where=["EXTRACT(date FROM found_date_time) = %s"], params=[date[0].date]).filter(site_id=request.POST['site_name']).count()
    if len(date) < 1:
        return HttpResponse(error)
    elif len(date) == 1:
        qs = Pages.objects.filter(Q(found_date_time__gt=date[0].date())) and Q(site_id=request.POST['site_name'])

        return JsonResponse(response)
    elif len(date) == 2:
        pass
    else:
        return HttpResponse(error)
    return JsonResponse(response)

