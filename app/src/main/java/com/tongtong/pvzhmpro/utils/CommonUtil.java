package com.tongtong.pvzhmpro.utils;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.transitions.CCFlipXTransition;

/**
 * Created by allen on 2017/10/20.
 */

public class CommonUtil {

    /**
     * 打开新的图层
     *
     * @param newLayer 新图层
     */
    public static void changeLayer(CCLayer newLayer) {
        //获取导演对象
        CCDirector ccDirector = CCDirector.sharedDirector();
        //创建场景
        CCScene scene = CCScene.node();
        scene.addChild(newLayer);
        //添加转场动画，参数1：时间，参数2：场景，参数3：Flip开始方向
        CCFlipXTransition transition = CCFlipXTransition.transition(1, scene, 1);
        ccDirector.replaceScene(transition);
    }

}
