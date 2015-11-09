package jp.ac.it_college.std.jsontest;

import java.util.List;

public class CouponInfo {
    private final String key;
    private final String companyName;
    private final String address;
    private final List<String> category;

    public CouponInfo(String key, String companyName, String address, List<String> category) {
        this.key = key;
        this.companyName = companyName;
        this.address = address;
        this.category = category;
    }

    public String getKey() {
        return key;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getCategory() {
        return category;
    }
}
