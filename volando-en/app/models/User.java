package models;
 
import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;
import com.google.gson.JsonObject;
import play.data.validation.*;
 
@Entity
public class User extends Model {
 
    @Required
    @MinSize(8)
    public String name;
        
    @Required
    @Email
    public String email;
    
    @Required
    public String gender;
        
    @Required
    @MaxSize(1000)
    public String address;
    
    public String zipcode;
    public String company;
    public String title;
    
    public String toString() {
        return email;
    }
    public String password;
    public String username;
    @Phone
    public String phone;
    public String country;
    public String last_name;
    public String nationality;
    public String visa_no;
    public String birth_year;
    public String birth_month;
    public String birth_day;
    public String phone_code;
    public String cellphone;
    public String city;
    public boolean isAdmin;
    public boolean isManager;
    public boolean isApproved;
    
    /*public User(String email, String password, String fullname) {
        this.email = email;
        this.password = password;
        this.username = fullname;
        this.isApproved = false;
    }*/
    
    public User() {
        this.username = username;
        this.password = password;
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.nationality = nationality;
        this.visa_no = visa_no;
        this.birth_year = birth_year;
        this.birth_month = birth_month;
        this.birth_day = birth_day;
        this.address = address;
        this.gender = gender;
        this.phone = phone;
        this.phone_code = phone_code;
        this.cellphone = cellphone;
        this.city = city;
        this.country = country;
        this.isApproved = false;
    }
    
    public static User connect(String email, String password) {
        return find("byEmailAndPassword", email, password).first();
    }

    public void approve(Boolean approve) {
        if (approve) {
            this.isApproved = true;
        } else {
            this.isApproved = false;
        }
    }
 
}