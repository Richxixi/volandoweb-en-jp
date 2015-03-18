package models;

import java.util.*;

import javax.persistence.*;

import org.joda.time.DateTime;

import play.db.jpa.*;
import com.google.gson.JsonObject;
import play.data.validation.*;
import play.data.binding.As;
@Entity
public class Restaurant_record extends Model 
{   
    public String customer_email;	
    public long restaurant_id;
    public int discount_id;
    public int person_number;
    @MaxSize(45)
    public String pay_approach;
    @MaxSize(45)
    public String phone;
    @MaxSize(150)
    public String habit;
    @MaxSize(150)
    public String other_requirement;
    @MaxSize(150)
    public String members;
    
    @As("yyyy-MM-dd")
    public Date order_date;
    
    @MaxSize(45)
    public String communicate_time;
    @MaxSize(20)
    public String VAT;
   
    public String toString() {
        return customer_email;
    }
    
    
    
 
}

