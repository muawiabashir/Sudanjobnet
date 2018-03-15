package onlinemarketing.net.sudanjobnet.Fragment;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatDelegate;
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
import org.piwik.sdk.Tracker;
import org.piwik.sdk.extra.TrackHelper;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import onlinemarketing.net.sudanjobnet.Activity.PiwikApp;
import onlinemarketing.net.sudanjobnet.Adapter.RecyclerAdapterLearning;
import onlinemarketing.net.sudanjobnet.Json.CustomVolleyRequest;
import onlinemarketing.net.sudanjobnet.Model.JobItems;
import onlinemarketing.net.sudanjobnet.Model.LearningItems;
import onlinemarketing.net.sudanjobnet.R;
import onlinemarketing.net.sudanjobnet.util.Util;


/**
 * Created by muawia.ibrahim on 1/11/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Fragment_learning_List extends Fragment implements RecyclerAdapterLearning.OnItemClick, SwipeRefreshLayout.OnRefreshListener {
    String URL = "http://www.learnpage.net/applearn.php";
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<LearningItems> jobItemsArrayList1;
    CoordinatorLayout rootLayout;
    Button mySnackbar;
    private ArrayList<LearningItems> jobItemsArrayList = new ArrayList<>();
    private RecyclerAdapterLearning adapter;
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private TextView title, company_name, closing, city, footer;
    private ImageView clogo;
    //  SqlHandler db;
    private LinearLayout linlaHeaderProgress;
    private LinearLayout no_content;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    public Fragment_learning_List() {

    }


    @Nullable

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning_list, container, false);
        //    db = new SqlHandler(getActivity());
//        Tracker tracker = ((PiwikApp) getActivity().getApplication()).getTracker();
//        TrackHelper.track().screen("Learning Pages").title("Learning Pages").with(tracker);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        linlaHeaderProgress = view.findViewById(R.id.linlaHeaderProgress1);
        no_content = view.findViewById(R.id.no_content1);
        Tracker tracker = ((PiwikApp) getActivity().getApplication()).getTracker();
        TrackHelper.track().screen("Learning Pages").title("Learning Pages").with(tracker);
//        TrackHelper.track().download().with(tracker);
        // ((PiwikApp) getActivity().getApplication()).getTracker()
        //   .trackScreenView("Learning Pages", "Learning Pages");
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
//        rootLayout = (CoordinatorLayout) view.findViewById(R.id.coordinatorLayout);
//
//        mySnackbar = (Button) view.findViewById(R.id.mbutton);
        title = view.findViewById(R.id.title1);
        company_name = view.findViewById(R.id.company_name1);
        closing = view.findViewById(R.id.closing1);
//        city=(TextView)view.findViewById(R.id.city);
//        footer=(TextView)view.findViewById(R.id.footer);
        clogo = view.findViewById(R.id.clogo);
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
                if (Util.checknetwork(getActivity())) {
                    getData();


                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "Please Connect to the internet", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;


    }

    //This method will get data from the web api
    private void getData() {
        if (Util.checknetwork(getActivity())) {
            jobItemsArrayList.clear();
            mSwipeRefreshLayout.setRefreshing(false);

//            linlaHeaderProgress.setVisibility(View.VISIBLE);
            final AlertDialog dialog = new SpotsDialog(getActivity(), R.style.progress_dialog);
            dialog.show();

            //Creating a json array request
            JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(URL, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONArray jsonarray = response.getJSONArray("learn");
                               // linlaHeaderProgress.setVisibility(View.GONE);
dialog.dismiss();
                                //calling method to parse json array
                                parseData(jsonarray);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            // mSwipeRefreshLayout.setRefreshing(false);
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
            // jsonArrayRequest.setShouldCache(true);
            //Creating request queue
            CustomVolleyRequest.getInstance(getContext()).getRequestQueue().add(jsonArrayRequest);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            Snackbar snackbar = Snackbar
                    .make(getView(), R.string.check_connection, Snackbar.LENGTH_LONG);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(Color.parseColor("#dc913d"));
            snackbar.setActionTextColor(Color.WHITE);
            snackbar.show();
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
                LearningItems jobitems = new LearningItems();
                JSONObject json = null;
                try {

                    no_content.setVisibility(View.GONE);
                    json = array.getJSONObject(i);
                    String pid = json.getString("pid_learn");
                    jobitems.setPid(pid);
                    String logo = json.getString("logo");
                    jobitems.setClogo(logo);
                    String title = json.getString("title_learn");
                    jobitems.setTitle(title);
                    String company_name = json.getString("company_name_learn");
                    jobitems.setCompany_name(company_name);
                    String closing = json.getString("closing_learn");
                    jobitems.setClosing(closing);
                    //  db.FillData(pid, title, company_name, closing, logo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jobItemsArrayList.add(jobitems);

            }
        //Finally initializing our adapter

        mSwipeRefreshLayout.setRefreshing(false);
        adapter = new RecyclerAdapterLearning(jobItemsArrayList, getActivity());
        adapter.setOnItemClickListener(this);
        //Adding adapter to recyclerview
        final AlertDialog dialog = new SpotsDialog(getActivity(), R.style.progress_dialog);
        dialog.dismiss();
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnClick(Object objet, int position) {
        Intent intent = new Intent(getContext(), Fragment_learning_Details.class);

        LearningItems item1 = new LearningItems();

        if (objet instanceof LearningItems)
            item1 = (LearningItems) objet;

        intent.putExtra("item", item1);
        intent.putExtra("pid", item1.getPid());
        startActivity(intent);
    }

//JobItems jobItems=new JobItems();


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private List<JobItems> filter(List<JobItems> models, String query) {
        query = query.toLowerCase();

        final ArrayList<JobItems> filteredModelList = new ArrayList<>();
        for (JobItems model : models) {
            final String text = model.getTitle().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public void onRefresh() {
        getData();
      //  linlaHeaderProgress.setVisibility(View.GONE);\
        final AlertDialog dialog = new SpotsDialog(getActivity(), R.style.progress_dialog);
        dialog.dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
