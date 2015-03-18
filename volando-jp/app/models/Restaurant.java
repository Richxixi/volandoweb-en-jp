package models;

import java.util.*;

import javax.persistence.*;

import org.joda.time.DateTime;

import play.db.jpa.*;
import com.google.gson.JsonObject;
import play.data.validation.*;
import play.data.binding.As;
@Entity
public class Restaurant extends Model 
{   
    public String time;	
    public String name;
    @MaxSize(450)
    public String description;
    public int seat_num;
  
    public String toString() {
        return name;
    }
    
    
    
 
}

