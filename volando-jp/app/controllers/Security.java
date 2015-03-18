package controllers;

import models.User;

public class Security extends Secure.Security {
    
    static boolean authenticate(String email, String password) {
        return User.connect(email, password) != null;
    }

    static boolean check(String profile) {
        System.out.println("connected()=>" + session.get("email"));
        User user = User.find("byEmail", session.get("email")).first();
        if (user != null) {
            if ("isAdmin".equals(profile)) {
                return user.isAdmin;
            } else if ("isManager".equals(profile)) {
                return user.isManager;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    static void onDisconnected() {
        System.out.println("logout");
        Application.index();
    }

    static void onAuthenticated() {
        // Reports.upcoming();
        String username = session.get("username");
        session.put("email", username);
        User user = User.find("byUsername", username).first();
        if (user != null) {
            long userId = (Long) user.getId();
            session.put("userId", userId);
            session.put("username", user.username);
        }
        System.out.println(flash.get("url"));
        if (flash.get("url") == null) {
            redirect("/");
        } else {
            String url = flash.get("url");
            redirect(url);
        }
    }
    
}