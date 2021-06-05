package org.safegyn.dto;

import org.safegyn.model.error.ApiException;
import org.safegyn.model.form.UserForm;
import org.safegyn.model.form.UserUpdateForm;
import org.safegyn.service.UserService;
import org.safegyn.util.NormalizeUtil;
import org.safegyn.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDto {

    @Autowired
    private UserService userService;

    public Long addUser(UserForm userForm) throws ApiException {
        NormalizeUtil.normalize(userForm);
        ValidationUtil.validate(userForm);
        return userService.addUser(userForm);
    }

    public void updateUser(UserUpdateForm updateForm) throws ApiException {
        NormalizeUtil.normalize(updateForm);
        ValidationUtil.validate(updateForm);
        userService.updateUser(updateForm);
    }

}
