
package controllers;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.Hotspring;
import models.Hotspring_record;
import models.Restaurant;
import models.User;
import play.mvc.Controller;
import play.mvc.With;
import play.mvc.Scope.Session;

@With(Secure.class)

public class HotspringRecordManagement extends Controller {

	public static void index() {
		
		List<Hotspring_record> hot_records= Hotspring_record.findAll();
		renderJSON(hot_records);
    }
	
	public static void addhotspringrecordByID(long hotspring_id) throws FileNotFoundException 
	{
		Hotspring_record hot_record = new Hotspring_record();
		hot_record.save();
        redirect("/HotspringRecordManagement" + "/" + hot_record.getId() +"/"+hotspring_id+ "/edithotspringrecord");
    }
	public static void addhotspringrecord() throws FileNotFoundException 
	{
		
		List<Hotspring> hotsprings= Hotspring.findAll();
		render(hotsprings);
		
    }
	public static void edithotspringrecord(long id,long hotspring_id) throws FileNotFoundException 
	{
		Hotspring hotspring = Hotspring.findById(hotspring_id);
		String email = Session.current().get("email");
        System.out.println("Admin.profile(): email = " + email);
        User user = User.find("byEmail", email).first();
		final Hotspring_record hot_record = Hotspring_record.findById(id);
        render(hot_record, hot_record.getId(), user,hotspring);
    }
	
	//By Kyo
	public static void showhotspringrecord(long id) {
		final Hotspring_record hot_record = Hotspring_record.findById(id);
		render(hot_record, hot_record.getId());
	}
	
	public static void updatehotspringrecordbyID(long id,String customer_email,long hotspring_id,int restaurant_record_id,
			int discount_room_id,int person_number,String pay_approach, String other_requirement,
			Date checkin_date,Date checkout_date,String communicate_approach,String communicate_time) throws ParseException 
	{
		final Hotspring_record hot_record = Hotspring_record.findById(id);
		hot_record.customer_email =customer_email;
		hot_record.hotspring_id = hotspring_id;
		hot_record.restaurant_record_id = restaurant_record_id;
		hot_record.discount_room_id = discount_room_id;
		hot_record.person_number = person_number;
		hot_record.pay_approach = pay_approach;
		hot_record.other_requirement = other_requirement;
		hot_record.checkin_date = checkin_date;
		//hot_record.checkout_date = checkout_date;
		//hot_record.communicate_approach = communicate_approach;
		hot_record.communicate_time = communicate_time;
		hot_record.save();
        //index();
		showhotspringrecord(id);
	}
	
	public static void updatehotspringrecord(Hotspring_record hot_record) 
	{
		hot_record.save();
        index();
	}
	
	public static void forms() {
		render();
	}
	 
}
