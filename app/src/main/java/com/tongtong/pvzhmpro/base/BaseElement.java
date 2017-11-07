package com.tongtong.pvzhmpro.base;

import org.cocos2d.nodes.CCSprite;


/**
 *  游戏开始后所有子Sprite的父类
 * @author Administrator
 */
public abstract class BaseElement extends CCSprite {
    public interface DieListener {
        void die();
    }

    private DieListener dieListener;

    public void setDieListener(DieListener dieListener) {  // ��©��һ������
        this.dieListener = dieListener;
    }

    public BaseElement(String filepath) {
        super(filepath);
    }


    public abstract void baseAction();


    public void destroy() {
        if (dieListener != null) {
            dieListener.die();
        }
        this.removeSelf();
    }
}
