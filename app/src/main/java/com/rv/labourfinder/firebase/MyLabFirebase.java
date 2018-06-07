package com.rv.labourfinder.firebase;

public class MyLabFirebase {

    public String l_name, l_email, l_pass, l_proff, l_avail, l_town, l_dist, l_state, l_abtyou;
    public int l_experinceInt;
    public long l_phone;
    String image1;


    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }


    public MyLabFirebase() {

    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getL_email() {
        return l_email;
    }

    public void setL_email(String l_email) {
        this.l_email = l_email;
    }

    public String getL_pass() {
        return l_pass;
    }

    public void setL_pass(String l_pass) {
        this.l_pass = l_pass;
    }

    public String getL_proff() {
        return l_proff;
    }

    public void setL_proff(String l_proff) {
        this.l_proff = l_proff;
    }

    public String getL_avail() {
        return l_avail;
    }

    public void setL_avail(String l_avail) {
        this.l_avail = l_avail;
    }

    public String getL_town() {
        return l_town;
    }

    public void setL_town(String l_town) {
        this.l_town = l_town;
    }

    public String getL_dist() {
        return l_dist;
    }

    public void setL_dist(String l_dist) {
        this.l_dist = l_dist;
    }

    public String getL_state() {
        return l_state;
    }

    public void setL_state(String l_state) {
        this.l_state = l_state;
    }

    public String getL_abtyou() {
        return l_abtyou;
    }

    public void setL_abtyou(String l_abtyou) {
        this.l_abtyou = l_abtyou;
    }

    public int getL_experince() {
        return l_experinceInt;
    }

    public void setL_experince(int l_experinceInt) {
        this.l_experinceInt = l_experinceInt;
    }

    public long getL_phone() {
        return l_phone;
    }

    public void setL_phone(long l_phone) {
        this.l_phone = l_phone;
    }

    public MyLabFirebase(String l_name, String l_email, String l_pass, String l_proff, String l_avail, String l_town, String l_dist, String l_state, String l_abtyou, int l_experinceInt, long l_phone, String image1) {
        this.l_name = l_name;
        this.l_email = l_email;
        this.l_pass = l_pass;
        this.l_proff = l_proff;
        this.l_avail = l_avail;
        this.l_town = l_town;
        this.l_dist = l_dist;
        this.l_state = l_state;
        this.l_abtyou = l_abtyou;
        this.l_experinceInt = l_experinceInt;
        this.l_phone = l_phone;
        this.image1 = image1;

    }
}
