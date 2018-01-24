from django.contrib import auth, messages
from django.contrib.auth.forms import PasswordChangeForm
from django.shortcuts import render, HttpResponseRedirect
from django.urls import reverse
from .auth_backend import AuthBackend

#local imports
from authapp.forms import RegisterForm, LoginForm, EditForm


def login(request):
    title = 'Вход'

    login_form = LoginForm(data=request.POST)

    if request.method == 'POST' and login_form.is_valid():
        username = request.POST['username']
        password = request.POST['password']
        user = auth.authenticate(username=username, password=password)
        if user:
            auth.login(request, user)
            return HttpResponseRedirect('/')
        
    content = {'title': title, 'login_form': login_form}

    return render(request, 'authapp/login.html', content)


def logout(request):
    auth.logout(request)
    return HttpResponseRedirect('/')


def register(request):
    title = 'Регистрация'
    if request.method == 'POST':
        register_form = RegisterForm(request.POST)

        if register_form.is_valid():
            register_form.save()
            return HttpResponseRedirect(reverse('landing_page:main'))
    else:
        register_form = RegisterForm()

    content = {'title': title, 'register_form': register_form}

    return render(request, 'authapp/register.html', content)


def edit(request):
    title = 'Редактирование'

    if request.method == 'POST':
        edit_form = EditForm(request.POST, instance=request.user)
        if edit_form.is_valid():
            edit_form.save()
            return HttpResponseRedirect(reverse('auth:edit'))
    else:
        edit_form = EditForm(instance=request.user)

    content = {'title': title, 'edit_form': edit_form}

    return render(request, 'authapp/edit.html', content)


def change_password(request):
    title = 'Изменение пароля'

    if request.method == 'POST':
        form = PasswordChangeForm(user=request.user, data=request.POST)
        if form.is_valid():
            form.save()
            auth.update_session_auth_hash(request, request.user)
            messages.success(request, 'Пароль был успешно обновлен!')
            return render(request, 'auth:change_password')
        else:
            messages.error(request, 'Пожалуйста, исправьте ошибки ниже.')
    else:
        form = PasswordChangeForm(request.user)

    content = {'title': title, 'form': form}

    return render(request, 'authapp/change_password.html', content)