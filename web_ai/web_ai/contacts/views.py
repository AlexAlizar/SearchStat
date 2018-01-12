from django.core.mail import send_mail, BadHeaderError
from django.shortcuts import render, HttpResponse, redirect
from contacts.forms import ContactForm


def contact(request):
    form = ContactForm()

    if request.method == 'POST':
        form = ContactForm(request.POST)

        if form.is_valid():
            subject = form.cleaned_data['subject']
            from_email = form.cleaned_data['email']
            message = form.cleaned_data['message']
            try:
                send_mail(subject, from_email, message, ['test@test.test'])
            except BadHeaderError:
                return HttpResponse('Invalid header error')
            return render(request, 'contacts/success.html')

    return render(request, 'contacts/contact.html', {'form': form})
