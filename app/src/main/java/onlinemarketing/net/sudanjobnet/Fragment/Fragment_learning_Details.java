package onlinemarketing.net.sudanjobnet.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.FrameLayout;
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

import onlinemarketing.net.sudanjobnet.Json.CustomVolleyRequest;
import onlinemarketing.net.sudanjobnet.Model.JobItems;
import onlinemarketing.net.sudanjobnet.Model.LearningItems;
import onlinemarketing.net.sudanjobnet.R;
import onlinemarketing.net.sudanjobnet.util.Util;


/**
 * Created by muawia.ibrahim on 1/20/2016.
 */
public class Fragment_learning_Details extends Activity {

    String URL = "http://www.learnpage.net/applearndet.php?pid=";
    String URL_det = "http://www.learnpage.net/event.php?id=";
    //SqlHandler db;
    Context mContext;
    LearningItems learningItems = new LearningItems();
    private TextView title;
    private TextView company_name;
    private TextView closing;
    private TextView city;
    private TextView footer,length1,duration1;
    private JobItems pid;
    private ImageView clogo;
    private ImageLoader imageLoader;
    private ArrayList<LearningItems> jobItemsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn_details);
      //  db = new SqlHandler(this);

        LinearLayout linlaHeaderProgress = findViewById(R.id.linlaHeaderProgress);



        // pid = (TextView) findViewById(R.id.pid);
        title = findViewById(R.id.title_learn);
        company_name = findViewById(R.id.company_name_learn);
        closing = findViewById(R.id.closing_learning);
       // city = findViewById(R.id.city);
        footer = findViewById(R.id.footer_learn);
        clogo = findViewById(R.id.clogo_learn);
        length1 = findViewById(R.id.length_learn);
        duration1 = findViewById(R.id.duration_learn);
       // db = new SqlHandler(this);
        FrameLayout apply = findViewById(R.id.apply_learn);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LearningItems jobItem = new LearningItems();
                jobItem = (LearningItems) getIntent().getSerializableExtra("item");
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
                            Fragment_learning_Details.this, false);
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



    }

    private void getData() {

        learningItems = (LearningItems) getIntent().getSerializableExtra("item");
        // Intent i = getIntent();
        //   pid =i.getExtras(Fragment_Job_List)
        //Showing a progress dialog
        //     final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, false);
        final ProgressDialog loading = ProgressDialog.show(Fragment_learning_Details.this, "Loading Data", "Please wait...", false, false);
        //Creating a json array request
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(URL + learningItems.getPid(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonarray = response.getJSONArray("learn");

                            loading.dismiss();
                            for (int i = 0; i < jsonarray.length(); i++) {
                                LearningItems learningItems = new LearningItems();
                                JSONObject json = null;

                                try {


                                    json = jsonarray.getJSONObject(i);

                                    String pid = json.getString("pid");
                                    learningItems.setPid(pid);
                                    String footer1 = json.getString("footer");
                                    learningItems.setPid(footer1);
                                    String titleStr = json.getString("title");
                                    learningItems.setTitle(titleStr);
                                    String company_name1 = json.getString("company_name");
                                    learningItems.setCompany_name(company_name1);
                                    String city1 = json.getString("city");
                                    learningItems.setCity(city1);
                                    String closing1 = json.getString("closing");
                                    learningItems.setClosing(closing1);
                                    String footer2 = json.getString("footer");
                                    learningItems.setFooter(footer2);
                                    String length = json.getString("length");
                                    learningItems.setClosing(length);
                                    String duration = json.getString("duration");
                                    learningItems.setClosing(duration);

                                   // String footer2 = json.getString("footer");

                                  //  jobitems.setFooter(footer2);
                                    // title.setText(jobitems.getTitle());
                                    // company_name.setText(jobitems.getCompany_name());
                                    title.setText(Html.fromHtml(learningItems.getTitle()));
                                    company_name.setText(Html.fromHtml(learningItems.getCompany_name()));
//                                    city.setText(Html.fromHtml(learningItems.getCity()));
                                    closing.setText(learningItems.getClosing());
                                    footer.setText(Html.fromHtml(learningItems.getFooter()));
                    //                length1.setText(Html.fromHtml(learningItems.getLength()));
//                                    duration1.setText(Html.fromHtml(learningItems.getDuration()));
                                    imageLoader = CustomVolleyRequest.getInstance(Fragment_learning_Details.this).getImageLoader();

                                   // imageLoader.get(learningItems.getClogo(), ImageLoader.getImageListener(clogo, R.drawable.ic_launcher, android.R.drawable.ic_dialog_alert));
                                 //   db.FillDetails(pid, titleStr, company_name1, closing1, city1, footer2);
//                                  ((PiwikApplication)getApplication()).getTracker();
//                                 //  .trackScreenView("Learn Details",pid +" "+ titleStr +" "+company_name1);
//                                    Tracker tracker = ((PiwikApplication) getApplication()).getTracker();
//                                    TrackHelper.track().screen("Learn Details").title("Learn Details").with(tracker);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                jobItemsArrayList.add(learningItems);

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


}
