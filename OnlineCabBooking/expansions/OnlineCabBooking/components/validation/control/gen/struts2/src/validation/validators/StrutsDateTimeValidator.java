package validation.validators;

// expanded with nsx-expanders:5.12.1, expansionResource net.democritus:Expanders:5.12.1

import static net.democritus.validation.ValidationHelper.getValueFromObject;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

import com.opensymphony.xwork2.ActionSupport;

import net.democritus.validation.ValidationResult;
import net.democritus.validation.Result;
import net.democritus.validation.IFieldValidator;

public class StrutsDateTimeValidator extends FieldValidatorSupport {

    private final IFieldValidator fieldValidator = new net.democritus.valuetype.basic.DateTimeValidator();

    public void validate(Object enterer) throws ValidationException {
        String fieldName = getFieldName();
        Result result = getValueFromObject(enterer, getFieldName());
        if (result.isError()) {
            throw new ValidationException("field: " + getFieldName() + " message: " + result.getMessage());
        }

        ValidationResult validationResult = fieldValidator.validate(result.getValue());
        if (validationResult.isError()) {
            String[] parts = fieldName.split("\\.");
            String formFieldName = parts[0] + "Details." + parts[1];

            ActionSupport actionSupport = (ActionSupport) enterer;
            actionSupport.addFieldError(formFieldName, validationResult.getMessage());
        }
    }

}
