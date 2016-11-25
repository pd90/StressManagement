package com.example.parasdhanta.stressmanagement.pojos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Paras Dhanta on 10/3/2016.
 */
public class LoginUser implements Parcelable {
     public String emailId;
     public String userName;
     public String userId;
     public String imageUrl;

    public static final Creator<LoginUser> CREATOR = new Creator<LoginUser>() {
        @Override
        public LoginUser createFromParcel(Parcel in) {
            return new LoginUser(in);
        }

        @Override
        public LoginUser[] newArray(int size) {
            return new LoginUser[size];
        }
    };

    public LoginUser(Parcel in) {
        readFromParcel(in);
    }

    public LoginUser() {

    }

    private void readFromParcel(Parcel in) {
        this.emailId = in.readString();
        this.userName = in.readString();
        this.userId = in.readString();
        this.imageUrl = in.readString();
    }

    //constructor that initializes the user object

    public LoginUser(String emailId,String userName,String userId,String imageUrl) {
        this.emailId = emailId;
        this.userName = userName;
        this.userId = userId;
        this.imageUrl = imageUrl;
    }
    public LoginUser(String emailId) {
        this.emailId = emailId;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.emailId);
        dest.writeString(this.userName);
        dest.writeString(this.userId);
        dest.writeString(this.imageUrl);
    }
}
