package be.appfoundry.android.testing;

import android.os.AsyncTask;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import be.appfoundry.android.testing.di.DaggerHelper;
import be.appfoundry.android.testing.service.BigBangService;
import be.appfoundry.android.testing.ui.activity.PersonListActivity;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import dagger.Module;
import dagger.Provides;
import java.io.IOException;
import javax.inject.Singleton;
import retrofit.RestAdapter;
import retrofit.android.MainThreadExecutor;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;

/**
 * @author Filip Maelbrancke
 */
public class MockWebServerTest extends ActivityInstrumentationTestCase2<PersonListActivity> {

    public MockWebServerTest() {
        super(PersonListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        DaggerHelper.initProductionModules();
    }

    @Module(
        overrides = true,
        library = true
    )
    public class MockWebServerBigBangServiceModule {

        private MockWebServer mockWebServer;

        public MockWebServerBigBangServiceModule() {
            mockWebServer = new MockWebServer();
            try {
                mockWebServer.play();
            } catch (IOException e) {
                Log.e("MOCKWEBSERVER", e.getMessage());
            }
        }

        public MockWebServer getMockWebServer() {
            return mockWebServer;
        }

        @Provides
        public MockWebServer provideMockWebServer() {
            return mockWebServer;
        }

        @Singleton
        @Provides
        public BigBangService provideBigBangService(MockWebServer mockWebServer) {
            return new RestAdapter.Builder()
                .setEndpoint(getMockWebServer().getUrl("/").toString())
                .setExecutors(AsyncTask.THREAD_POOL_EXECUTOR, new MainThreadExecutor())
                .build()
                .create(BigBangService.class);
        }
    }

    public void testServerErrorShouldShowEmptyState() {
        MockWebServerBigBangServiceModule mockWebServerServiceModule = new MockWebServerBigBangServiceModule();
        DaggerHelper.initWithTestModules(mockWebServerServiceModule);

        // Setup the MockWebServer
        MockResponse mockResponse = new MockResponse();
        mockResponse.setResponseCode(500);
        mockWebServerServiceModule.getMockWebServer().enqueue(mockResponse);

        // mockwebserver + restadapter are setup, now init activity
        getActivity();

        onView(withId(R.id.list_empty_image)).check(matches(isDisplayed()));
    }

}
