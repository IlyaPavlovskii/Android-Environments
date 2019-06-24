package by.bulba.android.environments;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Root application activity.
 * */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextView)findViewById(R.id.mainTvBuildTypeValue)).setText(
                getString(R.string.main_build_type_pattern, BuildConfig.KEY_BUILD_TYPE_VALUE)
        );
        ((TextView)findViewById(R.id.mainTvFlavorValue)).setText(
                getString(R.string.main_flavor_value_pattern, BuildConfig.KEY_FLAVOR_VALUE)
        );
    }
}
