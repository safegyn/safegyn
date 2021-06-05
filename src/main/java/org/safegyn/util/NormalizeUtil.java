package org.safegyn.util;

import org.safegyn.model.form.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NormalizeUtil {

    public static void normalize(UserForm userForm) {
        if (Objects.nonNull(userForm.getUsername()))
            userForm.setUsername(userForm.getUsername().trim());

        if (Objects.nonNull(userForm.getPassword()))
            userForm.setPassword(userForm.getPassword().trim());

        if (Objects.nonNull(userForm.getName()))
            userForm.setName(userForm.getName().trim());
        else userForm.setName(userForm.getUsername());
    }

    public static void normalize(UserUpdateForm updateForm) {
        if (Objects.nonNull(updateForm.getUsername()))
            updateForm.setUsername(updateForm.getUsername().trim());

        if (Objects.nonNull(updateForm.getPassword()))
            updateForm.setPassword(updateForm.getPassword().trim());

        if (Objects.nonNull(updateForm.getName()))
            updateForm.setName(updateForm.getName().trim());
    }

    public static void normalize(QuestionForm form) {
        form.setTitle(form.getTitle().trim().toUpperCase());
    }

    public static void normalize(QuestionUpdateForm updateForm) {
        if (Objects.nonNull(updateForm.getTitle()))
            updateForm.setTitle(updateForm.getTitle().trim().toUpperCase());
    }

    public static Map<String, String> normalizeReviewMap(Map<String, String> reviewMap) {
        Map<String, String> normalizedMap = new HashMap<>();
        for (Map.Entry<String, String> entry : reviewMap.entrySet()) {
            if (Objects.isNull(entry.getKey())) break;
            normalizedMap.put(entry.getKey().trim().toUpperCase(), entry.getValue().trim().toUpperCase());
        }

        return normalizedMap;
    }
}