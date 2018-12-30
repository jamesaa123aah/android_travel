package com.buu.app.travel.tools;

import com.buu.app.travel.Cfg;
import com.buu.app.travel.role.User;
import org.litepal.crud.DataSupport;
import java.util.List;
/**
 * Created by king- on 2017/8/18.
 */

public class UpdateUDB {
    public static void Update(String account,String password){
            List<User> users = DataSupport.findAll(User.class);
            User u = null;
            for (User u1:users) {
                if(u1.getAcId()== Cfg.ID){
                    u = u1;
                    break;
                }
            }
            if (u == null) {
                User user = new User();
                user.setAcId(Cfg.ID);
                user.setLastTime(System.currentTimeMillis());
                user.setAutoLogin(Cfg.AUTO_LOGIN);
                user.setAccount(account);
                user.setPassword(password);
                user.save();
                int x = DataSupport.count("User");
                if(x>4){
                    DataSupport.delete(User.class,DataSupport.findFirst(User.class).getId());
                }
            } else {
                DataSupport.delete(User.class,DataSupport.findLast(User.class).getId());
                User user = new User();
                user.setAcId(u.getId());
                user.setLastTime(System.currentTimeMillis());
                user.setAutoLogin(u.isAutoLogin());
                user.setAccount(u.getAccount());
                user.setPassword(u.getPassword());
                user.save();
            }
    }
}
