    package com.example.lib.exceptions;

    import org.springframework.http.HttpStatus;

    import java.util.List;

    public class ValidationErrorResponse {
        private String field;
        private String message;
        private String errorCode;

        public ValidationErrorResponse(String field, String message, String errorCode) {
            this.field = field;
            this.message = message;
            this.errorCode = errorCode;
        }

        public ValidationErrorResponse() {}

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }
    }
