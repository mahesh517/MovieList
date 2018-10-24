package app.com.movielist.BaseClass;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import app.com.movielist.ApiClass.APIServiceFactory;
import app.com.movielist.ApiClass.ApiService;
import app.com.movielist.CustomViews.Loader;
import app.com.movielist.R;

public class BaseActivity extends AppCompatActivity {

    public ApiService apiService;

    public static String API_KEY = "5bb32eaa19a96bcf373d32f08f702aef";

    public Loader loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        apiService = APIServiceFactory.getRetrofit().create(ApiService.class);

        loader = new Loader(BaseActivity.this);


    }

    public void show_progress() {
        if (loader != null) {
            loader.show();
        }
    }

    public void dismiss_progress() {
        if (loader != null) {
            loader.dismiss();
        }
    }

    public void show_Toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
