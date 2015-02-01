package be.appfoundry.android.testing.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import be.appfoundry.android.testing.R;
import be.appfoundry.android.testing.model.Person;
import be.appfoundry.android.testing.util.AppUtils;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Adapter between the BigBang characters and a listview.
 */
public class PersonAdapter extends ArrayAdapter<Person> {

    public PersonAdapter(Context context, List<Person> persons) {
        super(context, R.layout.list_item, persons);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Person person = getItem(position);

        holder.label.setText(person.getFullName());

        Picasso.with(getContext()).load(AppUtils.URL + person.getImageUri()).into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.launchWikipedia(getContext(), person.getWikipediaUrl(), person.getFullName());
            }
        });

        return convertView;
    }

     class ViewHolder {
        @InjectView(R.id.list_item_photo)
        ImageView image;

        @InjectView(R.id.list_item_label)
        TextView label;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
