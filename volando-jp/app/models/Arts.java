package models;

import java.util.*;

import javax.persistence.*;

import org.joda.time.DateTime;

import play.db.jpa.*;
import com.google.gson.JsonObject;
import play.data.validation.*;
import play.data.binding.As;
@Entity
public class Arts extends Model 
{   
    public String type;  //art type diff artist
    
    @Required
    @As("yyyy-MM-dd")
    public Date create_time;
    
    public String size;
    public int price;
    public String name;
    public String pic_location;
    public boolean isDelete;
    
}

