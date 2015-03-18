package models;
 
import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;
import com.google.gson.JsonObject;
import play.data.validation.*;
import play.data.binding.As;
@Entity
public class GuestBook_record extends Model {

    @Required
    public long gb_id;
    
    @Required
    public String email;
    public String content;
 
    public Date create_time;
 
}