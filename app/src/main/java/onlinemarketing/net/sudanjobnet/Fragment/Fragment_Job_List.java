package onlinemarketing.net.sudanjobnet.Fragment;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.piwik.sdk.Tracker;
import org.piwik.sdk.extra.TrackHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;
import onlinemarketing.net.sudanjobnet.Activity.NotificationTargetActivity;
import onlinemarketing.net.sudanjobnet.Activity.Notification_Receiver;
import onlinemarketing.net.sudanjobnet.Activity.PiwikApp;
import onlinemarketing.net.sudanjobnet.Adapter.MyDividerItemDecoration;
import onlinemarketing.net.sudanjobnet.Adapter.RecyclerAdapterJobs;
import onlinemarketing.net.sudanjobnet.Json.CustomVolleyRequest;
import onlinemarketing.net.sudanjobnet.Model.JobItems;
import onlinemarketing.net.sudanjobnet.R;
import onlinemarketing.net.sudanjobnet.helper.SqlHandler;
import onlinemarketing.net.sudanjobnet.util.Util;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;
import static java.util.Collections.*;

/**
 * Created by muawia.ibrahim on 1/11/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Fragment_Job_List extends Fragment implements RecyclerAdapterJobs.OnItemClick, SwipeRefreshLayout.OnRefreshListener {
    String URL = "http://sudanjob.net/appjobs.php?limit=" + LOAD_LIMIT;
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ArrayList<JobItems> jobItemsArrayList = new ArrayList<>();
    private RecyclerAdapterJobs adapter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private TextView title, company_name, closing, city, footer, ending;
    private ImageView clogo;
    private static final int LOAD_LIMIT = 10;
    private static final int LOAD_LIMIT_loadMore = 100;
    private String lastId = "0"; // this will issued to php page, so no harm make it string
    private LinearLayout linlaHeaderProgress;
    private boolean itShouldLoadMore = true;
    private TextView time_agoe;
    //SqlHandler db;
    CoordinatorLayout rootLayout;
    Button mySnackbar;
private SqlHandler db;
    public Fragment_Job_List() {

    }


    @Nullable

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_list, container, false);
        //     db = new SqlHandler(getActivity());
        //((PiwikApp) getActivity().getApplication()).getTracker()
        //      .trackScreenView("Jobs List", "Jobs List");
        linlaHeaderProgress = view.findViewById(R.id.linlaHeaderProgress);
        Tracker tracker = ((PiwikApp) getActivity().getApplication()).getTracker();
        TrackHelper.track().screen("Jobs List").title("Jobs List").with(tracker);
        TrackHelper.track().download().with(tracker);
        recyclerView = view.findViewById(R.id.myList);
        //  time_agoe=(TextView)view.findViewById(R.id.mago) ;

        db = new SqlHandler(getActivity());
        jobItemsArrayList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL, 36));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);

                // Fetching data from server
//                jobItemsArrayList.clear();
                firstLoadData();
            }
        });





        adapter = new RecyclerAdapterJobs(jobItemsArrayList, getActivity());
        adapter.setOnItemClickListener(this);

        //Adding adapter to recyclerview
        //Collections.sort(jobItemsArrayList, Collections.reverseOrder());
        recyclerView.setAdapter(adapter);
        //final   AlertDialog dialog = new SpotsDialog(getActivity());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            // for this tutorial, this is the ONLY method that we need, ignore the rest
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    // Recycle view scrolling downwards...
                    // this if statement detects when user reaches the end of recyclerView, this is only time we should load more
                    if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                        // remember "!" is the same as "== false"
                        // here we are now allowed to load more, but we need to be careful
                        // we must check if itShouldLoadMore variable is true [unlocked]
                        if (itShouldLoadMore) {
                            loadMore();
                        }
                    }

                }
            }
        });

        //    mySnackbar = (Button) view.findViewById(R.id.mbutton);
        title = view.findViewById(R.id.title);
        company_name = view.findViewById(R.id.company_name);
        closing = view.findViewById(R.id.closing);
        city = view.findViewById(R.id.city_job);
        footer = view.findViewById(R.id.footer);

        clogo = view.findViewById(R.id.clogo);

        return view;


    }

    //This method will get data from the web api
    private void firstLoadData() {
        if (Util.checknetwork(getActivity())) {

            final AlertDialog dialog = new SpotsDialog(getActivity(), R.style.progress_dialog);
            dialog.show();
            mSwipeRefreshLayout.setRefreshing(false);
         //   linlaHeaderProgress.setVisibility(View.VISIBLE);

            itShouldLoadMore = false;
            JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(URL, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            itShouldLoadMore = true;

                            try {
                                JSONArray jsonarray = response.getJSONArray("product");
                          //      linlaHeaderProgress.setVisibility(View.GONE);

                                dialog.dismiss();
                                //calling method to parse json array
                                firstloadData(jsonarray);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                       //     linlaHeaderProgress.setVisibility(View.GONE);
                            itShouldLoadMore = true;
                            dialog.dismiss();
                            mSwipeRefreshLayout.setRefreshing(false);
                            String message = null;
                            if (volleyError instanceof NetworkError) {
                                message = "Cannot connect to Internet...Please check your connection!";
                            } else if (volleyError instanceof ServerError) {
                                message = "The server could not be found. Please try again after some time!!";
                            } else if (volleyError instanceof AuthFailureError) {
                                message = "Cannot connect to Internet...Please check your connection!";
                            } else if (volleyError instanceof ParseError) {
                                message = "Parsing error! Please try again after some time!!";
                            } else if (volleyError instanceof NoConnectionError) {
                                message = "Cannot connect to Internet...Please check your connection!";
                            } else if (volleyError instanceof TimeoutError) {
                                message = "Connection TimeOut! Please check your internet connection.";
                            }
                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

                        }

                    }) {

            };
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonArrayRequest.setShouldCache(true);

            //Creating request queue
            CustomVolleyRequest.getInstance(getContext()).getRequestQueue().add(jsonArrayRequest);

        } else {
            mSwipeRefreshLayout.setRefreshing(false);
//Toast.makeText(getApplicationContext(),"there is no internet please check the connectivity ",Toast.LENGTH_LONG).show();
        }
    }

    //This method will parse json data
    private void firstloadData(JSONArray array) {

        for (int i = 0; i < array.length(); i++) {
            JobItems jobitems = new JobItems();
            JSONObject json = null;

            try {


                json = array.getJSONObject(i);
                if (json != null) {
                    String pid1 = json.getString("pid");
                    jobitems.setPid(pid1);
                    lastId = json.getString("pid");
                    Log.v("Last ID First Load", lastId);
                    String logo = json.getString("logo");
                    jobitems.setClogo(logo);
                    String title1 = json.getString("title");
                    jobitems.setTitle(title1);
                    String company_name1 = json.getString("company_name");
                    jobitems.setCompany_name(company_name1);
                    String closing1 = json.getString("closing");
                    jobitems.setClosing(closing1);
                    // LocalDate today = LocalDate.now();
                    Calendar c1 = Calendar.getInstance();
                    DateTimeFormatter fmt = DateTimeFormat.forPattern("dd MMMM yyyy");
                    DateTime c_date = fmt.parseDateTime(closing1);
                    String posted_on = json.getString("posted_on");
                    jobitems.setPosted_on(posted_on);
                    db.FillData2(pid1,title1,company_name1,closing1,posted_on);

                    DateTime today = new DateTime();
                    int todyint = today.getDayOfMonth();
                    Period period = new Period(today, c_date);
                    adapter.notifyDataSetChanged();
                    Days days = Days.daysBetween(today.withTimeAtStartOfDay(), c_date.withTimeAtStartOfDay());
                    if (days.getDays() == 0) {
                        getTitle(title1);
//        if (days.getDays()==0 ) {
                        //        Toast.makeText(getActivity(), "today is the last day for :" + "Positions:\n"+title+"\n\n", Toast.LENGTH_LONG).show();
//                    new MaterialStyledDialog.Builder(getActivity())
//                            .setTitle("Sudanjob.net")
//                            .setDescription("Last day for the following Positions:" + title + "\n\n")
//                            .setStyle(Style.HEADER_WITH_ICON)
//                            .setHeaderColor(R.color.colorAccent)
//                            .withDialogAnimation(true)
//                            .setCancelable(true)
//
//                            .setIcon(R.mipmap.icon_sudanjob1)
//
//                            //.setStyle(Style.HEADER_WITH_TITLE)
//                            .withIconAnimation(true)
//                            .show();


//                    NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
//                    NotificationCompat.Builder builder = null;
//                    Intent intent = new Intent(getActivity(), Fragment_Job_List.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    PendingIntent pi = PendingIntent.getActivity(getActivity(),
//                            0 /* Request code */,
//                            intent,
//                            PendingIntent.FLAG_ONE_SHOT);
//
//                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                        int importance = NotificationManager.IMPORTANCE_DEFAULT;
//                        NotificationChannel notificationChannel = new NotificationChannel("ID", "Name", importance);
//                        notificationManager.createNotificationChannel(notificationChannel);
//                        builder = new NotificationCompat.Builder(getActivity(), notificationChannel.getId());
//                    } else {
//                        builder = new NotificationCompat.Builder(getActivity());
//                    }
//
//                    builder = builder
//                            .setSmallIcon(R.mipmap.icon_sudanjob1)
//                            .setColor(ContextCompat.getColor(getActivity(), R.color.colorAccent))
//                            .setContentTitle(getActivity().getString(R.string.app_name))
//                            .setTicker(getActivity().getString(R.string.hary))
//                            .setContentText("Last day for the following Positions:" + title + "\n\n")
//                            //.setDefaults(Notification.DEFAULT_ALL)
//                            .setSound(Uri.parse("android.resource://onlinemarketing.net.sudanjobnet/"+ R.raw.sudajobmp3))
//                            .setContentIntent(pi)
//                            .setAutoCancel(true);
//
//                    notificationManager.notify(0, builder.build());

