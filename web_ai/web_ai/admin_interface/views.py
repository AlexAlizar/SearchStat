from django.shortcuts import get_list_or_404, render_to_response
from .models import Sites


def admin_interface(request):
    sites = get_list_or_404(Sites.objects.all())
    return render_to_response('base.html', {'sites': sites})

