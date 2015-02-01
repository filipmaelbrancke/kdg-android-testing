package be.appfoundry.android.testing.di;

import android.os.AsyncTask;
import be.appfoundry.android.testing.service.BigBangService;
import be.appfoundry.android.testing.ui.fragment.PersonDetailFragment;
import be.appfoundry.android.testing.ui.fragment.PersonListFragment;
import be.appfoundry.android.testing.util.AppUtils;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import retrofit.RestAdapter;
import retrofit.android.MainThreadExecutor;

/**
 * @author Filip Maelbrancke
 */
@Module(injects = {
    PersonListFragment.class,
    PersonDetailFragment.class
})
public class BigBangServiceModule {

    @Singleton
    @Provides
    public BigBangService provideBigBangService() {
        return new RestAdapter.Builder()
            .setEndpoint(AppUtils.URL)
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setExecutors(AsyncTask.THREAD_POOL_EXECUTOR, new MainThreadExecutor())
            .build()
            .create(BigBangService.class);
    }
}
