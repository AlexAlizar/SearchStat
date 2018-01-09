package alizarchik.alex.searchstat;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * Created by Александр on 07.01.2018.
 */

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    // Убедитесь, что используется версия
    // android.support.v7.app.ActionBarDrawerToggle.

    // android.support.v4.app.ActionBarDrawerToggle устарел.

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Установить Toolbar для замены ActionBar'а.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Найти наш view drawer'а
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        // Найти наш view drawer'а
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Настроить view drawer'а
        setupDrawerContent(nvDrawer);

        mDrawer.addDrawerListener(drawerToggle);

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // Примечание: Убедитесь, что вы передаёте допустимую ссылку
        // на toolbar
        // ActionBarDrawToggle() не предусматривает в ней
        // необходимости и не будет отображать иконку гамбургера без
        // неё
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open,  R.string.navigation_drawer_close);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Синхронизировать состояние переключения после того, как
        // возникнет onRestoreInstanceState
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Передать любые изменения конфигурации переключателям
        // drawer'а
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem(menuItem);
                    return true;
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Создать новый фрагмент и задать фрагмент для отображения
        // на основе нажатия на элемент навигации
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.general_stat:
                fragmentClass = GeneralStatFragment.class;
                break;
            case R.id.detailed_stat:
                fragmentClass = SitesFragment.class;
                break;
            case R.id.account:
                fragmentClass = GeneralStatFragment.class;
                break;
            case R.id.settings:
                fragmentClass = GeneralStatFragment.class;
                break;
            case R.id.exit:
                fragmentClass = GeneralStatFragment.class;
                break;
            default:
                fragmentClass = GeneralStatFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Вставить фрагмент, заменяя любой существующий
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

        // Выделение существующего элемента выполнено с помощью
        // NavigationView
        menuItem.setChecked(true);
        // Установить заголовок для action bar'а
        setTitle(menuItem.getTitle());
        // Закрыть navigation drawer
        mDrawer.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

/*public void onClick() {
        mInfoTextView.setText("");
        // для логирования ретрофита
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = null;
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://195.110.59.16:8081/rest-api-servlet/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            restAPI = retrofit.create(IRestApi.class);
        } catch (Exception io) {
            mInfoTextView.setText("no retrofit: " + io.getMessage());
            Log.e(TAG, "no retrofit: " + io.getMessage());
            return;
        }
        // подготовили вызов на сервер
        Call<GeneralStatisticsModel> call = restAPI.loadSite();
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected()) {
            // запускаем
            try {
                progressBar.setVisibility(View.VISIBLE);
                downloadOneUrl(call);
            } catch (IOException e) {
                e.printStackTrace();
                mInfoTextView.setText(e.getMessage());
                Log.e(TAG, e.getMessage());
            }
        } else {
            Toast.makeText(MainActivity.this, "Подключите интернет", Toast.LENGTH_SHORT).show();
        }
    }

    private void downloadOneUrl(Call<GeneralStatisticsModel> call) throws IOException {
        call.enqueue(new Callback<GeneralStatisticsModel>() {
            @Override
            public void onResponse(Call<GeneralStatisticsModel> call, Response<GeneralStatisticsModel> response) {
                if (response.isSuccessful()) {
                    if (response != null) {
                        GeneralStatisticsModel curGeneralModel = null;
                        curGeneralModel = response.body();
                        mInfoTextView.append("\nSites = " + curGeneralModel.getSites() +
                                "\n-----------------");
                    }
                } else {
                    Log.e(TAG, "onResponse error: " + response.code());
                    mInfoTextView.setText("onResponse error: " + response.code());
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GeneralStatisticsModel> call, Throwable t) {
                Log.e(TAG, "onFailure ", t);
                mInfoTextView.setText("onFailure " + t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }*/


