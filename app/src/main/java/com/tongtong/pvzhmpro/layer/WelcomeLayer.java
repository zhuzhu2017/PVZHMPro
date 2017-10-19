package com.tongtong.pvzhmpro.layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;

/**
 * 欢迎页面图层
 * Created by allen on 2017/10/19.
 */

public class WelcomeLayer extends CCLayer {

    private CGSize winSize; //屏幕尺寸

    /*构造函数*/
    public WelcomeLayer() {
        init();
    }

    /*初始化*/
    private void init() {
        //创建Logo精灵
        CCSprite logo = CCSprite.sprite("image/popcap_logo.png");
        //获取屏幕尺寸
        winSize = CCDirector.sharedDirector().getWinSize();
        //设置Logo位于屏幕中间位置
        logo.setPosition(winSize.width / 2, winSize.height / 2);
        //添加到图层
        this.addChild(logo);
    }
}
