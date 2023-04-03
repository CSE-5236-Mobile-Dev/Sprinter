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
            userDao.insert(new User(this.email, this.password));
            this.model.setStatus("User Existed!", false);
        }
        else
        {
            // User already exists - post error to UI
            this.model.setStatus("Error Duplicate User", true);
        }

        Log.d("LoginThread", "DB Operation Completed");



        // Retrieve data from the database
        List<User> usersInDB = userDao.getAll();
    }
}
