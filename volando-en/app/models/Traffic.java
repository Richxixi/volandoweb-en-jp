package models;

import java.util.*;

import javax.persistence.*;

import org.joda.time.DateTime;

import play.db.jpa.*;
import com.google.gson.JsonObject;
import play.data.validation.*;
import play.data.binding.As;
@Entity
public class Traffic extends Model 
{

    public Date time;	
    public String name;
    @MaxSize(15)
    public String end_point;
    @MaxSize(15)
    public String start_point;
    public int seat_num;
    public int reserved_number;
    public String toString() {
        return name;
    }
}
