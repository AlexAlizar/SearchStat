from django.shortcuts import render, HttpResponseRedirect, reverse
from django.contrib import auth
from authapp.forms import LoginForm


def main_view(request):
    form = LoginForm(data=request.POST)
    if request.method == 'POST' and form.is_valid():
        username = request.POST['username']
        password = request.POST['password']
        user = auth.authenticate(username=username, password=password)
        if user:
            auth.login(request, user)
            return HttpResponseRedirect(reverse('ai:statistic'))

    return render(request, 'landing_page/index.html', {'form': form})