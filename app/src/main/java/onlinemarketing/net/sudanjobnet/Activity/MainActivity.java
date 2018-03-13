package onlinemarketing.net.sudanjobnet.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import onlinemarketing.net.sudanjobnet.Adapter.RecyclerAdapterJobs;
import onlinemarketing.net.sudanjobnet.Fragment.Fragment_Job_List;
import onlinemarketing.net.sudanjobnet.Fragment.Fragment_freehour_List;
import onlinemarketing.net.sudanjobnet.Fragment.Fragment_go_event_List;
import onlinemarketing.net.sudanjobnet.Fragment.Fragment_learning_List;
import onlinemarketing.net.sudanjobnet.Json.CustomVolleyRequest;
import onlinemarketing.net.sudanjobnet.Model.HeaderImage;
import onlinemarketing.net.sudanjobnet.Model.JobItems;
import onlinemarketing.net.sudanjobnet.R;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String HeaderURL = "http://www.sudanjob.net/apphead.php";
    NavigationView navigationView = null;
    boolean doubleBackToExitPressedOnce = false;
    private TabLayout tabLayout;
    private ImageLoader imageLoader;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private ArrayList<HeaderImage> headerImageArrayList = new ArrayList<>();
    private ArrayList<JobItems> jobItemsArrayList = new ArrayList<>();
    private RecyclerAdapterJobs adapter;
    private RecyclerView recyclerView;
    private ProgressBar header_progress;
    //SqlHandler db;
    private String[] title = {"Jobs", "Learn", "Training", "Go Event"
    };
    private int[] tabIcons = {
            R.drawable.ic_tab_sudanjob,
            R.drawable.ic_tab_sudanjob,
            R.drawable.ic_tab_sudanjob,
            R.drawable.ic_tab_sudanjob
    };
    private ProgressDialog pd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        navigationView = findViewById(R.id.nav_view);
        header_progress = findViewById(R.id.header_progress);

        navigationView.setNavigationItemSelectedListener(this);
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
//        setupTabIcons();
        setupToolbar();
        navigationView.setItemIconTintList(null);
        setupViewPager();

        setupCollapsingToolbar();
        getHeaderImage();
        check_4_Updates();
        NetworkImageView header_inageview = findViewById(R.id.image_header);
        header_inageview.setImageResource(R.drawable.appheader);


    }

    private void setupCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = findViewById(
                R.id.collapse_toolbar);

        collapsingToolbar.setTitleEnabled(false);
    }

    private void setupViewPager() {
        final ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
       // setupTabIcons();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sudanjob.net");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

  private void setupTabIcons(){
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
      tabLayout.getTabAt(1).setIcon(tabIcons[1]);
      tabLayout.getTabAt(2).setIcon(tabIcons[2]);
      tabLayout.getTabAt(3).setIcon(tabIcons[3]);
  }

    public void check_4_Updates(){
        AppUpdater appUpdater = new AppUpdater(this);
        appUpdater.start();
        new AppUpdater(this)
                .setUpdateFrom(UpdateFrom.JSON)
                .setDisplay(Display.DIALOG)
                .setGitHubUserAndRepo("javiersantos", "AppUpdater")
                .setUpdateJSON("http://www.sudanjob.net/appupdate/update-changelog.json")
                .setTitleOnUpdateAvailable("Update available")
                .setContentOnUpdateAvailable("Check out the latest version available of sudanjob.net!")
                .setTitleOnUpdateNotAvailable("Update not available")
                .setContentOnUpdateNotAvailable("No update available. Check for updates again later!")
                .setButtonUpdate("Update now?")
                .setIcon(R.mipmap.ic_launcher) // Notification icon
                .setIcon(R.mipmap.ic_launcher)
                .showEvery(5)
                .start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //     super.onCreateOptionsMenu(menu, inflater);
        return true;
    }


//    private void setupTabIcons() {
//        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
//        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
//        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (item.getItemId() == android.R.id.home) {
            if (drawer.isDrawerOpen(Gravity.LEFT)) {
                drawer.closeDrawer(Gravity.LEFT);
            } else {
                drawer.openDrawer(Gravity.LEFT);
            }
        }
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Intent settings= new Intent(this,Pref.class);
//            startActivity(settings);
//            return true;
//       }


        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Fragment_Job_List(), " Jobs");
        adapter.addFrag(new Fragment_learning_List(), " Learn");
        adapter.addFrag(new Fragment_freehour_List(), " FreeHour");
        adapter.addFrag(new Fragment_go_event_List(), " Go Event");
        viewPager.setAdapter(adapter);
    }

    private void shareApp() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Sudanjob.net");
        share.putExtra(Intent.EXTRA_TEXT, "http://www.app.sudanjob.net");

