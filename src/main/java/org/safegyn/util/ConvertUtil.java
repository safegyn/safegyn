package org.safegyn.util;

import org.safegyn.db.entity.User;
import org.safegyn.model.error.ApiException;
import org.safegyn.model.form.UserForm;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.beanutils.BeanUtils.copyProperties;

public class ConvertUtil {
    public static <T> T convert(Object sourceObject, Class<T> destinationClass) throws ApiException {
        try {
            T destinationObject = destinationClass.getDeclaredConstructor().newInstance();
            copyProperties(destinationObject, sourceObject);
            return destinationObject;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new ApiException(
                    ApiException.Type.SERVER_ERROR,
                    "Attempting conversion of objects by copying properties",
                    "Failed to convert object due to " + e.getMessage(),
                    "Ensure the types being inter-converted are compatible");
        }
    }

    public static <T> List<T> convert(List<?> sourceObjectList, Class<T> destinationClass) throws ApiException {
        List<T> destinationObjectList = new ArrayList<>();
        for (Object sourceObject : sourceObjectList)
            destinationObjectList.add(convert(sourceObject, destinationClass));
        return destinationObjectList;
    }

    public static User getEntity(UserForm userForm) throws ApiException {
        return ConvertUtil.convert(userForm, User.class);
    }

}