package models;
import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;
import com.google.gson.JsonObject;
import play.data.validation.*;
import play.data.binding.As;
@Entity
public class MealSet extends Model 
{   
    @Required
    public String name;
    
    
    @MaxSize(45)	
    public String pic_location;
    public long restaurant_id;
    public int prize;
    @MaxSize(300)	
    public String description;

    public String toString() {
        return name;
    }
}

