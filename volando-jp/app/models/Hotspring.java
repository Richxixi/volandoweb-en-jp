package models;
 
import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;
import com.google.gson.JsonObject;
import play.data.validation.*;
import play.data.binding.As;
@Entity
public class Hotspring extends Model 
{   
    @Required
    public String name;
    
    public String pic_location;
    public String pic_location2;
    public String pic_location3;
    public String pic_location4;
    public String pic_location5;
    public String pic_location6;
    public String pic_location7;
       

    public String prize_day;
    public String prize_day2;
    public String prize_day3;
    public String prize_holigy;
    public int person_num;
    public float size;
    @MaxSize(300)	
    public String description;
    public String information; //francy modified
   
    public String toString() {
        return name;
    }
    
    
 
}

