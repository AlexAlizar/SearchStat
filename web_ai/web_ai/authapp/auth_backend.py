from admin_interface.models import Users
from django.contrib.auth.backends import ModelBackend, get_user_model
from django.contrib.auth.hashers import BasePasswordHasher

UserModel = get_user_model()


class AuthBackend(ModelBackend):

    def authenticate(self, request, username=None, password=None, **kwargs):
        try:
            user = Users.objects.get(login=username)
            if password == user.password:
                return user
        except Users.DoesNotExist:
            return None

    def get_user(self, user_id):
        try:
            user = Users.objects.get(pk=user_id)
            return user
        except Users.DoesNotExist:
            return None


class NotPasswordEncryption(BasePasswordHasher):

    algorithm = 'None'
    library = 'None'

    def verify(self, *args):
        return args[0]

    def encode(self, *args):
        return args[0]

    def safe_summary(self, *args):
        return args[0]