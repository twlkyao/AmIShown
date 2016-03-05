package twlkyao.com.myviewshow;

import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView1, textView2, textView3, textView4;

    private List<TextView> textViewList = new ArrayList<>();

    private ScrollView scrollView;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        scrollView = (ScrollView) findViewById(R.id.scrollView);

        textView1 = (TextView) findViewById(R.id.textview1);
        textViewList.add(textView1);
        textView1.setOnClickListener(this);

        textView2 = (TextView) findViewById(R.id.textview2);
        textViewList.add(textView2);
        textView2.setOnClickListener(this);

        textView3 = (TextView) findViewById(R.id.textview3);
        textViewList.add(textView3);
        textView3.setOnClickListener(this);

        textView4 = (TextView) findViewById(R.id.textview4);
        textViewList.add(textView4);
        textView4.setOnClickListener(this);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://twlkyao.com.myviewshow/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://twlkyao.com.myviewshow/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    /**
     * if the view and its ancestors are set the visiablity to visible, then return ture.
     *
     * @param textview
     */
    private void checkIsShown(TextView textview) {
        if (textview.isShown()) {
            Toast.makeText(this, textview.getText().toString() + " is shown", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, textview.getText().toString() + " is not shown", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkIsConfined(ScrollView scrollView, TextView textview) {
        fullDrawConfined(scrollView, textview); // the most accurate method, it calculate the bounds visible on the screen.
        partConfined(scrollView, textview); // if the view is visible, then hit the target.
        fullHitConfined(scrollView, textview);
    }


    @Override
    public void onClick(View v) {
        for (TextView textview : textViewList) {
//            checkIsShown(textview);
            checkIsConfined(scrollView, textview);

        }
    }

    /**
     * if the view is fully displayed in its parent, then return true.
     *
     * @param scrollView
     * @param textview
     */
    private void fullDrawConfined(ScrollView scrollView, TextView textview) {
        Rect scrollBounds = new Rect();
        scrollView.getDrawingRect(scrollBounds); // the coordinates are relative to the view itself.
        Log.d("steveqi", "fullDrawConfined getDrawingRect scrollBounds.top=" + scrollBounds.top + " scrollBounds.bottom=" + scrollBounds.bottom);
        float top = textview.getY(); // the y coordinate of the view relative to its parent.
        float bottom = top + textview.getHeight();
        Log.d("steveqi", "fullHitConfined" + textview.getText() + " top=" + top + " bottom=" + bottom);
        if (scrollBounds.top < top && scrollBounds.bottom > bottom) {
            Toast.makeText(this, textview.getText().toString() + " is confined", Toast.LENGTH_SHORT).show();
            Log.d("steveqi", "fullDrawConfined " + textview.getText().toString() + " is confined");
        } else {
            Toast.makeText(this, textview.getText().toString() + " is not confined", Toast.LENGTH_SHORT).show();
            Log.d("steveqi", "fullDrawConfined " + textview.getText().toString() + " is not confined");
        }
    }

    /**
     * if the view is partly displayed in its parent, then return true.
     *
     * @param scrollView
     * @param textview
     */
    private void partConfined(ScrollView scrollView, TextView textview) {
        Rect scrollBounds = new Rect();
        scrollView.getDrawingRect(scrollBounds); //Return the visible drawing bounds of your view.
        Log.d("steveqi", "partConfined getDrawingRect scrollBounds.top=" + scrollBounds.top + " scrollBounds.bottom=" + scrollBounds.bottom);
        if (textview.getLocalVisibleRect(scrollBounds)) {
            Toast.makeText(this, textview.getText().toString() + " is confined", Toast.LENGTH_SHORT).show();
            Log.d("steveqi", "partConfined " + textview.getText().toString() + " is confined");
        } else {
            Toast.makeText(this, textview.getText().toString() + " is not confined", Toast.LENGTH_SHORT).show();
            Log.d("steveqi", "partConfined " + textview.getText().toString() + " is not confined");
        }
    }

    /**
     * use getHitRect to judge whether the view is fully displayed in its parent, then return true.
     * the return value is not always reliable.
     *
     * @param scrollView
     * @param textview
     */
    private void fullHitConfined(ScrollView scrollView, TextView textview) {
        Rect scrollBounds = new Rect();
        scrollView.getHitRect(scrollBounds); //获得和父View的重叠矩形，并用父View的坐标系展示，如果用来判断是否显示，最好用于没有父View的View。Hit rectangle in parent's coordinates. The the cross rectangle by the view and the screen, and the coordinates are relative to the screen.
        Log.d("steveqi", "fullHitConfined getHitRect scrollBounds.top=" + scrollBounds.top + " scrollBounds.bottom=" + scrollBounds.bottom);
        float top = textview.getY();
        float bottom = top + textview.getHeight();
        Log.d("steveqi", "fullHitConfined" + textview.getText() + " top=" + top + " bottom=" + bottom);
        if (scrollBounds.top < top && scrollBounds.bottom > bottom) {
            Toast.makeText(this, textview.getText().toString() + " is confined", Toast.LENGTH_SHORT).show();
            Log.d("steveqi", "fullHitConfined " + textview.getText().toString() + " is confined");
        } else {
            Toast.makeText(this, textview.getText().toString() + " is not confined", Toast.LENGTH_SHORT).show();
            Log.d("steveqi", "fullHitConfined " + textview.getText().toString() + " is not confined");
        }
    }
}
