package models;

import java.util.*;

import javax.persistence.*;

import org.joda.time.DateTime;

import play.db.jpa.*;
import com.google.gson.JsonObject;
import play.data.validation.*;
import play.data.binding.As;
@Entity
public class Page extends Model 
{   
	//@MaxSize(250)
	//public String title;

    public String type;  //project type
    @Required
    @As("yyyy-MM-dd")
    public Date create_time;
    @Required
    @As("yyyy-MM-dd")
    public Date update_time;
    
    @Column(columnDefinition = "LONGTEXT") 
    public String content;
  
   // public String iconUrl;	
}

