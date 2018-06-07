package com.rv.labourfinder.firebase;

public class MyCusFirebase {
    private static final MyCusFirebase ourInstance = new MyCusFirebase();

    public MyCusFirebase() {
    }

    public static MyCusFirebase getInstance() {
        return ourInstance;
    }

    String name;
    String email;
    String password;

    public MyCusFirebase(String image) {
    }

    public String getImage() {
        return image;
    }

    long mob;
    String image;


    public MyCusFirebase(String name, String email, long mob, String password, String image) {
        this.name = name;
        this.email = email;
        this.mob = mob;
        this.password = password;
        this.image = image;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getMob() {
        return mob;
    }

    public void setMob(long mob) {
        this.mob = mob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
