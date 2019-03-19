package cn.ifhu.bean;


/**
 * @author fuhongliang
 */
public interface UserServiceBean {

    class LoginForm {
        public String member_name;
        public String member_passwd;

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public String getMember_passwd() {
            return member_passwd;
        }

        public void setMember_passwd(String member_passwd) {
            this.member_passwd = member_passwd;
        }
    }


    class LoginResponse {


        private int store_id;
        private String store_name;
        private String store_avatar;
        private String area_info;
        private String store_address;
        private String store_workingtime;
        private int store_state;
        private String store_description;
        private String business_licence_number_electronic;
        private int member_id;
        private String member_mobile;
        private String token;

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getStore_avatar() {
            return store_avatar;
        }

        public void setStore_avatar(String store_avatar) {
            this.store_avatar = store_avatar;
        }

        public String getArea_info() {
            return area_info;
        }

        public void setArea_info(String area_info) {
            this.area_info = area_info;
        }

        public String getStore_address() {
            return store_address;
        }

        public void setStore_address(String store_address) {
            this.store_address = store_address;
        }

        public String getStore_workingtime() {
            return store_workingtime;
        }

        public void setStore_workingtime(String store_workingtime) {
            this.store_workingtime = store_workingtime;
        }

        public int getStore_state() {
            return store_state;
        }

        public void setStore_state(int store_state) {
            this.store_state = store_state;
        }

        public String getStore_description() {
            return store_description;
        }

        public void setStore_description(String store_description) {
            this.store_description = store_description;
        }

        public String getBusiness_licence_number_electronic() {
            return business_licence_number_electronic;
        }

        public void setBusiness_licence_number_electronic(String business_licence_number_electronic) {
            this.business_licence_number_electronic = business_licence_number_electronic;
        }

        public int getMember_id() {
            return member_id;
        }

        public void setMember_id(int member_id) {
            this.member_id = member_id;
        }

        public String getMember_mobile() {
            return member_mobile;
        }

        public void setMember_mobile(String member_mobile) {
            this.member_mobile = member_mobile;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

}
