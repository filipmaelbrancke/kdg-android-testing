package be.appfoundry.android.testing.espresso;

import android.view.View;
import android.widget.EditText;
import com.google.android.apps.common.testing.ui.espresso.UiController;
import com.google.android.apps.common.testing.ui.espresso.ViewAction;
import org.hamcrest.Matcher;

import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isAssignableFrom;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
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
