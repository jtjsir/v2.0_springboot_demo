package com.example.demo.web.profile.model;

/**
 * @author nanco
 * -------------
 * -------------
 * @create 18/9/9
 */
public class ResEntity {
    private Boolean success = Boolean.TRUE;

    private Object data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static class BindExRes {
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
