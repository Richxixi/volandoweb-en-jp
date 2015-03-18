package models;
import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;
import com.google.gson.JsonObject;
import play.data.validation.*;
import play.data.binding.As;
@Entity
public class Room_capacity extends Model
{
   
    public Date room_date;
    public int prize;
    public int capicity;
    public long room_id;

}
