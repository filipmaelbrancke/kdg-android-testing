package be.appfoundry.android.testing.espresso;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.webkit.WebView;
import be.appfoundry.android.testing.model.Person;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static org.hamcrest.CoreMatchers.is;

/**
 * @author Filip Maelbrancke
 */
public class CustomMatchers {

    public static Matcher<Object> withPerson(final String fullName) {

        return new BoundedMatcher<Object, Person>(Person.class) {
            @Override
            protected boolean matchesSafely(Person person) {
                return person.getFullName().equalsIgnoreCase(fullName);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with fullname: ");
            }


        };
    }

    public static Matcher<View> withResourceName(String resourceName) {
        return withResourceName(is(resourceName));
    }

    public static Matcher<View> withResourceName(final Matcher<String> resourceNameMatcher) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with resource name: ");
                resourceNameMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                int id = view.getId();
                return id != View.NO_ID && id != 0 && view.getResources() != null
                    && resourceNameMatcher.matches(view.getResources().getResourceName(id));
            }
        };
    }

    public static Matcher<View> loadsUrl(final String url) {

        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                // only applies for a WebView
                if (!(view instanceof WebView)) {
                    return false;
                }
                WebView webView = (WebView) view;
                return webView.getUrl().equalsIgnoreCase(url);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("webview's url");
            }
        };
    }
}
