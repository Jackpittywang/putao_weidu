package com.putao.wd.dto;

import java.io.Serializable;

/**
 * 受控Item
 * Created by wango on 2015/12/2.
 */
@Deprecated
public class ControllItem implements Serializable {
    private String name;
    private boolean isSelect;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    @Override
    public String toString() {
        return "EquipmentItem{" +
                "name='" + name + '\'' +
                ", isSelect=" + isSelect +
                '}';
    }
}
