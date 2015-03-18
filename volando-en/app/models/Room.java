package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;
import com.google.gson.JsonObject;
import play.data.validation.*;
import play.data.binding.As;
@Entity
public class Room extends Model 
{   
    @Required
    public String name;
    public String name_en;
    	
    public String pic_location;
    public String pic_location2;
    public String pic_location3;
    public String pic_location4;
    public String pic_location5;
    public String pic_location6;
    public String pic_location7;
    public String pic_location8;
    public String pic_location9;
    public String pic_location10;
    public String pic_location_poster;
    
    public int prize_day;
    public int prize_holigy;
    public int person_num;
    public float size;
    
    @Column(columnDefinition = "LONGTEXT") 
    public String description;
    @MaxSize(30)	
    public String bad_type;
   
    public String toString() {
        return name;
    }
    
    
 
}

