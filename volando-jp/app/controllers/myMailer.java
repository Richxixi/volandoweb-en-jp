package controllers;

import com.google.gson.JsonObject;
import com.restfb.FacebookClient;

import play.*;
import play.data.validation.Validation;
import play.libs.MimeTypes;
import play.libs.Mail;
import play.mvc.*;
import play.mvc.Http.Cookie;
import play.mvc.Http.Request;
import play.mvc.Scope.Session;
import play.modules.facebook.FbGraph;
import play.modules.facebook.FbGraphException;
import play.modules.facebook.Parameter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.regex.Pattern;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.mail.EmailException;

import models.*;

public class myMailer extends Mailer{
	public static void replyCustomMail(String name, String email, String title, String content) {
		  String datestring;  
		
	      setFrom("Volendo <service@volendo.com>");
	      setSubject("【客服回覆】 " + title);
	      addRecipient(email);
	     
	      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	      Date date = new Date();
	      datestring = dateFormat.format(date);
	      
	      send(name, email, title, content, datestring);
	}
}