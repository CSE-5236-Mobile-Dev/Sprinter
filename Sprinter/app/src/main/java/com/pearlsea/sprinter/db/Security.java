package com.pearlsea.sprinter.db;

import com.pearlsea.sprinter.db.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Security {

    /** fetches user object from the database
    User user = getUserByUsername(name);

    // gets the hash value stored on their object
    String passwordHash = user.getPasswordHash();

    // hashes the submitted password
    String submittedHash = h(submittedPassword);

    if (passwordHash.equals(submittedHash)) {
        // the hashes are the same, the passwords can be assumed to be the same
    }
    else {
        // the hashes are different, so the passwords are definitely different
    }*/
    PreparedStatement statement = conn.prepareStatement("SELECT name, user_pass FROM users WHERE name = '" + name +"';");
    ResultSet result = statement.executeQuery();
    String upass = new String(password.getPassword());
    String user = "";
    String pass = "";
                while(result.next()){
        user = result.getString("username");
        pass = result.getString("user_pass");
    }
                if(username.equals(user) && BCrypt.checkpw(upass, pass)){
        frame.dispose();
        new CommunityCooks();
    }
                else{
        JOptionPane.showMessageDialog(null, "incorrect credentials");
    }
}


