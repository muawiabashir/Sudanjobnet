package onlinemarketing.net.sudanjobnet.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import onlinemarketing.net.sudanjobnet.Model.FreeHourItems;
import onlinemarketing.net.sudanjobnet.Model.JobItems;
import onlinemarketing.net.sudanjobnet.R;
import onlinemarketing.net.sudanjobnet.util.Util;

/**
 * Created by muawia.ibrahim on 1/20/2016.
 */
public class Fragment_freehour_Details extends FragmentActivity {
    private TextView title;
    private TextView company_name;
    private TextView closing;
    private TextView city;
    private TextView footer, length1, duration1;
    private JobItems pid;
    private ImageView clogo;
    private ImageLoader imageLoader;
    String URL = "http://www.learnpage.net/appfreehourdet.php?pid=";
    String URL_det = "http://www.learnpage.net/appfreehourdet.php?pid=";
    private ArrayList<FreeHourItems> jobItemsArrayList = new ArrayList<>();
    //SqlHandler db;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freehour_details);
  //      db = new SqlHandler(this);

        LinearLayout linlaHeaderProgress = findViewById(R.id.linlaHeaderProgress);


        // pid = (TextView) findViewById(R.id.pid);
        title = findViewById(R.id.title3);
        company_name = findViewById(R.id.company_name3);
        closing = findViewById(R.id.closing3);
        city = findViewById(R.id.length3);
        footer = findViewById(R.id.footer3);
        clogo = findViewById(R.id.clogo3);
        length1 = findViewById(R.id.length3);
        duration1 = findViewById(R.id.duration3);
     //   db = new SqlHandler(this);
        FrameLayout apply = findViewById(R.id.apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FreeHourItems jobItem = new FreeHourItems();
                jobItem = (FreeHourItems) getIntent().getSerializableExtra("item");
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
                            Fragment_freehour_Details.this, false);
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


        //  JobItems jobItem1 = new JobItems();
        //   title.setText(jobList.get())
        //   title.setText(jobItem1.getTitle());
        //   company_name.setText(jobItem1.getCompany_name());
        //   city.setText(jobItem1.getCity());
        //  closing.setText(jobItem1.getClosing());
        //  footer.setText(jobItem1.getFooter());
        //  title.setText(jobItem1.getTitle());
        // adapter = new RecyclerAdapterJobs(jobList, getActivity());
        //adapter.setOnItemClickListener(this);
        //Adding adapter to recyclerview
        //   recyclerView.setAdapter(adapter);

    }

    // title.setText(jobItem.getTitle());
    //company_name.setText(jobItem.getCompany_name());
    // closing.setText(jobItem.getClosing());
    //    footer.setText(jobItem.getFooter());


    FreeHourItems freeHourItems = new FreeHourItems();

    private void getData() {

        freeHourItems = (FreeHourItems) getIntent().getSerializableExtra("item");
        // Intent i = getIntent();
        //   pid =i.getExtras(Fragment_Job_List)
        //Showing a progress dialog
        //     final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, false);
        final ProgressDialog loading = ProgressDialog.show(Fragment_freehour_Details.this, "Loading Data", "Please wait...", false, false);
        //Creating a json array request
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(URL + freeHourItems.getPid(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonarray = response.getJSONArray("freehour");

                            loading.dismiss();
                            for (int i = 0; i < jsonarray.length(); i++) {
                                FreeHourItems jobitems = new FreeHourItems();
                                JSONObject json = null;

                                try {


                                    json = jsonarray.getJSONObject(i);

                                    String pid = json.getString("pid_freehour");
                                    jobitems.setPid(pid);

                                    String titleStr = json.getString("title_freehour");
                                    jobitems.setTitle(titleStr);
                                    String company_name1 = json.getString("company_name_freehour");
                                    jobitems.setCompany_name(company_name1);
                                    String city1 = json.getString("city_freehour");
                                    jobitems.setCity(city1);
                                    String closing1 = json.getString("closing_freehour");
                                    jobitems.setClosing(closing1);
                                    String footer2 = json.getString("footer_freehour");

                                    jobitems.setFooter(footer2);

                                    String length2 = json.getString("length_freehour");

                                    jobitems.setLength(length2);
                                    String duration2 = json.getString("duration_freehour");

                                    jobitems.setDuration(duration2);
                                    // title.setText(jobitems.getTitle());
                                    // company_name.setText(jobitems.getCompany_name());
                                    title.setText(Html.fromHtml(jobitems.getTitle()));
                                    company_name.setText(Html.fromHtml(jobitems.getCompany_name()));
                                    city.setText(Html.fromHtml(jobitems.getCity()));
                                    closing.setText(jobitems.getClosing());
                                    footer.setText(Html.fromHtml(jobitems.getFooter()));
                                    length1.setText(jobitems.getLength());
                                    duration1.setText(jobitems.getDuration());
                                     imageLoader = CustomVolleyRequest.getInstance(Fragment_freehour_Details.this).getImageLoader();
                                   // imageLoader.get(jobitems.getClogo(), ImageLoader.getImageListener(clogo, R.mipmap.no_image, R.mipmap.no_image));
                                    //     db.FillDetails(pid, titleStr, company_name1, closing1, city1, footer2);
                                    //    ((PiwikApp)getApplication()).getTracker()
                                    //        .trackScreenView("Job Details",pid +" "+ titleStr +" "+company_name1);
//                                  Tracker tracker = ((PiwikApplication) getApplication()).getTracker();
//                                  TrackHelper.track().screen("\"Job Details\",pid +\" \"+ titleStr +\" \"+company_name1").title("Job Details").with(tracker);
                             } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                jobItemsArrayList.add(jobitems);

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
