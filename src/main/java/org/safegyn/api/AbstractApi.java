package org.safegyn.api;

import org.safegyn.config.security.UserPrincipal;
import org.safegyn.config.security.UserRole;
import org.safegyn.model.error.ApiException;
import org.safegyn.util.NullAwarePropertyCopy;
import org.safegyn.util.SecurityUtil;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

@Service
public class AbstractApi {

    public void checkNull(Object object, ApiException.Type type, String context, String problem, String solution) throws ApiException {
        if (Objects.nonNull(object))
            throw new ApiException(type, context, problem, solution);
    }

    public void checkNotNull(Object object, ApiException.Type type, String context, String problem, String solution) throws ApiException {
        if (Objects.isNull(object))
            throw new ApiException(type, context, problem, solution);
    }

    public void copyObject(Object sourceObject, Object destinationObject) throws ApiException {
        BeanUtilsBean beanUtil = new NullAwarePropertyCopy();
        try {
            beanUtil.copyProperties(destinationObject, sourceObject);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new ApiException(
                    ApiException.Type.SERVER_ERROR,
                    "Copying Object to Object using NullAwarePropertyCopy",
                    "Failed to copy object due to " + e.getMessage(),
                    "Ensure objects to be copied are compatible");
        }
    }

    public void authorizeUser(UserRole requiredRole) throws ApiException {
        UserPrincipal principal = SecurityUtil.getPrincipal();
        if (!principal.getUserRoles().contains(requiredRole))
            throw new ApiException(
                    ApiException.Type.USER_ERROR,
                    "Authorizing Request",
                    "User Role not present",
                    "Ensure that you have the required authority to take this action"
            );
    }

    public void authorizeUser(Long userId, UserRole requiredRole) throws ApiException {
        UserPrincipal principal = SecurityUtil.getPrincipal();
        if ((Objects.nonNull(requiredRole) && !principal.getUserRoles().contains(requiredRole))
                || !userId.equals(principal.getUserId()))
            throw new ApiException(
                    ApiException.Type.USER_ERROR,
                    "Authorizing Request",
                    "User Role not present",
                    "Ensure that you have the required authority to take this action"
            );
    }

}