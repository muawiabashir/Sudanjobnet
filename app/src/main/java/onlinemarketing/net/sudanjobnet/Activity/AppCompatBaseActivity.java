package onlinemarketing.net.sudanjobnet.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Adil on 7/16/2015.
 */
public abstract class AppCompatBaseActivity extends AppCompatActivity {

    private static final String TAG="BasicAppCompatActivity";
    public AppCompatBaseActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initViews();
        initValues();
        setAppBar();
        initValuesInViews();
        setOnViewClickListener();
        setOnViewTouchListener();
        setOnViewSwipe();
        setOnViewItemClickListener();
    }


    public void setOnViewSwipe(){Log.v(TAG, "setOnViewSwipe()");}

    public void initViews(){
        Log.v(TAG, "initViews()");
    }

    public void initValues(){
        Log.v(TAG, "initValues()");
    }

    public void setAppBar(){
        Log.v(TAG, "setAppBar");
    }

    public  void initValuesInViews(){
        Log.v(TAG, "initValuesInViews");
    }

    public  void setOnViewTouchListener(){
        Log.v(TAG, "setOnViewTouchListener");
    }

    public  void setOnViewClickListener(){
        Log.v(TAG, "setOnViewClickListener");
    }

    public  void setOnViewItemClickListener(){
        Log.v(TAG, "setOnViewItemClickListener()");
    }
}
