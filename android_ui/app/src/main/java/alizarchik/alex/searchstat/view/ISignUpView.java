package alizarchik.alex.searchstat.view;

/**
 * Created by Olesia on 21.04.2018.
 */

public interface ISignUpView {
    void hideLoadingIndicator();
    void showNoConnectionToTheServer();
    void showNoConnectionMessage();
    void showErrorMessage();
    void startLoginActivity();
}
