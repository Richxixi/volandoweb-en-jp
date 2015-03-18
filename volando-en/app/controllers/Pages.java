package controllers;

import play.*;
import play.data.Upload;
import play.modules.facebook.FbGraph;
import play.modules.facebook.FbGraphException;
import play.mvc.*;
import play.mvc.Scope.Session;

import java.io.File;
import java.util.*;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.mapping.Array;

import com.google.gson.JsonObject;

import models.*;

public class Pages extends Controller {

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
    public static void about() {
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

        int template_type = 11;
    	
    	render(template_type);
    }
   
    public static void about_page(String category) {
    	int template_type = 3;
        User user = User.find("byEmail", session.get("email")).first();
        if (user==null)
            CRMHandler.insertCRM(Http.Request.current().url,"","");
        else
            CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

        Life life = Life.find("byType", "fixed").first();
		render(category, template_type, life);
    		
    	
    	
    	
    }
    
   public static void meeting(){
	   int template_type = 9;
       User user = User.find("byEmail", session.get("email")).first();
       if (user==null)
           CRMHandler.insertCRM(Http.Request.current().url,"","");
       else
           CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

       render(template_type);
   }
   
   public static void meeting_page(String category){
	   int template_type = 3;
       User user = User.find("byEmail", session.get("email")).first();
       if (user==null)
           CRMHandler.insertCRM(Http.Request.current().url,"","");
       else
           CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

	   Page page = Page.find("byType", category).first();
	   
	   render(page, category, template_type);
   }
   
   public static void spring(){
	   int template_type = 10;
       User user = User.find("byEmail", session.get("email")).first();
       if (user==null)
           CRMHandler.insertCRM(Http.Request.current().url,"","");
       else
           CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

       render(template_type);
   }
   
   public static void spring_page(String category){
	   int template_type = 3;
       User user = User.find("byEmail", session.get("email")).first();
       if (user==null)
           CRMHandler.insertCRM(Http.Request.current().url,"","");
       else
           CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);


       render( category, template_type);
   }
    
   public static void catering(){
	   int template_type = 4;
       User user = User.find("byEmail", session.get("email")).first();
       if (user==null)
           CRMHandler.insertCRM(Http.Request.current().url,"","");
       else
           CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

       render(template_type);
   }
   
   public static void catering_page(String category){
	   int template_type = 3;
       User user = User.find("byEmail", session.get("email")).first();
       if (user==null)
           CRMHandler.insertCRM(Http.Request.current().url,"","");
       else
           CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

       Page page = Page.find("byType", category).first();
			   
	   render(page, category, template_type);
   }
   
   public static void spa(){
	   int template_type = 6;
       User user = User.find("byEmail", session.get("email")).first();
       if (user==null)
           CRMHandler.insertCRM(Http.Request.current().url,"","");
       else
           CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

       render(template_type);
   }
   
   public static void spa_page(String category){
	   int template_type = 3;
       User user = User.find("byEmail", session.get("email")).first();
       if (user==null)
           CRMHandler.insertCRM(Http.Request.current().url,"","");
       else
           CRMHandler.insertCRM(Http.Request.current().url,user.email,user.phone);

       Page page = Page.find("byType", category).first();
		
	   render(page, category, template_type);
   }
}