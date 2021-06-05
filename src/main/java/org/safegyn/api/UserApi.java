package org.safegyn.api;

import org.safegyn.config.security.UserRole;
import org.safegyn.db.dao.UserDao;
import org.safegyn.db.entity.User;
import org.safegyn.model.error.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserApi extends AbstractApi {

    @Autowired
    private UserDao dao;

    public Long add(User user) throws ApiException {
        checkUsernameNotExists(user.getUsername());
        dao.persist(user);
        return user.getUserId();
    }

    public User getCheck(Long id) throws ApiException {
        User user = dao.select(id);
        checkNotNull(user,
                ApiException.Type.USER_ERROR,
                "Fetching User by user ID",
                "User does not exist with user ID: " + id,
                "Invalid User ID");
        return user;
    }

    public User getCheckByUsername(String username) throws ApiException {
        User user = dao.select("username", username);
        checkNotNull(user,
                ApiException.Type.USER_ERROR,
                "Fetching user data by username",
                "User does not exist with user name: " + username,
                "Provide registered username");
        return user;
    }

    private void checkUsernameNotExists(String username) throws ApiException {
        checkNull(dao.select("username", username),
                ApiException.Type.USER_ERROR,
                "Checking availability of username",
                "User already exists with username: " + username,
                "Try using a different username");
    }

    public List<User> getByRole(UserRole role) {
        return dao.selectMultiple("userRole", role);
    }

    public void update(User userUpdate) throws ApiException {
        authorizeUser(userUpdate.getUserId(), null);

        User fetchedUser = getCheck(userUpdate.getUserId());
        validateUserUpdate(fetchedUser, userUpdate);
        checkForDuplication(userUpdate, fetchedUser);

        copyObject(userUpdate, fetchedUser);
    }

    private void checkForDuplication(User userUpdate, User fetchedUser) throws ApiException {
        checkForUsernameDuplication(userUpdate, fetchedUser);
    }

    private void checkForUsernameDuplication(User userUpdate, User fetchedUser) throws ApiException {
        if (Objects.nonNull(userUpdate.getUsername())) {
            User byUsername = dao.select("username", (userUpdate.getUsername()));
            if (Objects.nonNull(byUsername) && !byUsername.getUserId().equals(fetchedUser.getUserId()))
                throw new ApiException(
                        ApiException.Type.USER_ERROR,
                        "Updating User",
                        "Invalid Update attempted due to duplication",
                        "Username is already in use, please use a different value"
                );
        }
    }

    private void validateUserUpdate(User fetchedUser, User userUpdate) throws ApiException {
        if (!userUpdate.getUserRole().equals(fetchedUser.getUserRole()))
            throw new ApiException(
                    ApiException.Type.USER_ERROR,
                    "Updating User",
                    "Invalid Update attempted",
                    "Please provide only the required fields for updating User"
            );
    }
}


