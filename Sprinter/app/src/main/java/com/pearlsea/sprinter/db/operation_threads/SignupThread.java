package com.pearlsea.sprinter.db.operation_threads;

import android.content.Context;
import android.util.Log;


import com.pearlsea.sprinter.db.DatabaseInstanceSingleton;
import com.pearlsea.sprinter.db.SprinterDatabase;
import com.pearlsea.sprinter.db.User.User;
import com.pearlsea.sprinter.db.User.UserDao;
import com.pearlsea.sprinter.mvvm.SignupViewModel;

import java.util.List;

public class SignupThread extends Thread{

    String name;
    String email;
    String password;
    Context context;
    SignupViewModel model;

    public SignupThread(String name, String email, String password, Context context, SignupViewModel model) {
        this.name = name;
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
            userDao.insert(new User(this.name, this.email, this.password));
            this.model.setStatus("User Created!", false);
        }
        else
        {
            // User already exists - post error to UI
            this.model.setStatus("Error Duplicate User", true);
        }

        Log.d("SignupThread", "DB Operation Completed");



        // Retrieve data from the database
        List<User> usersInDB = userDao.getAll();
    }
}
