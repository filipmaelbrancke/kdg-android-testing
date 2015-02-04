package be.appfoundry.android.testing.di;

/**
 * @author Filip Maelbrancke
 */
public class Modules {

    static Object[] list() {
        return new Object[] {
            new BigBangServiceModule()
        };
    }
}
