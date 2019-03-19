package cn.ifhu.bean;


/**
 * @author fuhongliang
 */
public interface UserServiceBean {

    public class LoginForm {
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


        /**
         * code : 200
         * data : {"store_id":3,"store_name":"张三的店","store_avatar":"06058037468017940_sm.jpg","area_info":"河南 南阳市 邓州市","store_address":"刘集乡户周村","store_workingtime":"33444444","store_phone":"123","store_state":1,"store_description":"店铺描述","business_licence_number_electronic":"06058035285403321.jpg","token":"02b3f7edf50cfbbb92ac0d71f63d2890"}
         * msg : 获取成功
         */

        private int code;
        private DataBean data;
        private String msg;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public static class DataBean {
            /**
             * store_id : 3
             * store_name : 张三的店
             * store_avatar : 06058037468017940_sm.jpg
             * area_info : 河南 南阳市 邓州市
             * store_address : 刘集乡户周村
             * store_workingtime : 33444444
             * store_phone : 123
             * store_state : 1
             * store_description : 店铺描述
             * business_licence_number_electronic : 06058035285403321.jpg
             * token : 02b3f7edf50cfbbb92ac0d71f63d2890
             */

            private int store_id;
            private String store_name;
            private String store_avatar;
            private String area_info;
            private String store_address;
            private String store_workingtime;
            private String store_phone;
            private int store_state;
            private String store_description;
            private String business_licence_number_electronic;
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

            public String getStore_phone() {
                return store_phone;
            }

            public void setStore_phone(String store_phone) {
                this.store_phone = store_phone;
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

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }
        }
    }

}
