package org.safegyn.util;

import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.InvocationTargetException;

public class NullAwarePropertyCopy extends BeanUtilsBean {

    @Override
    public void copyProperty(Object dest, String name, Object value) throws IllegalAccessException, InvocationTargetException {
        if (value == null) return;
        if (value.toString().isEmpty()) value = null;
        super.copyProperty(dest, name, value);
    }
}