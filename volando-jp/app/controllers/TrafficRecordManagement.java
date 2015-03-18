package controllers;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.*;


import play.data.Upload;
import play.libs.Crypto;
import play.mvc.Controller;
import play.mvc.With;
import play.mvc.Scope.Session;

@With(Secure.class)
public class TrafficRecordManagement extends Controller{
	public static void index() 
	{
		
		List<Traffic> traffics = Traffic.findAll();
		renderJSON(traffics);
    }
	
	public static void addtrafficrecord(long traffic_id) throws FileNotFoundException 
	{
		Traffic_record traffic_record = new Traffic_record();
		traffic_record.save();
		redirect("/TrafficRecordManagement" + "/" + traffic_record.getId()+"/"+ traffic_id+ "/edittrafficrecord");

    }
	public static void edittrafficrecord(long id,long traffic_id) throws FileNotFoundException 
	{
		Traffic traffic  = Traffic.findById(traffic_id);
		String email = Session.current().get("email");
        System.out.println("Admin.profile(): email = " + email);
        User user = User.find("byEmail", email).first();
		final Traffic_record traffic_record = Traffic_record.findById(id);
        render(traffic_record, traffic_record.getId(),user,traffic);
    }
	public static void showtrafficrecord(long id) {
		final Traffic_record traffic_record = Traffic_record.findById(id);
		render(traffic_record, traffic_record.getId());
	}
	public static void updatetrafficrecordbyID(long id,long traffic_id, int person_number) throws ParseException 
	{
		String email = Session.current().get("email");
		final Traffic_record traffic_record = Traffic_record.findById(id);
		traffic_record.customer_email =email;
		traffic_record.person_number = person_number;
		traffic_record.traffic_id = traffic_id;
		traffic_record.save();
        //index();
		showtrafficrecord(id);
	}
	
	public static void updaterestaurntrecord(Restaurant_record restaurant_record) 
	{
		restaurant_record.save();
        index();
	}
	
	public static void forms() {
		render();
	}
	
}
