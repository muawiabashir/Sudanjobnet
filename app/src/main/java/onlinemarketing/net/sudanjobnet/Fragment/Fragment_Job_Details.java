package onlinemarketing.net.sudanjobnet.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.FacebookSdk;
import com.facebook.share.widget.ShareDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.piwik.sdk.Tracker;
import org.piwik.sdk.extra.PiwikApplication;
import org.piwik.sdk.extra.TrackHelper;

import java.util.ArrayList;

import onlinemarketing.net.sudanjobnet.Json.CustomVolleyRequest;
import onlinemarketing.net.sudanjobnet.Model.JobItems;
import onlinemarketing.net.sudanjobnet.R;
import onlinemarketing.net.sudanjobnet.helper.SqlHandler;
import onlinemarketing.net.sudanjobnet.util.Util;

/**
 * Created by muawia.ibrahim on 1/20/2016.
 */
public class Fragment_Job_Details extends AppCompatActivity {

    String URL = "http://sudanjob.net/appjobsdet.php?pid=";
    String URL_det = "http://www.sudanjob.net/jobview.php?id=";
    View botomSheet;
    //SqlHandler db;
    Context mContext;
    ShareDialog shareDialog;
    SqlHandler db;
    JobItems jobItem = new JobItems();
    private LinearLayout linlaHeaderProgress;
    private TextView title;
    private TextView company_name;
    private TextView closing;
    private TextView city;
    private TextView footer;
    private JobItems pid;
    private ImageView clogo;
    private ImageLoader imageLoader;
    private BottomSheetBehavior bsheet;
    private BottomSheetBehavior mBottomSheetBehavior1;
    private ArrayList<JobItems> jobItemsArrayList = new ArrayList<>();


    // title.setText(jobItem.getTitle());
    //company_name.setText(jobItem.getCompany_name());
    // closing.setText(jobItem.getClosing());
    //    footer.setText(jobItem.getFooter());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.job_details);
        //    db = new SqlHandler(this);
        String App_ID = getString(R.string.facebook_app_id);

//botomSheet=findViewById(R.id.bottom_sheet);

//        View bottomSheet = findViewById(R.id.bottom_sheet1);
//        mBottomSheetBehavior1 = BottomSheetBehavior.from(bottomSheet);
//

