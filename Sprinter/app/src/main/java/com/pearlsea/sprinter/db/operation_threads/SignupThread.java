package com.pearlsea.sprinter.db.operation_threads;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.pearlsea.sprinter.SignupFragment;
import com.pearlsea.sprinter.db.DatabaseInstanceSingleton;
import com.pearlsea.sprinter.db.SprinterDatabase;
import com.pearlsea.sprinter.db.User;
import com.pearlsea.sprinter.db.UserDao;

import java.util.List;

public class SignupThread extends Thread{

    String name;
    String email;
    String password;
    Context context;
    TextView status;

    public SignupThread(String name, String email, String password, Context context, TextView status) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.context = context;
        this.status = status;
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
            this.status.setText("User Created!");
            for (User u : userDao.getAll())
            {
                Log.d("SignupThread", u.toString());
            }
        }
        else
        {
            // User already exists - post error to UI
            this.status.setText("Error Duplicate User");
        }

        Log.d("SignupThread", "DB Operation Completed");



        // Retrieve data from the database
        List<User> usersInDB = userDao.getAll();
    }
}
