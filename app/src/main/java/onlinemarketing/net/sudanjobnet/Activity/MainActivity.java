package onlinemarketing.net.sudanjobnet.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.annotation.Nullable;
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
import onlinemarketing.net.sudanjobnet.helper.SqlHandler;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String TAG_TITLE = "title";
    public static final String TAG_COMPANY_NAME = "company_name";
    public static final String TAG_CLOSING = "closing";
    public static final String TAG_DATE_LINE = "dateline";
    public static final String TAG_Posting = "posted_on";
    public static final String TAG_PID = "pid";
    public static final String ID = "_id";
    private static final int THREAD_ID = 10000;
    String HeaderURL = "http://www.sudanjob.net/apphead.php";
    NavigationView navigationView = null;
    boolean doubleBackToExitPressedOnce = false;
    private SqlHandler db;
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

        TrafficStats.setThreadStatsTag(THREAD_ID);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //  toolbar.setLogo(R.mipmap.icon_sudanjob1);

        //   getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        //  getSupportActionBar().setDisplayShowTitleEnabled(true);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setHomeButtonEnabled(true);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false); //disable "hamburger to arrow" drawable
        toggle.setHomeAsUpIndicator(R.drawable.ic_action_drawar); //set your own
        toggle.syncState();

        getClosing_posts_Sent2BroadCast();
        navigationView = findViewById(R.id.nav_view);
        header_progress = findViewById(R.id.header_progress);

        navigationView.setNavigationItemSelectedListener(this);
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(onTabSelectedListener(viewPager));
//        setupTabIcons();
        setupToolbar();
        navigationView.setItemIconTintList(null);
        setupViewPager();

        setupCollapsingToolbar();
        getHeaderImage();
        check_4_Updates();
        NetworkImageView header_inageview = findViewById(R.id.image_header);
        header_inageview.setImageResource(R.drawable.appheader);

        viewPager.setOffscreenPageLimit(1);
//       TextView tv2=(TextView)findViewById(R.id.toolbar_title);
//
//        Typeface face= Typeface.createFromAsset(getAssets(), "font01.ttf");
//        tv2.setTypeface(face);

    }

    private void setupCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = findViewById(
                R.id.collapse_toolbar);


    }


    private void setupViewPager() {
        final ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        onTabSelectedListener(viewPager);
        // setupTabIcons();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setIcon(R.drawable.ic_action_icon_sudanjob);
        //getSupportActionBar().setIcon(R.drawable.ic_action_icon_sudanjob);
        //  getSupportActionBar().setIcon(R.mipmap.icon_sudanjob_round);
        //   getSupportActionBar().setTitle("Sudanjob.net");
        //  toolbar.setLogo(R.mipmap.icon_sudanjob1);
        //  toolbar.setSubtitle("Get A Better Job");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setLogo(R.mipmap.icon_sudanjob1);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    public void check_4_Updates() {
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

    private TabLayout.OnTabSelectedListener onTabSelectedListener(final ViewPager viewPager) {

        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
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
        adapter.addFrag(new Fragment_go_event_List(), " Go Events");
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

        } else if (id == R.id.sign_up) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://sudanjob.net/register.php"));
            startActivity(i);

        }
//        if (id == R.id.about_sudanjob1) {
//            Intent about = new Intent(this, About_Us.class);
//            startActivity(about);
//            return true;
//        }
//        if (id == R.id.about_freehoure1) {
//            Intent about = new Intent(this, About_Freehour.class);
//            startActivity(about);
//            return true;
//        }
//        if (id == R.id.about_go_event1) {
//            Intent about = new Intent(this, About_Go_Envent.class);
//            startActivity(about);
//            return true;
//        }
//        if (id == R.id.about_learn1) {
//            Intent about = new Intent(this, About_Learn.class);
//            startActivity(about);
//            return true;
//        }
        if (id == R.id.close) {
            finish();
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
//                Glide.with(this).load(header)
//                        .thumbnail(0.5f)
//
//                        .into(header_inageview);
                imageLoader.get(headerImage.getHeader(), ImageLoader.getImageListener(header_inageview, R.mipmap.no_image, R.mipmap.no_image));
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
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // .... other stuff in my onResume ....
        this.doubleBackToExitPressedOnce = false;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    public void getClosing_posts_Sent2BroadCast() {
        SqlHandler db;
        db = new SqlHandler(this);
        String DB_TITLE, DB_COMPAN_NAME, DB_PID;

//        db.open();
        Cursor cr = db.getToDay_ClosedPost();
        if (cr != null && cr.getCount() != 0) {


            cr.moveToFirst();


            int id = cr.getColumnIndex(ID);
            int pid = cr.getColumnIndex(TAG_PID);
            int title = cr.getColumnIndex(TAG_TITLE);
            int company_name = cr.getColumnIndex(TAG_COMPANY_NAME);
            int closing = cr.getColumnIndex(TAG_CLOSING);
            DB_TITLE = cr.getString(title);
            DB_COMPAN_NAME = cr.getString(company_name);

            Intent alarmIntent = new Intent(MainActivity.this, Notification_Receiver.class);

            alarmIntent.putExtra("title", DB_TITLE);
            alarmIntent.putExtra("company", DB_COMPAN_NAME);
            this.sendBroadcast(alarmIntent);
            cr.moveToNext();
        }
        cr.close();
        db.close();
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
