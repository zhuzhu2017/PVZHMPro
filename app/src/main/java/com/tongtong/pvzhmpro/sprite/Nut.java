package com.tongtong.pvzhmpro.sprite;

import com.tongtong.pvzhmpro.base.DefancePlant;
import com.tongtong.pvzhmpro.utils.CommonUtil;

import org.cocos2d.actions.base.CCAction;

/**
 * Created by allen on 2017/11/7.
 */

public class Nut extends DefancePlant {
    public Nut() {
        super("image/plant/nut/p_3_01.png");
        baseAction();
    }

    @Override
    public void baseAction() {
        CCAction animate = CommonUtil.getAnimate("image/plant/nut/p_3_%02d.png", 11, true);
        this.runAction(animate);
    }
}
