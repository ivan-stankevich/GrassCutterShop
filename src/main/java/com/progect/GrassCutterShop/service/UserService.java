package com.progect.GrassCutterShop.service;


import com.progect.GrassCutterShop.dao.impl.UserDaoImpl;
import com.progect.GrassCutterShop.entity.User;
import com.progect.GrassCutterShop.entity.enums.UserRole;
import com.progect.GrassCutterShop.exception.DaoException;
import com.progect.GrassCutterShop.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.progect.GrassCutterShop.exception.ExceptionMessage.SERVICE_EXCEPTION_MESSAGE;


public class UserService {

    private static UserService instance = new UserService();

    /**
     * Return instance of the {@code UserService}
     *
     * @return instance of the {@code UserService}
     */
    public static UserService getInstance() {
        return instance;
    }

    private UserService() {
    }

    /**
     * Find in database {@code UserData} by user's id
     *
     * @param userId its id of the user which data need to find
     * @return {@code Optional} of {@code UserData} if value present
     * or empty {@code Optional}
     * @throws ServiceException if Dao layer can't do own method
     */
    public User findUserByUserId(Long userId) throws ServiceException {
        User user = null;
        try {
            user = UserDaoImpl.getInstance().findUserByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return user;
    }
    /**
     * Update user's data and payment data
     *
     * @param user        its a {@code User} object which data will be updated
     */
    public void updateUser(User user) throws ServiceException, SQLException {
        UserDaoImpl.getInstance().updateUser(user);
    }

    /**
     * Find user in database by his login and password
     *
     * @param login    its a login value
     * @param password its a encoded password value
     * @return {@code UserWithoutPassword} for saving in {@code HttpSession}
     * @throws ServiceException if Dao layer can't do own method
     */
    public User findUserDTOByLoginAndPassword(String login, String password) throws ServiceException {
        User user;
        try {
            user = UserDaoImpl.getInstance().findUserByLoginAndPassword(login, password);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return user;
    }

    /**
     * Delete user from database
     *
     * @param userId its iser's id which need to delete
     * @return {@code true} if success or {@code false} if failed
     * @throws ServiceException if Dao layer can't do own method
     */
    public boolean deleteUser(Long userId) throws ServiceException {
        boolean result;
        try {
            result = UserDaoImpl.getInstance().deleteUser(userId);
        } catch (SQLException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    /**
     * Save admin or doctor
     *
     * @param firstName its a users firstName
     * @param lastName  its a users LastName
     * @param login     its a login value
     * @param password  its a encoded password value
     * @param userRole  its a admins or doctor's role
     * @return {@code UserWithoutPassword} for saving into {@code HttpSession}
     * @throws ServiceException if Dao layer can't do own method
     */
    public User registrationUser(String firstName, String lastName, String login, String password, UserRole userRole) throws ServiceException {
        User user;
        try {
            user = buildUser(firstName, lastName, login, password, userRole);
            user = UserDaoImpl.getInstance().saveUser(user);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return user;
    }

    /**
     * Find all users in database
     *
     * @return {@code List} of the users
     * @throws ServiceException if Dao layer can't do own method
     */
    public List<User> findAllUsers() throws ServiceException {
        List<User> userList;
        try {
            userList = UserDaoImpl.getInstance().findAllUsers();
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_EXCEPTION_MESSAGE, e);
        }
        return userList;
    }

    private User buildUser(String firstName, String lastName, String login, String password, UserRole userRole) {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .login(login)
                .password(password)
                .role(UserRole.USER_ROLE)
                .build();
    }
}
