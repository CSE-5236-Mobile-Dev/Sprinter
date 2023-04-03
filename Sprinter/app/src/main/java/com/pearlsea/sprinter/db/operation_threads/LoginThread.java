package com.pearlsea.sprinter.db.operation_threads;

import android.content.Context;
import android.util.Log;

import com.pearlsea.sprinter.db.DatabaseInstanceSingleton;
import com.pearlsea.sprinter.db.SprinterDatabase;
import com.pearlsea.sprinter.db.User;
import com.pearlsea.sprinter.db.UserDao;
import com.pearlsea.sprinter.mvvm.LoginViewModel;



import java.util.List;

public class LoginThread extends Thread{

    //String name;
    String email;
    String password;
    Context context;
    LoginViewModel model;

    public LoginThread(String email, String password, Context context, LoginViewModel model) {
        this.email = email;
        this.password = password;
        this.context = context;
        this.model = model;
    }

    @Override
    public void run() {
        // Initialize the Database
        DatabaseInstanceSingleton dbAccessor = DatabaseInstanceSingleton.getInstance(context);
        SprinterDatabase appDatabase = DatabaseInstanceSingleton.getDatabase();
        UserDao userDao = appDatabase.userDao();

        User checkIfExist = userDao.getUserByEmail(this.email);

        // No User Currently Exists
        if (checkIfExist == null) {
            this.model.setStatus("No User Exists", true);
        }
        else
        {
            DatabaseInstanceSingleton.activeUser = checkIfExist;
            // User already exists - log them in
            this.model.setStatus("Login Success", false);
        }

        Log.d("LoginThread", "DB Operation Completed");



        // Retrieve data from the database
        List<User> usersInDB = userDao.getAll();
    }
}
