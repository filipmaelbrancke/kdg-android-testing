package be.appfoundry.android.testing.espresso;

import android.support.test.espresso.ViewAction;

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
