package com.siddhu.capp.models;

/**
 * Created by siddhu on 4/1/2017.
 */

public class Student {


        private String name;
        private String email;
        private String unique_id;
        private String password;
        private String old_password;
        private String new_password;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String code;

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getUnique_id() {
            return unique_id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setOld_password(String old_password) {
            this.old_password = old_password;
        }

        public void setNew_password(String new_password) {
            this.new_password = new_password;
        }


}
