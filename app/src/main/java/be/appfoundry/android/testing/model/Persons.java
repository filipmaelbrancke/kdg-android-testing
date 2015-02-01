package be.appfoundry.android.testing.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filip Maelbrancke
 */
public class Persons {

    @SerializedName("personList")
    private List<Person> persons = new ArrayList<Person>();

    public List<Person> getPersons() {
        return persons;
    }
}
