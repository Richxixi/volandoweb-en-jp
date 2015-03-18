
package models;

import java.util.*;

import javax.persistence.*;

import org.joda.time.DateTime;

import play.db.jpa.*;
import com.google.gson.JsonObject;
import play.data.validation.*;
import play.data.binding.As;
@Entity
public class Traffic_record extends Model 
{   
    public String customer_email;	
    public long traffic_id;
    public int person_number;

    public String toString() {
        return customer_email;
    }
    
    
    
 
}

