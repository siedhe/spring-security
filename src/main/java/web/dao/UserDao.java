package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {

    void addUser(User user);
    void deleteUser(User user);
    void deleteUserById(Long id);
    List<User> getAllUsers();
    void updateUser(User user);
    User readUserById (Long id);
    User getUserByUsername(String username);

}

