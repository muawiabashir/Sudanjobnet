package onlinemarketing.net.sudanjobnet.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import onlinemarketing.net.sudanjobnet.Model.Go_event_Items;
import onlinemarketing.net.sudanjobnet.R;
import onlinemarketing.net.sudanjobnet.util.Util;


/**
 * Created by muawia.ibrahim on 1/20/2016.
 */
public class Fragment_go_event_Details extends AppCompatActivity {

    String URL = "http://learnpage.net/appgoeventdet.php?pid=";
    String URL_det = "http://www.learnpage.net/event.php?id=";
    //SqlHandler db;
    Context mContext;
    Go_event_Items go_event_items = new Go_event_Items();
    private TextView title;
    private TextView company_name;
    private TextView closing;
    private TextView city;
    private TextView footer,length1,duration1;
    private TextView pid;
    private ImageView clogo;
    private ImageLoader imageLoader;
    private ArrayList<Go_event_Items> jobItemsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.go_event_details);
      //  db = new SqlHandler(this);

        LinearLayout linlaHeaderProgress = findViewById(R.id.linlaHeaderProgress);

        Toolbar M_toolbar = (Toolbar) findViewById(R.id.toolbargoevent);
        setSupportActionBar(M_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Go Events Detail");
        pid = (TextView) findViewById(R.id.pid16);
        title = findViewById(R.id.title16);
        company_name = findViewById(R.id.company_name16);
        closing = findViewById(R.id.closing16);
       // city = findViewById(R.id.city);
        footer = findViewById(R.id.footer16);
        clogo = findViewById(R.id.clogo16);
        length1= findViewById(R.id.length16);
        duration1= findViewById(R.id.duration16);
       // db = new SqlHandler(this);
        Button apply = findViewById(R.id.apply_go_event);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Go_event_Items jobItem = new Go_event_Items();
                jobItem = (Go_event_Items) getIntent().getSerializableExtra("item");
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(URL_det + jobItem.getPid()));
                startActivity(i);
            }
        });

        if (Util.checknetwork(this)) {
            getData();


        } else {
            Util
                    .displayDialog(
                            getString(R.string.app_name),
                            getString(R.string.check_connection),
                            Fragment_go_event_Details.this, false);
            //  JobItems jobItem = new JobItems();
            //jobItem = (JobItems) getIntent().getSerializableExtra("item");
           // ArrayList<JobItems> jobdb = db.getJobDetails(jobItem.getPid());
         //   Cursor c =db.getJobDetails(jobItem.getPid());


//              title.setText(jobdb.get(3).getTitle());
            //  company_name.setText(jobdb.get(jobdb1).getCompany_name());
         //   city.setText(jobdb.get(6).getCity());
         //   closing.setText(jobdb.get(5).getClosing());
         //   footer.setText(jobdb.get(7).getFooter());
            //  title.setText(jobItem1.getTitle());
        }

        Button share = (Button) findViewById(R.id.share__go_event);
        share.setOnClickListener(new View.OnClickListener() {
            Go_event_Items
                    jobItem = (Go_event_Items) getIntent().getSerializableExtra("item");
            Uri imageUri = Uri.parse(jobItem.getClogo());

            @Override
            public void onClick(View v) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                // Add data to the intent, the receiving app will decide
                // what to do with it.
                share.putExtra(Intent.EXTRA_SUBJECT, "Event Title: " + jobItem.getTitle());
                share.putExtra(Intent.EXTRA_TEXT, "\n\n Hello....\n\n Click the link below for more information  \n\n" + URL_det + jobItem.getPid());
                share.putExtra(Intent.EXTRA_STREAM, imageUri);

                startActivity(Intent.createChooser(share, "Share job with friends !"));
            }
        });

    }

    private void getData() {

        go_event_items = (Go_event_Items) getIntent().getSerializableExtra("item");
        // Intent i = getIntent();
        //   pid =i.getExtras(Fragment_Job_List)
        //Showing a progress dialog
        //     final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, false);
        final ProgressDialog loading = ProgressDialog.show(Fragment_go_event_Details.this, "Loading Data", "Please wait...", false, false);
        //Creating a json array request
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(URL + go_event_items.getPid(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonarray = response.getJSONArray("goevent");

                            loading.dismiss();
                            for (int i = 0; i < jsonarray.length(); i++) {
                                Go_event_Items go_event_items = new Go_event_Items();
                                JSONObject json = null;

                                try {


                                    json = jsonarray.getJSONObject(i);

                                    String pid = json.getString("pid_goevent");
                                    go_event_items.setPid(pid);
                                    String footer1 = json.getString("footer_goevent");
                                    go_event_items.setPid(footer1);
                                    String titleStr = json.getString("title_goevent");
                                    go_event_items.setTitle(titleStr);
                                    String company_name1 = json.getString("company_name_goevent");
                                    go_event_items.setCompany_name(company_name1);
                                    String city1 = json.getString("city_goevent");
                                    go_event_items.setCity(city1);
                                    String closing1 = json.getString("closing_goevent");
                                    go_event_items.setClosing(closing1);
                                    String footer2 = json.getString("footer_goevent");
                                    go_event_items.setFooter(footer2);
                                    String length = json.getString("length_goevent");
                                    go_event_items.setClosing(length);
                                    String duration = json.getString("duration_goevent");
                                    go_event_items.setClosing(duration);

                                   // String footer2 = json.getString("footer");

                                  //  jobitems.setFooter(footer2);
                                    // title.setText(jobitems.getTitle());
                                    // company_name.setText(jobitems.getCompany_name());
                                    title.setText(Html.fromHtml(go_event_items.getTitle()));
                                    company_name.setText(Html.fromHtml(go_event_items.getCompany_name()));
//                                   city.setText(Html.fromHtml(go_event_items.getCity()));
                                    closing.setText(go_event_items.getClosing());
                                    footer.setText(Html.fromHtml(go_event_items.getFooter()));
//                                   length1.setText(Html.fromHtml(go_event_items.getLength()));
//                                   duration1.setText(Html.fromHtml(go_event_items.getDuration()));
                                    //                imageLoader = CustomVolleyRequest.getInstance(Fragment_go_event_Details.this).getImageLoader();

                                    //                imageLoader.get(go_event_items.getClogo(), ImageLoader.getImageListener(clogo, R.mipmap.no_image, R.mipmap.no_image));
                                   // imageLoader.get(learningItems.getClogo(), ImageLoader.getImageListener(clogo, R.drawable.ic_launcher, android.R.drawable.ic_dialog_alert));
                                 //   db.FillDetails(pid, titleStr, company_name1, closing1, city1, footer2);
//                                  ((PiwikApplication)getApplication()).getTracker();
//                                 //  .trackScreenView("Learn Details",pid +" "+ titleStr +" "+company_name1);
//                                    Tracker tracker = ((PiwikApplication) getApplication()).getTracker();
//                                    TrackHelper.track().screen("Learn Details").title("Learn Details").with(tracker);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                jobItemsArrayList.add(go_event_items);

                            }
                            //calling method to parse json array
                            //  parseData(jsonarray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {


        };

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
