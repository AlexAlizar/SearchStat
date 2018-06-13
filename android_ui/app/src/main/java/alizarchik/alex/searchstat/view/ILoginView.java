package alizarchik.alex.searchstat.view;

/**
 * Created by Olesia on 11.04.2018.
 */

public interface ILoginView {
    void hideLoadingIndicator();
    void showNoConnectionToTheServer();
    void showNoConnectionMessage();
    void showNeedRegisterMessage();
    void saveToken(String token);
}
