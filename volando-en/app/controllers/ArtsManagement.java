package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;


import models.Announce;
import models.Arts;
import models.GuestBook;
import models.Hotspring;
import models.Information;
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
public class ArtsManagement extends Controller {
	private static void init(){
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
	
	public static void index() {
		init();
		
    }
	
	public static void listAll(String type) {
		init();
		
		List<Arts> arts = Arts.find("byIsDeleteAndType", false, type.toString()).fetch();

		render(type, arts);
    }
	

	public static void add(String type) throws FileNotFoundException 
	{
		Arts art = new Arts();
		art.create_time = new Date();
		art.type = type;
		art.isDelete = false;
		art.save();
        redirect("/ArtsManagement" + "/" + art.getId() + "/editInfo");
    }
	
	public static void editInfo(Long id) throws FileNotFoundException 
	{
		init();
		final Arts art = Arts.findById(id);
        render(art,art.getId());
    }
	
	
	
	public static void imageHandler(Upload imageName)
	{
		if (imageName != null) {
			String dateString;
			Date date = new Date();
			dateString = String.valueOf(date.getTime());

	        File file = imageName.asFile();
	        String newFileName = dateString + "." + FilenameUtils.getExtension(file.getPath());
	        file.renameTo(new File("public/upload/cleditor", newFileName));
	        
	        String html = "<div id='image'>/public/upload/cleditor/" + newFileName + "</div>";
	        renderHtml(html);
	        
    	} else {
    		
    	}
	}
	
	
	public static void updateArtByID(long id,String name, String size, int price,  
			String pic_location) throws ParseException, FileNotFoundException 
	{
		final Arts art = Arts.findById(id);
		art.name = name;
		art.size = size;
		art.price = price;
		art.pic_location = pic_location;
		
		
		art.save();
		listAll(art.type);
        //show(id);
	}
	
	public static void deleteRecord(long id) {
		Arts art = Arts.findById(id);
		if(art != null){
			art.isDelete = true;
			art.save();
		}
		
		listAll(art.type);
	}
	
	 
}
