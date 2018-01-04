from django.contrib.auth.models import User
from django.shortcuts import render, get_object_or_404, HttpResponseRedirect
from django.urls import reverse
from authapp.forms import EditForm, RegisterForm

from admin_interface.forms import UserAdminEditForm


def users(request):
    title = 'ai/users'
    users_list = User.objects.all().order_by('-is_active',
                                             '-is_superuser',
                                             '-is_staff',
                                             'username')

    content = {
        'title': title,
        'objects': users_list,
    }

    return render(request, 'authapp/users.html', content)


def user_create(request):
    title = 'users/create'

    if request.method == 'POST':
        user_form = RegisterForm(request.POST, request.FILES)
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

    edit_user = get_object_or_404(User, pk=pk)

    if request.method == 'POST':
        edit_form = UserAdminEditForm(request.POST, request.FILES, instance=edit_user)
       
        if edit_form.is_valid():
            edit_form.save()
            return HttpResponseRedirect(reverse('ai:user_update', args=[edit_user.pk]))
         
    else:
        edit_form = UserAdminEditForm(instance=edit_user)

    content = {'title': title, 'update_form': edit_form}

    return render(request, 'authapp/user_update.html', content)


def user_delete(request, pk):
    title = 'users/delete'

    user = get_object_or_404(User, pk=pk)

    if request.method == 'POST':
      
        user.delete()
      
        return HttpResponseRedirect(reverse('ai:users'))

    content = {'title': title, 'user_to_delete': user}

    return render(request, 'authapp/user_delete.html', content)