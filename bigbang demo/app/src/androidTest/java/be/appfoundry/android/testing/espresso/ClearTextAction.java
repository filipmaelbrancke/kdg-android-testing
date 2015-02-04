package be.appfoundry.android.testing.espresso;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;
import android.widget.EditText;
import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Clears view text by setting {@link EditText}s text property to "".
 */
public class ClearTextAction implements ViewAction {

    @Override
    public Matcher<View> getConstraints() {
        return allOf(isDisplayed(), isAssignableFrom(EditText.class));
    }

    @Override
    public void perform(UiController uiController, View view) {
        ((EditText) view).setText("");
    }

    @Override
    public String getDescription() {
        return "Clear the EditText component";
    }
}
