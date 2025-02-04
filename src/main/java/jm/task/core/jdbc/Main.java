package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;



public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Алёна", "Пава", (byte) 25);
//        userService.saveUser("Cвета", "Светлова", (byte) 23);
//        userService.saveUser("Нюра", "Светлова", (byte) 34);
//        System.out.println(userService.getAllUsers());
//        userService.removeUserById(2);
//        System.out.println(userService.getAllUsers());
//        userService.dropUsersTable();
//        System.out.println(userService.getAllUsers());
    }
}
