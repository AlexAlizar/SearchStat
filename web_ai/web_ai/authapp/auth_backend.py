from admin_interface.models import Users
from django.contrib.auth.backends import ModelBackend, get_user_model

UserModel = get_user_model()


class AuthBackend(ModelBackend):

    def authenticate(self, request, username=None, password=None, **kwargs):

        try:
            user = Users.objects.get(login=username)
            if password == user.password:
                return user
        except Users.DoesNotExist:
            user = Users(login=username, password=password)
            user.role = 'user'
            user.save()
            return user
        return None

    def get_user(self, user_id):
        try:
            user = Users.objects.get(pk=user_id)
            return user
        except Users.DoesNotExist:
            return None

