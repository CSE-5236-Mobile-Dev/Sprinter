package com.pearlsea.sprinter.db.operation_threads;

import android.content.Context;

import com.pearlsea.sprinter.db.DatabaseInstanceSingleton;
import com.pearlsea.sprinter.db.SprinterDatabase;
import com.pearlsea.sprinter.db.User;
import com.pearlsea.sprinter.db.UserDao;
import com.pearlsea.sprinter.mvvm.MetricsViewModel;

public class UpdateMetricsThread extends Thread{
    String email;
    int height;
    int weight;
    int age;
    char gender;
    Context context;
    MetricsViewModel model;

    public UpdateMetricsThread(String email, int height, int weight, int age, String gender, Context context, MetricsViewModel model) {
        this.email = email;
        this.height = height;
        this.weight = weight;
        this.age = age;

        if (gender.equals("male")) {
            this.gender = 'm';
        } else if (gender.equals("female")) {
            this.gender = 'f';
        } else {
            this.gender = 'o';
        }

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

        if (checkIfExist == null) {
            model.setStatus("Error", true);
        } else {
            checkIfExist.updateUser(this.gender, this.age, this.weight, this.height);
            userDao.update(checkIfExist);
            model.setStatus("completed", false);
        }
    }
}
