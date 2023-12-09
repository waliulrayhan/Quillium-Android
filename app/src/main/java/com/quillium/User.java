package com.quillium;

public class User {
    private String fullname;
    private String email;
    private String dob; // Date of Birth

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String fullname, String email, String dob) {
        this.fullname = fullname;
        this.email = email;
        this.dob = dob;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }
}

