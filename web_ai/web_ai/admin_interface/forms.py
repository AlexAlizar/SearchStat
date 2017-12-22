from django import forms
from .models import ModelSite, ModelPerson


class SitesManageForm(forms.ModelForm):

    class Meta:
        model = ModelSite
        fields = '__all__'

    multiple_select = forms.ModelMultipleChoiceField(queryset=ModelSite.objects.all(), required=False)
    name = forms.CharField(required=False, widget=forms.URLInput(attrs={'id': 'url', 'value': 'http://'}))


class PersonsManageForm(forms.ModelForm):

    class Meta:
        model = ModelPerson
        fields = '__all__'

    multiple_select = forms.ModelMultipleChoiceField(queryset=ModelPerson.objects.all(), required=False)
    name = forms.CharField(required=False, widget=forms.TextInput(attrs={'id': 'person'}))