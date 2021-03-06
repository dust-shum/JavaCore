package com.dust.demo.easyRpc.pojo;

import java.io.Serializable;

/**
 * @author DUST
 * @description 用户类
 * @date 2022/1/21
 */
public class User implements Serializable {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
