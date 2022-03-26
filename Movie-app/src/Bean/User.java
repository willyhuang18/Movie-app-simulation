package Bean;

public class User {
    private String loginName;
    private String userName;
    private String password;
    private char sex;
    private String phone;
    private double money;

    public User() {
    }

    public User(String loginName, String userName, String password, char sex, String phone, double moneyl) {
        this.loginName = loginName;
        this.userName = userName;
        this.password = password;
        this.sex = sex;
        this.phone = phone;
        this.money = moneyl;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
