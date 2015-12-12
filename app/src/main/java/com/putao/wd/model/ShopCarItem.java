package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wango on 2015/12/12.
 */
public class ShopCarItem implements Serializable {
    private List<Cart> use;//
    private List<Cart> useless;//


    public List<Cart> getUse() {
        return use;
    }

    public void setUse(List<Cart> use) {
        this.use = use;
    }

    public List<Cart> getUseless() {
        return useless;
    }

    public void setUseless(List<Cart> useless) {
        this.useless = useless;
    }


    @Override
    public String toString() {
        return "Cart{" +
                "use='" + use + '\'' +
                "useless='" + useless + '\'' +
                '}';
    }
}
