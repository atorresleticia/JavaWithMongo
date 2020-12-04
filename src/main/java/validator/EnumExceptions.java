package validator;

import java.text.MessageFormat;

public enum EnumExceptions {

    FIELD_IS_MANDATORY("The field {0} is mandatory."),
    DUPLICATE_OBJECT_FOUND("Object of same type as {0} was found with the same {1}: {2}."),
    ;


    private String message;

    private EnumExceptions(String message) {
        this.message = message;
    }

    public String getMessage(Object ... args) {
        return MessageFormat.format(message, args);
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
