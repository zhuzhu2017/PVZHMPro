package com.tongtong.pvzhmpro.base;

import com.tongtong.pvzhmpro.base.BaseElement;

/**
 * 植物父类
 *
 * @author Administrator
 */
public abstract class Plant extends BaseElement {

    protected int life = 100;// 生命值

    protected int line;// 行号
    protected int row;// 列号

    public Plant(String filepath) {
        super(filepath);
        setScale(0.65);
        setAnchorPoint(0.5f, 0);//设置初始位置
    }

    /**
     * 被攻击
     *
     * @param attack 被攻击一次消耗的生命值
     */
    public void attacked(int attack) {
        life -= attack;
        if (life <= 0) {
            destroy();
        }
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getLife() {
        return life;
    }


}
