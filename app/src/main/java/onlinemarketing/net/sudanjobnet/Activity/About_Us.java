package onlinemarketing.net.sudanjobnet.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import onlinemarketing.net.sudanjobnet.R;


/**
 * Created by muawia.ibrahim on 5/2/2016.
 */
public class About_Us extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        setContentView(R.layout.about_us);
        //this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView t2 = (TextView) findViewById(R.id.about_text);
        t2.setMovementMethod(LinkMovementMethod.getInstance());
        this.findViewById(R.id.positive_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
