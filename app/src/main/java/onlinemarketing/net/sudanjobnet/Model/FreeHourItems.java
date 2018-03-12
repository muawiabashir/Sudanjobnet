package onlinemarketing.net.sudanjobnet.Model;

import java.io.Serializable;

/**
 * Created by muawia.ibrahim on 4/3/2016.
 */
public class FreeHourItems implements Serializable {

    String pid;
    String title;
    String company_name;
    String closing;
    String length;
    String city;
    String duration;
    String footer;

    public String getClogo() {
        return clogo;
    }

    public void setClogo(String clogo) {
        this.clogo = clogo;
    }

    String clogo;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getClosing() {
        return closing;
    }

    public void setClosing(String closing) {
        this.closing = closing;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }


}
