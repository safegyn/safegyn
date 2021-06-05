package org.safegyn.controller;

import org.safegyn.dto.UserDto;
import org.safegyn.model.error.ApiException;
import org.safegyn.model.form.UserForm;
import org.safegyn.model.form.UserUpdateForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
public class UserController {

    @Autowired
    private UserDto userDto;

    @ApiOperation(value = "Add a new User")
    @RequestMapping(path = "/api/admin/users", method = RequestMethod.POST)
    public Long addUser(@RequestBody UserForm userForm) throws ApiException {
        return userDto.addUser(userForm);
    }

    @ApiOperation(value = "Update a User")
    @RequestMapping(path = "/api/admin/users", method = RequestMethod.PUT)
    public void updateUser(@RequestBody UserUpdateForm userUpdateForm) throws ApiException {
        userDto.updateUser(userUpdateForm);
    }

}