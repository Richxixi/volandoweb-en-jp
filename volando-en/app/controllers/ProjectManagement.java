package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.*;
import org.apache.commons.io.FilenameUtils;


import play.data.Upload;
import play.libs.Crypto;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.With;

@With(Secure.class)
public class ProjectManagement extends Controller {
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
		List<Project> project = Project.find("byIsDelete", false).fetch();
		
		//List<Information> information = Information.findAll();
		renderJSON(project);
    }
	
	public static void list() {
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);


        init();
		List<Project> project = Project.find("byIsDelete", false).fetch();
		render(project);
    }
	

	public static void add() throws FileNotFoundException 
	{
		Project project = new Project();
		project.create_time = new Date();
		project.isDraft = true;
		project.isDelete = false;
		project.save();
        redirect("/ProjectManagement" + "/" + project.getId() + "/editInfo");
    }
	public static void editInfo(long id) throws FileNotFoundException 
	{
		init();
		System.out.println("in editInfo id: "+ id);
		Project project = Project.findById(id);
		System.out.println("in editInfo project.getId(): "+project.getId());
		System.out.println("in editInfo conten: "+ project.content);
        render(project,project.getId());
    }
	
	public static void show(long id) 
	{
		init();
		final Project project = Project.findById(id);
		render( project);
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
	
	
	public static void updateProjectByID(long id,String title,String type,String order_num,
			String content, String start_time, String end_time, String publish_time, String saveAsDraft,String pic_location) throws ParseException, FileNotFoundException
	{
        System.out.println("in updateProjectByID: "+ content);
		final Project project = Project.findById(id);
		project.title = title;
		project.type = type;
		project.order_num = order_num;
		project.content = content;
        project.iconUrl = pic_location;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		try {
			project.start_time = dateFormat.parse(start_time);
			project.end_time = dateFormat.parse(end_time);
			project.publish_time = dateFormat.parse(publish_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
		
		project.update_time = new Date();
		
		if(saveAsDraft.equals("yes")){
			project.isDraft = true;
		}
		else{
			project.isDraft = false;
		}
		
		project.save();
		editInfo(id);
        //show(id);
	}
	
	public static void deleteRecord(long id) {
		Project project = Project.findById(id);
		if(project != null){
			project.isDelete = true;
			project.save();
		}
		
		redirect("/ProjectManagement/list");
	}
	
	 
}
