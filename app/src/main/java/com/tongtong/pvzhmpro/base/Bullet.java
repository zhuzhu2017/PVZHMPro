package com.tongtong.pvzhmpro.base;

/**
 * 子弹对象
 */
public abstract class Bullet extends Product {
    protected int attack = 20;// 攻击力
    protected int speed = 100;// 飞行速度

    public Bullet(String filepath) {
        super(filepath);
    }

    @Override
    public void baseAction() {

    }

    /**
     * 移动
     */
    public abstract void move();

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

}
