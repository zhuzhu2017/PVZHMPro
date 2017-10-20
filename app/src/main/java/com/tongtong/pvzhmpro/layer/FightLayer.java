package com.tongtong.pvzhmpro.layer;

import com.tongtong.pvzhmpro.base.BaseLayer;

import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.types.CGSize;

/**
 * 对战页面
 * Created by allen on 2017/10/20.
 */

public class FightLayer extends BaseLayer {

    private CCTMXTiledMap map;

    public FightLayer() {
        init();
    }

    private void init() {
        loadMap();
        moveMap();
    }

    /**
     * 加载地图
     */
    private void loadMap() {
        //创建地图对象
        map = CCTMXTiledMap.tiledMap("image/fight/map_day.tmx");
        //设置锚点
        map.setAnchorPoint(0.5f, 0.5f);
        CGSize contentSize = map.getContentSize();
        //设置位置
        map.setPosition(contentSize.width / 2, contentSize.height / 2);
        this.addChild(map);
    }

    /**
     * 移动地图
     */
    private void moveMap() {
        int x = (int) (winSize.width - map.getContentSize().width);
        CCMoveBy ccMoveBy = CCMoveBy.action(2, ccp(x, 0));
        CCSequence ccSequence = CCSequence.actions(CCDelayTime.action(4), ccMoveBy);
        map.runAction(ccSequence);
    }

}
