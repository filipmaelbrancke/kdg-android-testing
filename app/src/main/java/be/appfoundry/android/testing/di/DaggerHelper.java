package be.appfoundry.android.testing.di;

import dagger.ObjectGraph;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * See Dorian Cussen - http://systemdotrun.blogspot.co.uk/
 */
public class DaggerHelper {

    private static ObjectGraph objectGraph;

    private static final List<Object> productionModules;

    static {
        productionModules = new ArrayList<Object>();
        productionModules.add(new BigBangServiceModule());
    }

    public synchronized static void initProductionModules() {
        initWithModules(productionModules);
    }

    public synchronized static void initWithTestModules(Object... testModules) {
        initWithModules(getModulesAsList(testModules));
    }

    /**
     * Will create a new object graph and therefore reset any previous modules set
     */
    private synchronized static void initWithModules(List<Object> modules) {
        objectGraph = ObjectGraph.create(modules.toArray());
    }

    private synchronized static List<Object> getModulesAsList(Object... extraModules) {
        List<Object> allModules = new ArrayList<Object>();
        allModules.addAll(productionModules);
        allModules.addAll(Arrays.asList(extraModules));
        return allModules;
    }

    /**
     * Dagger convenience method - will inject the fields of the passed in object
     */
    public synchronized static void inject(Object object) {
        objectGraph.inject(object);
    }
}
