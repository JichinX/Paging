package me.xujichang.lib.paging.test;

/**
 * @author xujichang on 2020/5/21.
 */
public class TestData {
    private String displayName;

    public TestData(int pIndex) {
        displayName = "This is the Test Data in " + pIndex;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String pDisplayName) {
        displayName = pDisplayName;
    }
}
