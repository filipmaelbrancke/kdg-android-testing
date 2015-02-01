package be.appfoundry.android.testing.espresso;

import android.support.test.espresso.IdlingResource;

public class IdleMonitor implements IdlingResource {

    @Override
    public String getName() {
        return IdleMonitor.class.getSimpleName();
    }

    @Override
    public boolean isIdleNow() {
        // return true if resource is idle
        return false;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        // store a reference to the resourceCallback
        // notify resourceCallback when idle
    }
}
