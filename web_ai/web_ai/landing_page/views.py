from django.contrib import auth
from django.shortcuts import render, HttpResponseRedirect, reverse
from django.views.decorators.csrf import csrf_exempt
from authapp.forms import LoginForm


@csrf_exempt
def main_view(request):
    
    form = LoginForm(data=request.POST)
    if request.method == 'POST' and form.is_valid():
        username = request.POST['username']
        password = request.POST['password']
        user = auth.authenticate(username=username, password=password)
        if user:
            auth.login(request, user)
            return HttpResponseRedirect(reverse('ai:statistic'))
    else:
        return render(request, 'landing_page/index.html', {'form': form})