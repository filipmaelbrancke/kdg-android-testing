package be.appfoundry.android.testing;

import android.support.test.espresso.matcher.ViewMatchers;
import be.appfoundry.android.testing.hamcrest.IsNotANumber;
import junit.framework.TestCase;
import org.hamcrest.CoreMatchers;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;

/**
 * @author Filip Maelbrancke
 */
public class HamcrestTests extends TestCase {

    public void testSquareRootOfMinusOneIsNotANumber() {
        assertThat(Math.sqrt(-1), CoreMatchers.is(IsNotANumber.notANumber()));
    }
}
