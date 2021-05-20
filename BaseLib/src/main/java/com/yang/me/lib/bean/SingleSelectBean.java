package com.yang.me.lib.bean;

public class SingleSelectBean extends BaseBean {
    private String itemName;
    private boolean isDefaultSelect;

    public SingleSelectBean() {
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isDefaultSelect() {
        return this.isDefaultSelect;
    }

    public void setDefaultSelect(boolean defaultSelect) {
        this.isDefaultSelect = defaultSelect;
    }
}
