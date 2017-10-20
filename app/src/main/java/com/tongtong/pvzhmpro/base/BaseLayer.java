package com.tongtong.pvzhmpro.base;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGSize;

/**
 * 图层基类
 * Created by allen on 2017/10/20.
 */

public class BaseLayer extends CCLayer {

    protected CGSize winSize;   //屏幕尺寸

    public BaseLayer() {
        winSize = CCDirector.sharedDirector().getWinSize();
    }

}
