package be.appfoundry.android.testing.espresso;

import com.google.android.apps.common.testing.ui.espresso.ViewAction;

/**
 * Your own custom View Actions.
 */
public class CustomViewActions {

    private CustomViewActions() {
    }

    /**
     * Returns an action that clears text on the view. Extra thoroughly!
     */
    public static ViewAction clearTextExtraThoroughly() {
        return new ClearTextAction();
    }
}
