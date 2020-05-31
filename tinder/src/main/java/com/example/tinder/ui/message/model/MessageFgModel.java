package com.example.tinder.ui.message.model;

import com.example.tinder.mvp.BaseModel;

import java.util.List;

public class MessageFgModel extends BaseModel {

    private List<UserNew> new_user_list;

    public List<UserNew> getNew_user_list() {
        return new_user_list;
    }

    public void setNew_user_list(List<UserNew> new_user_list) {
        this.new_user_list = new_user_list;
    }

    public List<UserOld> getOld_user_list() {
        return old_user_list;
    }

    public void setOld_user_list(List<UserOld> old_user_list) {
        this.old_user_list = old_user_list;
    }

    private List<UserOld> old_user_list;

    public class UserNew {
        private int id;
        private String photo;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public class UserOld {
        private int id;
        private String photo;
        private String name;
        private String message;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
