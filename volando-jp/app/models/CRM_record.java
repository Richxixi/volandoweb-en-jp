package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;
import com.google.gson.JsonObject;
import play.data.validation.*;
import play.data.binding.As;
@Entity
public class CRM_record  extends Model
{
    public String email;
    public String phone;
    public String site;
}

