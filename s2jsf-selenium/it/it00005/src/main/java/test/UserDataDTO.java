package test;

public class UserDataDTO {

    private Integer userID;
    private String userName;

    public UserDataDTO(Integer aUserID, String aUserName) {
        userID = aUserID;
        userName = aUserName;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer aUserID) {
        userID = aUserID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String aUserName) {
        userName = aUserName;
    }

}
