package be.appfoundry.android.testing.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import be.appfoundry.android.testing.R;
import be.appfoundry.android.testing.di.DaggerHelper;
import be.appfoundry.android.testing.model.BigBangCharacter;
import be.appfoundry.android.testing.model.Person;
import be.appfoundry.android.testing.service.BigBangService;
import be.appfoundry.android.testing.ui.activity.PersonDetailActivity;
import be.appfoundry.android.testing.ui.activity.PersonListActivity;
import be.appfoundry.android.testing.util.AppUtils;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A fragment representing a single Person detail screen.
 * This fragment is either contained in a {@link PersonListActivity}
 * in two-pane mode (on tablets) or a {@link PersonDetailActivity}
 * on handsets.
 */
public class PersonDetailFragment extends Fragment {

    @Inject
    BigBangService bigBangService;

    @InjectView(R.id.imageView)
    ImageView image;
    @InjectView(R.id.characterName)
    TextView characterNameTextView;
    @InjectView(R.id.profession)
    TextView professionTextView;
    @InjectView(R.id.realname)
    TextView realNameTextView;
    @InjectView(R.id.personDetail)
    TextView bioTextView;

    /**
     * The fragment argument representing the person ID that this fragment
     * represents.
     */
    public static final String ARG_PERSON_ID = "person_id";

    private String personId;

    /**
     * The person this fragment is presenting.
     */
    private Person mPerson;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PersonDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_PERSON_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            personId = getArguments().getString(ARG_PERSON_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_person_detail, container, false);

        ButterKnife.inject(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DaggerHelper.inject(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (!TextUtils.isEmpty(personId)) {
            bigBangService.getPersonDetail(personId, personDetailCallback);
        }
    }

    Callback<BigBangCharacter> personDetailCallback = new Callback<BigBangCharacter>() {

        @Override
        public void success(BigBangCharacter bigBangCharacter, Response response) {
            final Person person = bigBangCharacter.getPerson();
            final String imagePath = AppUtils.URL + person.getImageUri();
            Picasso.with(getActivity()).load(imagePath).into(image);
            characterNameTextView.setText(person.getFullName());
            professionTextView.setText(person.getProfession());
            realNameTextView.setText(person.getRealName());
            bioTextView.setText(person.getBio());
        }

        @Override
        public void failure(RetrofitError error) {

        }
    };
}
