package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;


import models.GuestBook;
import models.GuestBook_record;
import models.Hotspring;
import models.Hotspring_record;
import models.Restaurant;
import models.Restaurant_record;
import models.Room;
import models.Room_record;
import models.User;
import play.data.Upload;
import play.libs.Crypto;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class GuestbookManagement extends Controller {
	private static void init(){
		// Get Users
		List<User> users = User.findAll();
		int users_size = users.size();
	
		renderArgs.current().put("users", users);
		renderArgs.current().put("users_size", users_size);
	
		//Get messages
		List<GuestBook> guestbooks = GuestBook.find("byIsDeleteAndStatus", false, false).fetch(); 
		int guestbooks_size = guestbooks.size();
		
		renderArgs.current().put("guestbooks", guestbooks);
		renderArgs.current().put("guestbooks_size", guestbooks_size);
		
		//Get right top numbers
		List<Restaurant> restaurants= Restaurant.findAll();
		renderArgs.current().put("restaurants_size", restaurants.size());
		
		List<Hotspring> hotspring = Hotspring.findAll();
		renderArgs.current().put("hotspring_size", hotspring.size());
		
		List<Room> rooms= Room.findAll();
		renderArgs.current().put("rooms_size", rooms.size());
		
	}
	
	public static void list() {
		init();
		
		List<GuestBook> guestbook = GuestBook.find("byIsDelete", false).fetch();
		render(guestbook);
    }
	

	public static void show(long id) throws FileNotFoundException 
	{
		init();
		
		final GuestBook guestbook = GuestBook.findById(id);
		guestbook.view = true;
		guestbook.save();
		
		List<GuestBook_record> record = GuestBook_record.find("byGb_id", id).fetch();
		
		
        render(guestbook,guestbook.getId(), record);
    }
	
	public static void delete(long id) throws FileNotFoundException 
	{
		final GuestBook guestbook = GuestBook.findById(id);
		guestbook.isDelete = true;
		guestbook.save();
		
		list();
    }
	
	
	public static void replyGuestbookByID(long id, String content) throws ParseException, FileNotFoundException 
	{
		final GuestBook guestbook = GuestBook.findById(id);
		String title;
		
		if(content != ""){
			GuestBook_record record = new GuestBook_record();
			record.gb_id = id;
			record.email = session.get("email");
			record.content = content;
			record.create_time = new Date();
			record.save();
			
			if(guestbook.content.length() < 20){
				title = guestbook.content.substring(0, guestbook.content.length());
			}
			else{
				title = guestbook.content.substring(0, 20) + "......";
				
			}
			
			if(guestbook.deviceType == "pc"){
				myMailer.replyCustomMail(guestbook.name, guestbook.email, title, content);
			}
				
			guestbook.status = true;
			guestbook.save();
		}
		
        show(id);
	}
	 
}
