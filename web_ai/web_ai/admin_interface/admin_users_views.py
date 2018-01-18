from django.contrib.auth.models import User
from django.contrib.auth.decorators import user_passes_test
from django.shortcuts import render, get_object_or_404, HttpResponseRedirect
from django.urls import reverse

from authapp.forms import EditForm, RegisterForm
from admin_interface.forms import UserAdminEditForm
from admin_interface.models import Users


@user_passes_test(lambda user: user.role == 'admin', login_url='/auth/login')
def users(request):
    title = 'ai/users'
    users_list = Users.objects.all().order_by('role', 'username')
    content = {
        'title': title,
        'objects': users_list,
    }
    return render(request, 'authapp/users.html', content)


@user_passes_test(lambda user: user.role == 'admin', login_url='/auth/login')
def user_create(request):
    title = 'users/create'

    if request.method == 'POST':
        user_form = RegisterForm(request.POST)
        if user_form.is_valid():
            user_form.save()
            return HttpResponseRedirect(reverse('ai:users'))
    else:
        user_form = RegisterForm()

    content = {
        'title': title,
        'update_form': user_form
    }

    return render(request, 'authapp/user_update.html', content)


def user_update(request, pk):
    title = 'users/edit'
    edit_user = get_object_or_404(Users, pk=pk)

    if request.method == 'POST':
        edit_form = UserAdminEditForm(request.POST, instance=edit_user)
        if edit_form.is_valid():
            edit_form.save()
            # return HttpResponseRedirect(reverse('ai:user_update', args=[edit_user.pk]))
            return HttpResponseRedirect(reverse('ai:users'))
        else:
            edit_form = UserAdminEditForm(instance=edit_user)
            return render(request, 'authapp/user_update.html', {'edit_form': edit_form})
    else:
        edit_form = UserAdminEditForm(instance=edit_user)

    content = {'title': title, 'update_form': edit_form}

    return render(request, 'authapp/user_update.html', content)


@user_passes_test(lambda user: user.role == 'admin', login_url='/auth/login')
def user_delete(request, pk):
    title = 'users/delete'

    user = get_object_or_404(Users, pk=pk)

    if request.method == 'POST':
        user.delete()
        return HttpResponseRedirect(reverse('ai:users'))

    content = {'title': title, 'user_to_delete': user}

    return render(request, 'authapp/user_delete.html', content)