//    share.putExtra(Intent.EXTRA_ASSIST_CONTEXT,)
        startActivity(Intent.createChooser(share, "Share Sudanjob app !"));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.post_job) {
            //Set the fragment initially
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://www.sudanjob.net/buy.php"));
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.post_course_workshop) {
            //Set the fragment initially
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://learnpage.net/buy.php"));
            startActivity(i);

        } else if (id == R.id.contact_us) {

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://www.sudanjob.net/contact.php"));
            startActivity(i);
        } else if (id == R.id.View_CV) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://www.sudanjob.net/cv.php"));
            startActivity(i);
        } else if (id == R.id.help) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://sudanjob.net/faqs.php"));
            startActivity(i);

        }
        else if (id == R.id.sign_up) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://sudanjob.net/register.php"));
            startActivity(i);

        }
        if (id == R.id.about_sudanjob1) {
            Intent about = new Intent(this, About_Us.class);
            startActivity(about);
            return true;
        }
        if (id == R.id.about_freehoure1) {
            Intent about = new Intent(this, About_Freehour.class);
            startActivity(about);
            return true;
        }
        if (id == R.id.about_go_event1) {
            Intent about = new Intent(this, About_Go_Envent.class);
            startActivity(about);
            return true;
        }
        if (id == R.id.about_learn1) {
            Intent about = new Intent(this, About_Learn.class);
            startActivity(about);
            return true;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        Process.killProcess(Process.myPid());
        super.onDestroy();
    }

    private void getHeaderImage() {
        //Showing a progress dialog
        //final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);
//Displaying Progressbar
        //progressBar.setVisibility(View.VISIBLE);
        //setProgressBarIndeterminateVisibility(true);
        //Creating a json array request
        header_progress.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(HeaderURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonarray = response.getJSONArray("apphead");
          //                  progressBar.setVisibility(View.GONE);
                            header_progress.setVisibility(View.GONE);
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
                        header_progress.setVisibility(View.GONE);
                    }

                }) {


        };

        //Creating request queue
        CustomVolleyRequest.getInstance(getApplicationContext()).getRequestQueue().add(jsonArrayRequest);


        //Adding request to the queue
        // requestQueue.add(jsonArrayRequest);

    }
//
//    //This method will parse json data
    private void parseData1(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            HeaderImage headerImage = new HeaderImage();
            JSONObject json = null;

            try {


                json = array.getJSONObject(i);
                String header = json.getString("header");
                headerImage.setHeader(header);
                HeaderImage headerImage1 = new HeaderImage();
                NetworkImageView header_inageview = findViewById(R.id.image_header);
                imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext()).getImageLoader();


                //  imageLoader.get(headerImage.getHeader(), ImageLoader.getImageListener(header, R.drawable.photo, R.drawable.photo));
                header_inageview.setImageUrl(header, imageLoader);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            headerImageArrayList.add(headerImage);

        }
//
  }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        CustomDialogClass cdd = new CustomDialogClass(MainActivity.this);
         cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.show();
        cdd.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //style id
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // .... other stuff in my onResume ....
        this.doubleBackToExitPressedOnce = false;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {


            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
