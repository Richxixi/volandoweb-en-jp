
package models;

import java.util.*;

import javax.persistence.*;

import org.joda.time.DateTime;

import play.db.jpa.*;
import com.google.gson.JsonObject;
import play.data.validation.*;
import play.data.binding.As;
@Entity
public class Room_record extends Model 
{   
    public String customer_email;	
    public long room_id;
    public int restaurant_record_id;
    public int discount_room_id;
    public int person_number;
    @MaxSize(45)
    public String pay_approach;
    
    @MaxSize(150)
    public String other_requirement;
    @As("yyyy-MM-dd")
    public Date checkin_date;
    @As("yyyy-MM-dd")
    public Date checkout_date;
    @MaxSize(45)
    public String communicate_approach;
    @MaxSize(45)
    public String communicate_time;
    @MaxSize(20)
    public String VAT;
    public String live_day;
    
    public String toString() {
        return customer_email;
    }
    
    
    
 
}

