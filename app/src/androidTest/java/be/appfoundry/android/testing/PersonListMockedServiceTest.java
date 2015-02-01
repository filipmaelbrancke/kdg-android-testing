package be.appfoundry.android.testing;

import android.test.ActivityInstrumentationTestCase2;
import be.appfoundry.android.testing.di.DaggerHelper;
import be.appfoundry.android.testing.model.Person;
import be.appfoundry.android.testing.service.BigBangService;
import be.appfoundry.android.testing.service.MockBigBangService;
import be.appfoundry.android.testing.ui.activity.PersonListActivity;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * @author Filip Maelbrancke
 */
public class PersonListMockedServiceTest extends
    ActivityInstrumentationTestCase2<PersonListActivity> {

    public PersonListMockedServiceTest() {
        super(PersonListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Espresso will not automatically launch the Activity, we must launch it via getActivity().
        getActivity();
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
    public class MockedBigBangServiceModule {

        @Singleton
        @Provides
        public BigBangService provideBigBangService() {
            return new MockBigBangService(getActivity());
        }
    }

    public void testClickOnHoward() {
        MockedBigBangServiceModule mockedBigBangServiceModule = new MockedBigBangServiceModule();
        DaggerHelper.initWithTestModules(mockedBigBangServiceModule);


        // search for a row and perform action
        onData(is(instanceOf(Person.class))).atPosition(0).perform(click());
    }
}
