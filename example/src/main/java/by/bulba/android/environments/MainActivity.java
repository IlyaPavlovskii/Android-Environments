/*
 * Copyright (C) 2019. Ilya Pavlovskii
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
