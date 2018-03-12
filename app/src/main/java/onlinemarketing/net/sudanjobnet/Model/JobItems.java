package onlinemarketing.net.sudanjobnet.Model;

import java.io.Serializable;

/**
 * Created by muawia.ibrahim on 1/11/2016.
 */
public class JobItems implements Serializable {


    String pid;
    String title;
    String company_name;
    String closing;
    String clogo;
    String city;

    public String getPosted_on() {
        return posted_on;
    }

    public void setPosted_on(String posted_on) {
        this.posted_on = posted_on;
    }

    String posted_on;

    public boolean isShowShimmer() {
        return showShimmer;
    }

    public void setShowShimmer(boolean showShimmer) {
        this.showShimmer = showShimmer;
    }

    private boolean showShimmer;


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    String footer;

    public String getClogo() {
        return clogo;
    }

    public void setClogo(String clogo) {
        this.clogo = clogo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getClosing() {
        return closing;
    }

    public void setClosing(String closing) {
        this.closing = closing;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

}
