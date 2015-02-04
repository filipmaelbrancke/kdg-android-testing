package be.appfoundry.android.testing.ui.fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import be.appfoundry.android.testing.R;
import be.appfoundry.android.testing.di.DaggerHelper;
import be.appfoundry.android.testing.model.Person;
import be.appfoundry.android.testing.model.Persons;
import be.appfoundry.android.testing.service.BigBangService;
import be.appfoundry.android.testing.ui.adapter.PersonAdapter;
import java.util.Collections;
import javax.inject.Inject;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A list fragment representing a list of Persons. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link PersonDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link PersonSelectedCallback}
 * interface.
 */
public class PersonListFragment extends ListFragment {

    @Inject
    BigBangService bigBangService;

    private PersonAdapter adapter;

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated person position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private PersonSelectedCallback mPersonSelectedCallback = sDummyPersonSelectedCallback;

    /**
     * The current activated person position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of person
     * selections.
     */
    public interface PersonSelectedCallback {
        /**
         * Callback for when a person has been selected.
         */
        public void onPersonSelected(String id);
    }

    /**
     * A dummy implementation of the {@link PersonSelectedCallback} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static PersonSelectedCallback sDummyPersonSelectedCallback =
        new PersonSelectedCallback() {
            @Override
            public void onPersonSelected(String id) {
            }
        };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PersonListFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof PersonSelectedCallback)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mPersonSelectedCallback = (PersonSelectedCallback) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DaggerHelper.inject(this);

        adapter = new PersonAdapter(getActivity(), Collections.EMPTY_LIST);
        setListAdapter(adapter);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
            && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getBigBangCharacters();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mPersonSelectedCallback = sDummyPersonSelectedCallback;
    }

    private void getBigBangCharacters() {
        bigBangService.getPersons(bigBangCharactersCallback);
    }

    Callback<Persons> bigBangCharactersCallback = new Callback<Persons>() {

        @Override
        public void success(Persons persons, Response response) {

            adapter = new PersonAdapter(getActivity(), persons.getPersons());
            setListAdapter(adapter);
        }

        @Override
        public void failure(RetrofitError error) {
            adapter = new PersonAdapter(getActivity(), Collections.EMPTY_LIST);
            setListAdapter(adapter);
        }
    };

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that a person has been selected.
        if (adapter != null) {
            final Person selectedPerson = adapter.getItem(position);
            mPersonSelectedCallback.onPersonSelected(selectedPerson.getId());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
            ? ListView.CHOICE_MODE_SINGLE
            : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }
}