//                if (days.getDays()==0 ) {
//
//                    Toast.makeText(getApplicationContext(), "today is the last day for :" + title, Toast.LENGTH_LONG).show();
//
                        //      }

                    }
                } else {
                    Toast.makeText(getActivity(), "there is no Post Now ", Toast.LENGTH_LONG).show();
                }
                //   Log.v("Last ID First Load",lastId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mSwipeRefreshLayout.setRefreshing(false);
            jobItemsArrayList.add(jobitems);

//            adapter = new RecyclerAdapterJobs(jobItemsArrayList, getActivity());
//            adapter.setOnItemClickListener(this);
//
//            //Adding adapter to recyclerview
//            //Collections.sort(jobItemsArrayList, Collections.reverseOrder());
//            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();


        }

        //Finally initializing our adapter
        //    Toast.makeText(getApplicationContext(),lastId,Toast.LENGTH_LONG).show();
    }
private void getTitle(String title){
    Intent alarmIntent = new Intent(getActivity(), Notification_Receiver.class);
    alarmIntent.putExtra("title", title);
    getContext().sendBroadcast(alarmIntent);
};
    @Override
    public void OnClick(Object objet, int position) {
        Intent intent = new Intent(getActivity(), Fragment_Job_Details.class);

        JobItems item1 = new JobItems();

        if (objet instanceof JobItems)
            item1 = (JobItems) objet;

        intent.putExtra("item", item1);
        intent.putExtra("pid=", item1.getPid());
        startActivity(intent);
    }

    private void loadMore() {
        itShouldLoadMore = false; // lock this until volley completes processing
        String url = "http://sudanjob.net/appjobs.php?action=loadmore&lastId=" + lastId + "&limit=" + LOAD_LIMIT_loadMore;
        //  Toast.makeText(getApplicationContext(), lastId, Toast.LENGTH_LONG).show();
        final ProgressWheel progressWheel = getView().findViewById(R.id.progress_wheel);
        progressWheel.setVisibility(View.VISIBLE);

        progressWheel.setBarColor(getResources().getColor(R.color.colorAccent));
//        jobItemsArrayList.add(null);
//        jobItemsArrayList.remove(jobItemsArrayList.size() -1);
//        adapter.notifyItemRemoved(jobItemsArrayList.size());
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressWheel.setVisibility(View.GONE);
                        try {
                            JSONArray jsonarray = response.getJSONArray("product");


                            // since volley has completed and it has our response, now let's update
                            // itShouldLoadMore
                            itShouldLoadMore = true;
                            //calling method to parse json array
                            parseData1(jsonarray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressWheel.setVisibility(View.GONE);
                        // volley finished and returned network error, update and unlock  itShouldLoadMore
                        itShouldLoadMore = true;

                        Toast.makeText(getActivity(), "Failed to load more, network error", Toast.LENGTH_SHORT).show();
                    }

                }) {

        };
