package cn.ifhu.bean;


/**
 * @author fuhongliang
 */
public interface UserServiceBean {

    public class LoginForm {
        public long mobile;
        public int code;

        public long getMobile() {
            return mobile;
        }

        public void setMobile(long mobile) {
            this.mobile = mobile;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }


    public class LoginResponse {
        private String token;
        private int exp;
        private String jwt;

        public void setToken(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setExp(int exp) {
            this.exp = exp;
        }

        public int getExp() {
            return exp;
        }

        public String getJwt() {
            return jwt;
        }

        public void setJwt(String jwt) {
            this.jwt = jwt;
        }
    }


    public class ProfileForm {

        public String name;
        public String avatar;
        public String job_title;
        public String business_card;
        public String company;
        public String email;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getJob_title() {
            return job_title;
        }

        public void setJob_title(String job_title) {
            this.job_title = job_title;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getEmail() {
            return email;
        }

        public String getCompany() {
            return company;
        }

        public String getBusiness_card() {
            return business_card;
        }

        public void setBusiness_card(String business_card) {
            this.business_card = business_card;
        }
    }

}
