package com.tongtong.pvzhmpro.bean;

import com.tongtong.pvzhmpro.utils.CommonUtil;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.nodes.CCSprite;

/**
 * 僵尸对象
 * Created by allen on 2017/10/20.
 */

public class ShowZombies extends CCSprite {

    public ShowZombies() {
        super("image/zombies/zombies_1/shake/z_1_01.png");
        //设置缩放
        setScale(0.5f);
        //设置锚点
        setAnchorPoint(0.5f, 0);
        //播放序列帧动画
        CCAction animate = CommonUtil.getAnimate("image/zombies/zombies_1/shake/z_1_%02d.png", 2, true);
        runAction(animate);
    }

}