//        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        jsonArrayRequest.setShouldCache(true);
        //Creating request queue
        CustomVolleyRequest.getInstance(getContext()).getRequestQueue().add(jsonArrayRequest);
    }

    //This method will parse json data
    private void parseData1(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            JobItems jobitems = new JobItems();
            JSONObject json = null;

            try {


                json = array.getJSONObject(i);
                String pid1 = json.getString("pid");
                jobitems.setPid(pid1);
                lastId = json.getString("pid");
                Log.v("Last ID ", lastId);
//                Toast.makeText(getApplicationContext(), lastId, Toast.LENGTH_LONG).show();
                String logo = json.getString("logo");
                jobitems.setClogo(logo);
                String title1 = json.getString("title");
                jobitems.setTitle(title1);
                String company_name1 = json.getString("company_name");
                jobitems.setCompany_name(company_name1);
                String closing1 = json.getString("closing");
                jobitems.setClosing(closing1);
                String posted_on = json.getString("posted_on");
                jobitems.setPosted_on(posted_on);

                db.FillData2(pid1,title1,company_name1,closing1,posted_on);

                DateTimeFormatter fmt = DateTimeFormat.forPattern("dd MMMM yyyy");
              //  DateTime posted_no1 = fmt.parseDateTime(posted_on);

                DateTime c_date = fmt.parseDateTime(closing1);
                DateTime today = new DateTime();
                int todyint = today.getDayOfMonth();
                Period period = new Period(today, c_date);
//if (posted_no1==today){
//    ImageView posted_New= (ImageView)getView().findViewById(R.id.btn_new);
//    posted_New.setVisibility(View.VISIBLE);
//
//}
 Days days = Days.daysBetween(today.withTimeAtStartOfDay(), c_date.withTimeAtStartOfDay());
                if (days.getDays() == 0) {
//        if (days.getDays()==0 ) {
                    //        Toast.makeText(getActivity(), "today is the last day for :" + "Positions:\n"+title+"\n\n", Toast.LENGTH_LONG).show();
//                    new MaterialStyledDialog.Builder(getActivity())
//                            .setTitle("Sudanjob.net")
//                            .setDescription("Last day for the following Positions:" + title + "\n\n")
//                            .setStyle(Style.HEADER_WITH_ICON)
//                            .setHeaderColor(R.color.colorAccent)
//                            .withDialogAnimation(true)
//                            .setCancelable(true)
//
//                            .setIcon(R.mipmap.icon_sudanjob1)
//
//                            //.setStyle(Style.HEADER_WITH_TITLE)
//                            .withIconAnimation(true)
//                            .show();


//                    NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
//                    NotificationCompat.Builder builder = null;
//                    Intent intent = new Intent(getActivity(), Fragment_Job_List.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    PendingIntent pi = PendingIntent.getActivity(getActivity(),
//                            0 /* Request code */,
//                            intent,
//                            PendingIntent.FLAG_ONE_SHOT);
//
//                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                        int importance = NotificationManager.IMPORTANCE_DEFAULT;
//                        NotificationChannel notificationChannel = new NotificationChannel("ID", "Name", importance);
//                        notificationManager.createNotificationChannel(notificationChannel);
//                        builder = new NotificationCompat.Builder(getActivity(), notificationChannel.getId());
//                    } else {
//                        builder = new NotificationCompat.Builder(getActivity());
//                    }
//
//                    builder = builder
//                            .setSmallIcon(R.mipmap.icon_sudanjob1)
//                            .setColor(ContextCompat.getColor(getActivity(), R.color.colorAccent))
//                            .setContentTitle(getActivity().getString(R.string.app_name))
//                            .setTicker(getActivity().getString(R.string.hary))
//                            .setContentText("Last day for the following Positions:" + title + "\n\n")
//                            //.setDefaults(Notification.DEFAULT_ALL)
//                            .setSound(Uri.parse("android.resource://onlinemarketing.net.sudanjobnet/"+ R.raw.sudajobmp3))
//                            .setContentIntent(pi)
//                            .setAutoCancel(true);
//
//                    notificationManager.notify(0, builder.build());
                    Intent alarmIntent = new Intent(getActivity(), Notification_Receiver.class);
                    alarmIntent.putExtra("title",title1);
               getContext().sendBroadcast(alarmIntent);
                }
                //db.FillData(pid, title, company_name, closing, logo);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            jobItemsArrayList.add(jobitems);
            //    adapter.notifyItemInserted(jobItemsArrayList.size() - 1);
            //  Collections.sort(jobItemsArrayList, Collections.reverseOrder());
            adapter.notifyDataSetChanged();
            //
        }
        //Finally initializing our adapter

    }


    @Override
    public void onRefresh() {
        jobItemsArrayList.clear();
        firstLoadData();
        linlaHeaderProgress.setVisibility(View.GONE);
    }
}