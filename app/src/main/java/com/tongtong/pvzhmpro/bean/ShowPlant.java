package com.tongtong.pvzhmpro.bean;

import org.cocos2d.nodes.CCSprite;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by allen on 2017/10/20.
 */

public class ShowPlant {
    /*模拟数据库保存数据*/
    static Map<Integer, HashMap<String, String>> db;

    static {
        db = new HashMap<>();
        String format = "image/fight/chose/choose_default%02d.png";
        //将数据保存到数据库
        for (int i = 1; i <= 9; i++) {
            HashMap<String, String> hasMap = new HashMap<>();
            hasMap.put("path", String.format(format, i));
            hasMap.put("sun", 50 + "");
            db.put(i, hasMap);
        }
    }
    /*---------------------*/

    public CCSprite getShowSprite() {
        return showSprite;
    }

    private CCSprite showSprite;
    private CCSprite bgSprite;

    public CCSprite getBgSprite() {
        return bgSprite;
    }

    private int id;

    public int getId() {
        return id;
    }

    /**
     * 显示植物
     */
    public ShowPlant(int id) {
        this.id = id;
        HashMap<String, String> plant = db.get(id);
        String path = plant.get("path");
        showSprite = CCSprite.sprite(path);
        showSprite.setAnchorPoint(0, 0);
        bgSprite = CCSprite.sprite(path);
        bgSprite.setAnchorPoint(0, 0);
        bgSprite.setOpacity(150);   //设置半透明
    }
}
