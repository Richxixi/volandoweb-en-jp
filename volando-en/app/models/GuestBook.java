package models;
 
import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;
import com.google.gson.JsonObject;
import play.data.validation.*;
import play.data.binding.As;
@Entity
public class GuestBook extends Model {

	@Required
	public String deviceType;
	
    @Required
    public String guestBookType;
        
    @Required
    public String name;
    
    public String gender;

    public String email;    
    public String lang;
    public String score;
    
    public String replyway;
    public String contacttime;
    public String servicescore;
    
    public String type;
    
    @Column(columnDefinition = "LONGTEXT") 
    public String content;
    public String phone;
    
    public boolean view;
    public boolean status;
    public boolean isDelete = false;
    public Date create_time;
 
}