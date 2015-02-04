package be.appfoundry.android.testing.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import be.appfoundry.android.testing.R;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Filip Maelbrancke
 */
public class WikipediaActivity extends Activity {

    public static final String WIKIPEDIA_URL = "wikipediaUrl";
    public static final String WIKIPEDIA_TITLE = "wikipediaTitle";

    @InjectView(R.id.wikipedia_webview)
    WebView wikipediaWebview;

    @InjectView(R.id.wikipedia_title)
    TextView wikipediaTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wikipedia);
        setProgressBarIndeterminate(true);

        ButterKnife.inject(this);
        final String url = getIntent().getStringExtra(WIKIPEDIA_URL);
        final String title = getIntent().getStringExtra(WIKIPEDIA_TITLE);
        getActionBar().setTitle(getString(R.string.wikipedia));
        wikipediaTitle.setText(title);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        loadWikipedia(url);
    }

    private void loadWikipedia(final String url) {
        setProgressBarIndeterminateVisibility(true);
        wikipediaWebview.setWebViewClient(wikipediaWebViewClient);
        wikipediaWebview.loadUrl(url);
    }

    WebViewClient wikipediaWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            setProgressBarIndeterminateVisibility(false);
            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
