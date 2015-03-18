package models;
 
import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;
import com.google.gson.JsonObject;
import play.data.validation.*;
import play.data.binding.As;
@Entity
public class Customer extends Model {
 
	
	 
    @Required
    public String email;
        
    @Required
    public String name;
    
    
    @MaxSize(300)	
    public String address;
    
    public int gender;
    public String country;
    public String phone;
    
   
	@As("yyyy-MM-dd")
    public Date birthday;
    
    public String toString() {
        return email;
    }
    
//    public Customer(String email, String name, String address, String country, String phone) {
//        this.email = email;
//        this.name = name;
//        this.address = address;
//        this.country = country;
//        this.phone = phone;
//    }
    
    public static User connect(String email, String password) {
        return find("byEmail", email).first();
    }

 
}