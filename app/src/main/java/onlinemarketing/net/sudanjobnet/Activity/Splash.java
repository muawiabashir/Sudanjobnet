package onlinemarketing.net.sudanjobnet.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import onlinemarketing.net.sudanjobnet.R;


/**
 * Created by muawia.ibrahim on 3/20/2016.
 */
public class Splash extends AppCompatActivity

{

    Thread splashTread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView( R.layout.splash_screen);
        StartAnimations();
      //  AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//        Fragment_Job_List fragment_job_list= new Fragment_Job_List();
//        fragment_job_list.firstTimeLoadData();
////        fragment_job_list.loadMore();
        downscaleBitmapUsingDensities(80,R.drawable.bachground01);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = new Explode();
            getWindow().setAllowEnterTransitionOverlap(false);
            Slide slide=new Slide(Gravity.RIGHT);
            getWindow().setReturnTransition(slide);
        }
    }
    private void StartAnimations() {


        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(Splash.this,
                            MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    Splash.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    Splash.this.finish();
                }

            }
        };
        splashTread.start();

    }
    private Bitmap downscaleBitmapUsingDensities(final int sampleSize, final int imageResId)
    {
        final BitmapFactory.Options bitmapOptions=new BitmapFactory.Options();
        bitmapOptions.inDensity=sampleSize;
        bitmapOptions.inTargetDensity=1;
        final Bitmap scaledBitmap=BitmapFactory.decodeResource(getResources(),imageResId,bitmapOptions);
        scaledBitmap.setDensity(Bitmap.DENSITY_NONE);
        return scaledBitmap;
    }
}