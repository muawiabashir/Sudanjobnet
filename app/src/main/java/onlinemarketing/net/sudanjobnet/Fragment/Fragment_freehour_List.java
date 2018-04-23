package onlinemarketing.net.sudanjobnet.Fragment;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import onlinemarketing.net.sudanjobnet.Activity.About_Freehour;
import onlinemarketing.net.sudanjobnet.Adapter.RecyclerAdapterFreeHour;
import onlinemarketing.net.sudanjobnet.Json.CustomVolleyRequest;
import onlinemarketing.net.sudanjobnet.Model.FreeHourItems;
import onlinemarketing.net.sudanjobnet.R;
import onlinemarketing.net.sudanjobnet.helper.SqlHandler;
import onlinemarketing.net.sudanjobnet.util.Util;


/**
 * Created by muawia.ibrahim on 1/11/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Fragment_freehour_List extends Fragment implements RecyclerAdapterFreeHour.OnItemClick, SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout mSwipeRefreshLayout;
    String URL = "http://www.learnpage.net/appfreehour.php";
    ArrayList<FreeHourItems> jobItemsArrayList1;
    Button mySnackbar;
    private ArrayList<FreeHourItems> jobItemsArrayList = new ArrayList<>();
    private RecyclerAdapterFreeHour adapter;
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private TextView title, company_name, closing, city, footer;
    private ImageView clogo;
    SqlHandler db;
    private LinearLayout no_content;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    private LinearLayout linlaHeaderProgress;

    public Fragment_freehour_List() {

    }


    @Nullable

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_freehour_list, container, false);
     //   db = new SqlHandler(getActivity());
        //  ((PiwikApp) getActivity().getApplication()).getTracker()
        //        .trackScreenView("FreeHour Pages", "FreeHour Pages");
        no_content = view.findViewById(R.id.no_content);
        linlaHeaderProgress = view.findViewById(R.id.linlaHeaderProgress2);
//        Tracker tracker = ((PiwikApp) getActivity().getApplication()).getTracker();
//        TrackHelper.track().screen("FreeHour Pages").title("FreeHour Pages").with(tracker);
//        Tracker tracker = ((PiwikApp) getActivity().getApplication()).getTracker();
//        TrackHelper.track().screen("FreeHour Pages").title("FreeHour Pages").with(tracker);
//        TrackHelper.track().download().with(tracker);
        recyclerView = view.findViewById(R.id.myList1);
        jobItemsArrayList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(1000);
        animator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(animator);
        recyclerView.setLayoutManager(llm);
        // final    RecyclerView.ItemDecoration decoration =new ElementDecoration(this);
        //rootLayout = (CoordinatorLayout) view.findViewById(R.id.coordinatorLayout);

    //    mySnackbar = (Button) view.findViewById(R.id.mbutton);
        title = view.findViewById(R.id.title11);
        company_name = view.findViewById(R.id.company_name11);
        closing = view.findViewById(R.id.closing11);
//        city=(TextView)view.findViewById(R.id.city);
//        footer=(TextView)view.findViewById(R.id.footer);
        clogo = view.findViewById(R.id.clogo11);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        db = new SqlHandler(getActivity());
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);

                mSwipeRefreshLayout.setRefreshing(true);
                if (Util.checknetwork(getActivity())) {
                    getFreeHourData();


                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
//                    Snackbar snackbar = Snackbar
//                            .make(getView(), R.string.check_connection, Snackbar.LENGTH_LONG);
//                    View snackBarView = snackbar.getView();
//                    snackBarView.setBackgroundColor(Color.parseColor("#dc913d"));
//                    snackbar.setActionTextColor(Color.WHITE);
//                    snackbar.show();
                    Toast.makeText(getActivity(), "Please Connect to the internet", Toast.LENGTH_LONG).show();
                    //   Snackbar snackbar = Snackbar
//                    .make(container, R.string.check_connection, Snackbar.LENGTH_LONG);
//            View snackBarView = snackbar.getView();
//            snackBarView.setBackgroundColor(Color.parseColor("#dc913d"));
//            snackbar.setActionTextColor(Color.WHITE);
//            snackbar.show();
//////            Util
////                    .displayDialog(
////                            getString(R.string.app_name),
////                            getString(R.string.check_connection),
////                            getActivity(), false);
////            //	Toast.makeText(this,R.string.check_connection,Toast.LENGTH_LONG).show();
//            // ArrayList<LearningItems> jobList = db.getJobData();
//
//
//            //     adapter = new RecyclerAdapterLearning(jobList, getActivity());
//            //    adapter.setOnItemClickListener(this);
//            //Adding adapter to recyclerview
//            //      recyclerView.setAdapter(adapter);
//
//        }
//                    Util
//                            .displayDialog(
//                                    getString(R.string.app_name),
//                                    getString(R.string.check_connection),
//                                    getActivity(), false);
                    //	Toast.makeText(this,R.string.check_connection,Toast.LENGTH_LONG).show();
                    ArrayList<FreeHourItems> jobList = db.getFreeHourData();
                    final AlertDialog dialog = new SpotsDialog(getActivity(), R.style.progress_dialog);
                    dialog.dismiss();

                    adapter = new RecyclerAdapterFreeHour(jobList, getActivity());
                    //  adapter.setOnItemClickListener(this);
                    // Adding adapter to recyclerview
                    recyclerView.setAdapter(adapter);

                }

            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_free);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent about = new Intent(getActivity(), About_Freehour.class);
                startActivity(about);
            }
        });

        return view;


    }

    //This method will get data from the web api
    private void getFreeHourData() {
        final AlertDialog dialog = new SpotsDialog(getActivity(), R.style.progress_dialog);
        dialog.show();
        //Showing a progress dialog
        //  final ProgressDialog loading = ProgressDialog.show(getActivity(), "Loading Data", "Please wait...", false, false);
        if (Util.checknetwork(getActivity())) {
            jobItemsArrayList.clear();
        mSwipeRefreshLayout.setRefreshing(false);
      //  linlaHeaderProgress.setVisibility(View.VISIBLE);
        //Creating a json array request
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonarray = response.getJSONArray("freehour");
                            linlaHeaderProgress.setVisibility(View.GONE);
                            dialog.dismiss();
                            // mSwipeRefreshLayout.setRefreshing(false);
                            //calling method to parse json array
                            parseData(jsonarray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        dialog.dismiss();
                    }

                }) {


        };
        jsonArrayRequest.setShouldCache(true);
        //Creating request queue
        CustomVolleyRequest.getInstance(getContext()).getRequestQueue().add(jsonArrayRequest);
        }else {
//            Snackbar snackbar = Snackbar
//                    .make(getView(), R.string.check_connection, Snackbar.LENGTH_LONG);
//            View snackBarView = snackbar.getView();
//            snackBarView.setBackgroundColor(Color.parseColor("#dc913d"));
//            snackbar.setActionTextColor(Color.WHITE);
//            snackbar.show();
            //  Toast.makeText(getActivity(), "Please Connect to the internet", Toast.LENGTH_LONG).show();
            ArrayList<FreeHourItems> jobList = db.getFreeHourData();
            adapter = new RecyclerAdapterFreeHour(jobList, getActivity());
            //  adapter.setOnItemClickListener(this);
            //Adding adapter to recyclerview
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
            //  final AlertDialog dialog = new SpotsDialog(getActivity(), R.style.progress_dialog);
            dialog.dismiss();
        }

        //Adding request to the queue
        // requestQueue.add(jsonArrayRequest);

    }

    //This method will parse json data
    private void parseData(JSONArray array) {
        if (array.length() <= 0) {
            no_content.setVisibility(View.VISIBLE);
            //Toast.makeText(getContext(),"there is no item availabe",Toast.LENGTH_LONG).show();
        } else
            for (int i = 0; i < array.length(); i++) {
                FreeHourItems jobitems = new FreeHourItems();
                JSONObject json = null;
                try {


                    json = array.getJSONObject(i);
                    String pid = json.getString("pid_freehour");
                    jobitems.setPid(pid);
                    String logo = json.getString("logo");
                    jobitems.setClogo(logo);
                    String title = json.getString("title_freehour");
                    jobitems.setTitle(title);
                    String company_name = json.getString("company_name_freehour");
                    jobitems.setCompany_name(company_name);
                    String closing = json.getString("closing_freehour");
                    jobitems.setClosing(closing);
                    db.FillDataFreeHour(pid, title, company_name, closing, logo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jobItemsArrayList.add(jobitems);
                //  mSwipeRefreshLayout.setRefreshing(false);
            }
        //Finally initializing our adapter

        adapter = new RecyclerAdapterFreeHour(jobItemsArrayList, getActivity());
        adapter.setOnItemClickListener(this);
        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnClick(Object objet, int position) {
        Intent intent = new Intent(getContext(), Fragment_freehour_Details.class);

        FreeHourItems item1 = new FreeHourItems();

        if (objet instanceof FreeHourItems)
            item1 = (FreeHourItems) objet;

        intent.putExtra("item", item1);
        intent.putExtra("pid_freehour", item1.getPid());
        startActivity(intent);
    }

//JobItems jobItems=new JobItems();


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public void onRefresh() {
        getFreeHourData();
        final AlertDialog dialog = new SpotsDialog(getActivity(), R.style.progress_dialog);
        dialog.show();
     //   linlaHeaderProgress.setVisibility(View.GONE);
    }
}
