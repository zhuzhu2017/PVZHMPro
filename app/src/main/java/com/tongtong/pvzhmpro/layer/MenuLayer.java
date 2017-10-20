package com.tongtong.pvzhmpro.layer;

import android.util.Log;

import com.tongtong.pvzhmpro.base.BaseLayer;
import com.tongtong.pvzhmpro.utils.CommonUtil;

import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCSprite;

/**
 * 菜单图层
 * Created by allen on 2017/10/20.
 */

public class MenuLayer extends BaseLayer {
    public MenuLayer() {
        init();
    }

    private void init() {
        //创建精灵
        CCSprite sprite = CCSprite.sprite("image/menu/main_menu_bg.jpg");
        //设置锚点
        sprite.setAnchorPoint(0, 0);
        //添加到图层
        this.addChild(sprite);
        /*添加菜单到该图层*/
        CCSprite normalSprite = CCSprite.sprite("image/menu/start_adventure_default.png");
        CCSprite selectedSprite = CCSprite.sprite("image/menu/start_adventure_press.png");
        //创建菜单所需要的条目，参数1：正常情况下的精灵，参数2：点击情况下的精灵，参数3：对象，参数四：方法
        //说明：后面两个参数表示通过反射调用该对象的onClick方法
        CCMenuItem items = CCMenuItemSprite.item(normalSprite, selectedSprite, this, "onClick");
        //创建菜单对象
        CCMenu menu = CCMenu.menu(items);
        //设置缩放
        menu.setScale(0.5f);
        //设置位置
        menu.setPosition(winSize.width / 2 - 25, winSize.height / 2 - 110);
        //设置旋转
        menu.setRotation(4.5f);
        //添加至图层
        this.addChild(menu);
    }

    /**
     * 菜单条目点击事件
     *
     * @param o
     */
    public void onClick(Object o) {
       /*点击跳转的新的图层*/
        CommonUtil.changeLayer(new FightLayer());
    }

}
