package controllers;

import models.*;
import play.mvc.Controller;

import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

/**
 * Created by apple on 2014/10/6.
 */
public class CRMViewer extends Controller {
    public static void showRecords() {
        List<CRM_record> records = CRM_record.findAll();

        System.out.println(records.size());
        render(records);
    }

    public static void exportRecords() {
        try {
            List<CRM_record> records = CRM_record.findAll();
            FileWriter writer = new FileWriter("file.csv");
            writer.append("id,email,phone,site");
            writer.append('\n');
            for (int i = 0; i < records.size(); i++) {

                CRM_record record = records.get(i);
                writer.append(record.getId().toString());
                writer.append(',');
                writer.append(record.email);
                writer.append(',');
                writer.append(record.phone);
                writer.append(',');
                writer.append(record.site);
                writer.append(',');

                writer.append('\n');


            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File f = new File("file.csv");
        renderBinary(f);
    }

    public static void showmember() {
        List<User> users = User.findAll();


        render(users);
    }

    public static void exportmemberRecords() {
        try {
            List<User> records = User.findAll();
            FileWriter writer = new FileWriter("file.csv");
            writer.append("id,email,phone,name,address,username");
            writer.append('\n');
            for (int i = 0; i < records.size(); i++) {

                User user = records.get(i);
                writer.append(user.getId().toString());
                writer.append(',');
                writer.append(user.email);
                writer.append(',');
                writer.append(user.phone);
                writer.append(',');
                writer.append(user.name);
                writer.append(',');
                writer.append(user.address);
                writer.append(',');
                writer.append(user.username);
                writer.append(',');
                writer.append('\n');
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File f = new File("file.csv");
        renderBinary(f);
    }

}
