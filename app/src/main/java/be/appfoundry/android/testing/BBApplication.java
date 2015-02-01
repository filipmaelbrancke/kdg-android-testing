package be.appfoundry.android.testing;

import android.app.Application;
import be.appfoundry.android.testing.di.DaggerHelper;

/**
 * @author Filip Maelbrancke
 */
public class BBApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerHelper.initProductionModules();
    }
}