        final Button share = findViewById(R.id.share);
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomsheetview = getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);

        bottomSheetDialog.setContentView(bottomsheetview);
        //  shareDialog = new ShareDialog(this);  // initialize facebook shareDialog.
        linlaHeaderProgress = findViewById(R.id.linlaHeaderProgress);


        share.setOnClickListener(new View.OnClickListener() {
            JobItems
                    jobItem = (JobItems) getIntent().getSerializableExtra("item");
            Uri imageUri = Uri.parse(jobItem.getClogo());

            @Override
            public void onClick(View v) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                // Add data to the intent, the receiving app will decide
                // what to do with it.
                share.putExtra(Intent.EXTRA_SUBJECT, "Job Title: " + jobItem.getTitle());
                share.putExtra(Intent.EXTRA_TEXT, "\n\n Hello....\n\n\n Click the link below for more information  \n\n\n" + URL_det + jobItem.getPid());
                share.putExtra(Intent.EXTRA_STREAM, imageUri);

                startActivity(Intent.createChooser(share, "Share job with friends !"));
            }
        });

        //   JobItems jobItem = new JobItems();
        // linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        // pid = (TextView) findViewById(R.id.pid);
        title = findViewById(R.id.title10);
        company_name = findViewById(R.id.company_name);
        closing = findViewById(R.id.closing);
        city = findViewById(R.id.city_job);
        footer = findViewById(R.id.footer);
        clogo = findViewById(R.id.clogo);
        Button apply = findViewById(R.id.apply1);

        FacebookSdk.sdkInitialize(getApplicationContext());

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobItems jobItem = new JobItems();
                jobItem = (JobItems) getIntent().getSerializableExtra("item");
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(URL_det + jobItem.getPid()));
                startActivity(i);
            }
        });
        db = new SqlHandler(this);

        if (Util.checknetwork(this)) {
            getData();
//            Button whatsapp = (Button) bottomSheetDialog.findViewById(R.id.whatsapp);
//            whatsapp.setOnClickListener(new View.OnClickListener() {
//                JobItems
//                        jobItem = (JobItems) getIntent().getSerializableExtra("item");
//
//                @Override
//                public void onClick(View v) {
//                    Intent share = new Intent(android.content.Intent.ACTION_SEND);
//                    share.setType("text/plain");
//                    share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//
//                    // Add data to the intent, the receiving app will decide
//                    // what to do with it.
//                    share.putExtra(Intent.EXTRA_SUBJECT, jobItem.getTitle());
//                    share.putExtra(Intent.EXTRA_TEXT, URL_det + jobItem.getPid());
//
//                    startActivity(Intent.createChooser(share, "Share job!"));
//                }
//            });
//            Button facebook = (Button) bottomSheetDialog.findViewById(R.id.facebook);
//            shareDialog = new ShareDialog(this);  // initialize facebook shareDialog.
//            facebook.setOnClickListener(new View.OnClickListener() {
//                JobItems
//                        jobItem = (JobItems) getIntent().getSerializableExtra("item");
//
//                @Override
//                public void onClick(View v) {
//                    if (ShareDialog.canShow(ShareLinkContent.class)) {
//
//                        ShareLinkContent linkContent = new ShareLinkContent.Builder()
//                                .setContentTitle(jobItem.getTitle())
//                                .setImageUrl(Uri.parse(jobItem.getClogo()))
//                                .setContentDescription(jobItem.getFooter())
//                                .setContentUrl(Uri.parse(URL_det + jobItem.getPid()))
//                                .build();
//                        shareDialog.show(linkContent);  // Show facebook ShareDialog
//                    }
//                }
//            });
        } else {

            new MaterialStyledDialog.Builder(this)
                    .setTitle("Sudanjob.net")
                    .setDescription("Please Connect to the internet...\n\n")
                    .setStyle(Style.HEADER_WITH_TITLE)
                    .setHeaderColor(R.color.colorAccent)
                    .withDialogAnimation(true)
                    .setCancelable(true)

                    .setIcon(R.mipmap.icon_sudanjob1)

                    //.setStyle(Style.HEADER_WITH_TITLE)
                    .withIconAnimation(true)
                    .show();
            JobItems jobItemdetails = new JobItems();
            jobItemdetails = (JobItems) getIntent().getSerializableExtra("item");
            ArrayList<JobItems> jobdb = db.getJobDetails(jobItemdetails.getPid());

            //   Cursor c = (Cursor) db.getJobDetails(jobItem.getPid());

            for (JobItems cn : jobdb) {
                title.setText(cn.getTitle().toString());
                company_name.setText(cn.getCompany_name());
                city.setText(cn.getCity());
                closing.setText(cn.getClosing());
                footer.setText(Html.fromHtml(cn.getFooter()));
                Glide.with(this).load(cn.getClogo())
                        .thumbnail(0.5f)
                        .apply(new RequestOptions().placeholder(R.mipmap.no_image).error(R.mipmap.no_image))
                        .into(clogo);
                //  title.setText(jobdb.get(7).getFooter());
            }
        }

    }

    private void getData() {

        jobItem = (JobItems) getIntent().getSerializableExtra("item");
        // Intent i = getIntent();
        //   pid =i.getExtras(Fragment_Job_List)
        //Showing a progress dialog
        //     final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, false);
        //  final ProgressDialog loading = ProgressDialog.show(Fragment_Job_Details.this, "Loading Data", "Please wait...", false, false);
        //Creating a json array request
        linlaHeaderProgress.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(URL + jobItem.getPid(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonarray = response.getJSONArray("product");

                            linlaHeaderProgress.setVisibility(View.GONE);
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JobItems jobitems = new JobItems();
                                JSONObject json = null;

                                try {


                                    json = jsonarray.getJSONObject(i);

                                    String pid = json.getString("pid");
                                    jobitems.setPid(pid);
                                    String footer1 = json.getString("footer");
                                    jobitems.setPid(footer1);
                                    String titleStr = json.getString("title");
                                    jobitems.setTitle(titleStr);
                                    String company_name1 = json.getString("company_name");
                                    jobitems.setCompany_name(company_name1);
                                    String city1 = json.getString("city");
                                    jobitems.setCity(city1);
                                    String closing1 = json.getString("closing");
                                    jobitems.setClosing(closing1);
                                    String footer2 = json.getString("footer");

                                    jobitems.setFooter(footer2);
                                    // title.setText(jobitems.getTitle());
                                    // company_name.setText(jobitems.getCompany_name());
                                    title.setText(Html.fromHtml(jobitems.getTitle()));
                                    company_name.setText(Html.fromHtml(jobitems.getCompany_name()));
                                    city.setText(Html.fromHtml(jobitems.getCity()));
                                    closing.setText(jobitems.getClosing());
                                    footer.setText(Html.fromHtml(jobitems.getFooter()));
                                    imageLoader = CustomVolleyRequest.getInstance(Fragment_Job_Details.this).getImageLoader();
                                    imageLoader.get(jobItem.getClogo(), ImageLoader.getImageListener(clogo, R.mipmap.no_image, R.mipmap.no_image));
                                    db.FillDetails(pid, titleStr, company_name1, closing1, city1, footer2);
//                                      ((PiwikApp)getApplication()).getTracker()
//                                           .trackScreenView("Job Details",pid +" "+ titleStr +" "+company_name1);

                                    Tracker tracker = ((PiwikApplication) getApplication()).getTracker();
                                    TrackHelper.track().screen("Job Details").title("\"Job Details\",pid +\" \"+ titleStr +\" \"+company_name1").with(tracker);
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
                        linlaHeaderProgress.setVisibility(View.GONE);
                    }

                }) {


        };

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(jsonArrayRequest);
    }


}
