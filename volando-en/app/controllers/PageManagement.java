package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;


import models.*;

import play.data.Upload;
import play.libs.Crypto;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.With;

@With(Secure.class)
public class PageManagement extends Controller {
	 public static void insertCRM(String siteName)
    {
        User user = User.find("byEmail", session.get("email")).first();
        if (user != null)
        {
            CRM_record crm = new CRM_record();
            crm.email = user.email;
            crm.phone=user.phone;
            crm.site = siteName;
            crm.save();
        }
    }
	private static void init(){
		 User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

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
	
	public static void editInfo(String type) throws FileNotFoundException 
	{
		init();
		Page page = Page.find("byType", type).first();
		
		if(page == null){
			Page newPage = new Page();
			newPage.type = type;
			newPage.create_time = new Date();
			newPage.update_time = new Date();
			newPage.save();
			
			page = newPage;
		}
		
        render(type, page,page.getId());
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
	
	
	public static void updatePageByType(String type, String content) throws ParseException, FileNotFoundException 
	{
		Page page = Page.find("byType", type).first();
		

		page.content = content;
		page.update_time = new Date();

		page.save();
		
		
		editInfo(type);
        //show(id);
	}
	
	public static void editLifeTime(){
		Life life = Life.find("byType", "fixed").first();
		if(life == null){
			Life lifeNew = new Life();
			lifeNew.lifetime1 = "09：00";
			lifeNew.lifetime2 = "16：00";
			lifeNew.lifetime3 = "09：50";
			lifeNew.lifetime4 = "16：30";
			lifeNew.lifetime5 = "15：30";
			lifeNew.lifetime6 = "18：30";
			lifeNew.lifetime7 = "18：20";
			lifeNew.type = "fixed";
			lifeNew.save();
			
			life = lifeNew;
		}
		
		render(life);
	}
	
	public static void editLifeTimeForm(String time1, String time2,String time3,String time4,
			String time5, String time6, String time7){
		
		Life life = Life.find("byType", "fixed").first();
		life.lifetime1 = time1;
		life.lifetime2 = time2;
		life.lifetime3 = time3;
		life.lifetime4 = time4;
		life.lifetime5 = time5;
		life.lifetime6 = time6;
		life.lifetime7 = time7;
		life.save();
		
		editLifeTime();
	}
	
	 
}
