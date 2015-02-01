package be.appfoundry.android.testing;

import android.test.ActivityInstrumentationTestCase2;
import be.appfoundry.android.testing.model.Person;
import be.appfoundry.android.testing.ui.activity.PersonListActivity;
import com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions;

import static be.appfoundry.android.testing.espresso.CustomMatchers.loadsUrl;
import static be.appfoundry.android.testing.espresso.CustomMatchers.withPerson;
import static be.appfoundry.android.testing.espresso.CustomMatchers.withResourceName;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDescendantOfA;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;


/**
 * @author Filip Maelbrancke
 */
public class PersonListEspressoTest extends ActivityInstrumentationTestCase2<PersonListActivity> {

    public PersonListEspressoTest() {
        super(PersonListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Espresso will not automatically launch the Activity, we must launch it via getActivity().
        getActivity();
    }

    public void testClickOnHoward() {
        // search for a row and perform action
        onData(is(instanceOf(Person.class))).atPosition(0).perform(click());
    }

    public void testClickOnSheldon() {
        // search for a row and perform action
        onData(hasToString(containsString("Sheldon"))).perform(click());
    }

    public void testClickOnAmy() {
        onData(withPerson("Amy Farrah Fowler"))
            .perform(click());
    }

    public void testClickOnPennyShouldShowPennyDetail() {
        onData(hasToString(containsString("Penny"))).perform(click());

        onView(allOf(isDescendantOfA(withResourceName("android:id/action_bar_container")), withText("Person Detail")));

        onView(withId(R.id.profession)).check(ViewAssertions.matches(withText("Waitress")));
    }

    public void testClickAmyPictureShouldOpenWikipedia() {

        onData(allOf(is(instanceOf(Person.class)), hasToString(containsString("Amy"))))
            .onChildView(withId(R.id.list_item_photo))
            .perform(click());

        // check if the wikipedia page is being loaded in the webview
        onView(withId(R.id.wikipedia_webview))
            .check(ViewAssertions.matches(loadsUrl("https://en.wikipedia.org/wiki/Mayim_Bialik")));
    }


}
