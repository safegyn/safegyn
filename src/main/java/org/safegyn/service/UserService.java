package org.safegyn.service;

import org.safegyn.api.UserApi;
import org.safegyn.db.entity.User;
import org.safegyn.model.error.ApiException;
import org.safegyn.model.form.UserForm;
import org.safegyn.model.form.UserUpdateForm;
import org.safegyn.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = ApiException.class)
public class UserService {

    @Autowired
    private UserApi userApi;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Long addUser(UserForm userForm) throws ApiException {
        User user = ConvertUtil.getEntity(userForm);
        user.setPasswordDigest(passwordEncoder.encode(userForm.getPassword()));
        return userApi.add(user);
    }

    public void updateUser(UserUpdateForm userForm) throws ApiException {
        User user = ConvertUtil.convert(userForm, User.class);
        user.setPasswordDigest(passwordEncoder.encode(userForm.getPassword()));
        userApi.update(user);
    }

}
