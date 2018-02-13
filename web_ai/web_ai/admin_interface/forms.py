from django import forms
from django.contrib.auth.models import User
from django.contrib.auth.forms import UserChangeForm

from .models import Sites, Persons, Keywords, Users
from datetime import datetime

from authapp.forms import EditForm


class SitesManageForm(forms.ModelForm):

    class Meta:
        model = Sites
        fields = '__all__'

    name = forms.CharField(required=False, widget=forms.URLInput(attrs={'id': 'url', 'placeholder': 'http://site.com'}))


class PersonsManageForm(forms.ModelForm):

    class Meta:
        model = Persons
        fields = '__all__'

    name = forms.CharField(required=True, widget=forms.TextInput(attrs={'id': 'person'}))


class KeywordsForm(forms.Form):

    class Meta:
        model = Keywords
        fields = '__all__'

    dropdown = forms.ModelChoiceField(queryset=None, required=False)
    multiple_select = forms.ModelMultipleChoiceField(queryset=None, required=False)

    def __init__(self, *args, **kwargs):
        super(KeywordsForm, self).__init__(*args, **kwargs)
        self.fields['dropdown'] = forms.ModelChoiceField(
            queryset=Persons.objects.all()
        )


class KeywordsAddForm(forms.Form):
    class Meta:
        model = Keywords
        fields = '__all__'

    keywords_add = forms.CharField(widget=forms.TextInput(attrs={'placeholder': 'keyword1, keyword2, etc.'}),
                                   required=False)


class UserAdminEditForm(UserChangeForm):
    class Meta:
        model = Users
        exclude = ('last_login_date', 'last_login', 'username', 'token')


class AdminPasswordChangeForm(forms.Form):

    error_messages = {
        'password_mismatch': "The two password fields didn't match.",
    }
    password1 = forms.CharField(label="Пароль",
                                widget=forms.PasswordInput)
    password2 = forms.CharField(label="Повторите пароль",
                                widget=forms.PasswordInput)

    def __init__(self, user, *args, **kwargs):
        self.user = user
        super(AdminPasswordChangeForm, self).__init__(*args, **kwargs)

    def clean_password2(self):
        password1 = self.cleaned_data.get('password1')
        password2 = self.cleaned_data.get('password2')
        if password1 and password2:
            if password1 != password2:
                raise forms.ValidationError(
                    self.error_messages['password_mismatch'])
        return password2

    def save(self, commit=True):

        self.user.set_password(self.cleaned_data["password1"])
        if commit:
            self.user.save()
        return self.user


class ChoiceSiteForm(forms.Form):
    site_name = forms.ModelChoiceField(queryset=None, required=True)

    def __init__(self, *args, **kwargs):
        super(ChoiceSiteForm, self).__init__(*args, **kwargs)
        self.fields['site_name'] = forms.ModelChoiceField(queryset=Sites.objects.all())
