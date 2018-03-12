package onlinemarketing.net.sudanjobnet.Model;

import android.app.LauncherActivity;

/**
 * Created by muawia.ibrahim on 2/22/2018.
 */

public class Header extends LauncherActivity.ListItem {
    private String header;

    public Header(){}

    public String getHeader() {
        return header;
    }
    public void setHeader(String header) {
        this.header = header;
    }
}
