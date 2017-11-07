package com.tongtong.pvzhmpro.base;

/**
 * 跳动的植物
 *
 * @author Administrator
 */
public abstract class DefancePlant extends Plant {

    public DefancePlant(String filepath) {
        super(filepath);
        life = 200;
    }

}
