package com.pearlsea.sprinter.db.operation_threads;

import android.content.Context;

import com.pearlsea.sprinter.RunActivity;
import com.pearlsea.sprinter.db.DatabaseInstanceSingleton;
import com.pearlsea.sprinter.db.SprinterDatabase;
import com.pearlsea.sprinter.db.User;
import com.pearlsea.sprinter.db.UserDao;

public class DeleteUserThread extends Thread {

    User user;
    Context context;
    RunActivity activity;

    public DeleteUserThread(User user, Context context, RunActivity activity) {
        this.user = user;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void run() {
        // Initialize the Database
        DatabaseInstanceSingleton dbAccessor = DatabaseInstanceSingleton.getInstance(context);
        SprinterDatabase appDatabase = DatabaseInstanceSingleton.getDatabase();
        UserDao userDao = appDatabase.userDao();

        User toDelete = DatabaseInstanceSingleton.activeUser;

        if (toDelete != null) {
            // Delete User Account
            userDao.delete(toDelete);

            // Go back to the welcome screen
            activity.transitionToWelcomeScreen();
        }

        // Else - This shouldn't occur, but do nothing
    }
}
