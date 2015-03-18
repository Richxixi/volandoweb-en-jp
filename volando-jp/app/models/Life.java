package models;

import java.sql.Time;
import java.util.*;

import javax.persistence.*;

import org.joda.time.DateTime;

import play.db.jpa.*;
import com.google.gson.JsonObject;
import play.data.validation.*;
import play.data.binding.As;
@Entity
public class Life extends Model 
{   

	public String type;
    @Required
    public String lifetime1;
    @Required
    public String lifetime2;
    @Required
    public String lifetime3;
    @Required
    public String lifetime4;
    @Required
    public String lifetime5;
    @Required
    public String lifetime6;
    @Required
    public String lifetime7;
	
}

