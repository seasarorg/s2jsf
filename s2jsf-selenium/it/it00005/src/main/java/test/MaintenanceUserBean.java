package test;

import java.util.ArrayList;
import java.util.List;

/**
 * テスト処理
 */
public class MaintenanceUserBean {

    private TestForm testForm = null;
    private Integer selectedUserID = null;

    //テストデータ
    private final int[] userIDs = { 1000, 1001, 1002, 1003, 1004 };
    private final String[] userNames = { "john T carter", "mark green",
        "abee rockheart", "kelly weever", "susan ruice" };

    /**
     * 一覧取得
     * @return 一覧
     */
    public List getUserList() {

        List userList = new ArrayList(5);
        for (int i = 0; i < userIDs.length; i++) {
            userList
                .add(new UserDataDTO(new Integer(userIDs[i]), userNames[i]));
        }
        return userList;
    }

    /**
     * 選択
     * @return 処理結果
     */
    public String select() {

        System.out.println("select.selectedUserID=" + selectedUserID);
        for (int i = 0; i < userIDs.length; i++) {
            if (userIDs[i] == selectedUserID.intValue()) {
                testForm.setUserID(new Integer(userIDs[i]));
                testForm.setUserName(userNames[i]);
                break;
            }
        }

        return null;
    }

    public void setTestForm(TestForm aTestForm) {
        testForm = aTestForm;
    }

    public void setSelectedUserID(Integer aSelectedUserID) {
        selectedUserID = aSelectedUserID;
    }

}
