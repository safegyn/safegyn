package org.safegyn.util;

import org.safegyn.model.error.ApiException;
import org.safegyn.model.form.QuestionForm;
import org.safegyn.model.form.QuestionUpdateForm;
import org.safegyn.model.form.UserForm;
import org.safegyn.model.form.UserUpdateForm;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ValidationUtil {

    public static <T> void checkValid(T form) throws ApiException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(form);
        for (ConstraintViolation<T> violation : violations)
            throw new ApiException(
                    ApiException.Type.USER_ERROR,
                    "Validating Form Constraints",
                    "Input Constraint Validation Failed",
                    "Constraint Violation: " + violation.getMessage());
    }

    public static <T> void checkValid(Collection<T> formList) throws ApiException {
        for (T form : formList)
            checkValid(form);
    }

    public static void validate(QuestionForm form) throws ApiException {
        checkValid(form);
    }

    public static void validate(UserForm userForm) throws ApiException {
        checkValid(userForm);
        validatePassword(userForm.getPassword());
    }

    public static void validate(UserUpdateForm updateForm) throws ApiException {
        checkValid(updateForm);
        if (Objects.nonNull(updateForm.getPassword())) validatePassword(updateForm.getPassword());
    }

    private static void validatePassword(String password) throws ApiException {
        if (password.length() < 6)
            throw new ApiException(
                    ApiException.Type.USER_ERROR,
                    "Validating Password Input",
                    "Password not as per required format",
                    "Use a password with 6 or more characters");
    }

    public static void validate(QuestionUpdateForm updateForm) throws ApiException {
        if (Objects.nonNull(updateForm.getTitle()) && (updateForm.getTitle().length() < 3 || updateForm.getTitle().length() > 255))
            throw new ApiException(
                    ApiException.Type.USER_ERROR,
                    "Validating question update form",
                    "Question text size is not in range [3,255]",
                    "Text for a question cannot be less than 3 characters or more than 255 characters");
    }

    public static void validateAddReviewMap(Map<String, String> reviewMap) throws ApiException {
        if (reviewMap.containsKey(null))
            throw new ApiException(ApiException.Type.USER_ERROR,
                    "Adding review", "null key present in input", "No question's title may be blank");

        if (!reviewMap.containsKey("NAME"))
            throw new ApiException(ApiException.Type.USER_ERROR,
                    "Adding review", "'name' field not found", "'name' of doctor must be present");

        if (!reviewMap.containsKey("CITY"))
            throw new ApiException(ApiException.Type.USER_ERROR,
                    "Adding review", "'city' field not found", "'city' of doctor must be present");
    }

}