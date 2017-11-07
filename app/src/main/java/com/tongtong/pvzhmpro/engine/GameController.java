package com.tongtong.pvzhmpro.engine;

import com.tongtong.pvzhmpro.base.Plant;
import com.tongtong.pvzhmpro.bean.ShowPlant;
import com.tongtong.pvzhmpro.layer.FightLayer;
import com.tongtong.pvzhmpro.sprite.Nut;
import com.tongtong.pvzhmpro.sprite.PeasePlant;
import com.tongtong.pvzhmpro.sprite.PrimaryZombies;
import com.tongtong.pvzhmpro.utils.CommonUtil;

import org.cocos2d.actions.CCProgressTimer;
import org.cocos2d.actions.CCScheduler;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 处理游戏开始后的操作
 * 添加僵尸
 * 安放植物
 * 僵尸攻击植物
 * 植物攻击僵尸
 * Created by allen on 2017/11/7.
 */

public class GameController {

    public static boolean isStart; //游戏是否开始
    private CCTMXTiledMap map;
    private List<ShowPlant> selectPlants;

    private List<CGPoint> roadPoints;

    private GameController() {
    }

    private static GameController controller;

    public static GameController getInstance() {
        if (controller == null) {
            controller = new GameController();
        }
        return controller;
    }

    private static List<FightLine> lines;   //管理了五行

    static {
        lines = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            FightLine line = new FightLine(i);
            lines.add(line);
        }
    }

    /**
     * 开始游戏
     *
     * @param map          游戏地图
     * @param selectPlants 玩家已选植物的集合
     */
    public void startGame(CCTMXTiledMap map, List<ShowPlant> selectPlants) {
        isStart = true;
        this.map = map;
        this.selectPlants = selectPlants;
        //解析地图
        loadMap();
        //添加定时器,每隔一段时间添加一个僵尸
        CCScheduler.sharedScheduler().schedule("addZombie", this, 2, false);
        //安放植物
        //僵尸攻击植物
        //植物攻击僵尸
        progress();
    }

    CCProgressTimer progressTimer;
    int  progress=0;
    private void progress() {
        // 创建了进度条
        progressTimer = CCProgressTimer.progressWithFile("image/fight/progress.png");
        // 设置进度条的位置
        progressTimer.setPosition(CCDirector.sharedDirector().getWinSize().width - 80, 13);
        map.getParent().addChild(progressTimer); //图层添加了进度条
        progressTimer.setScale(0.6f);  //  设置了缩放

        progressTimer.setPercentage(0);// 每增加一个僵尸需要调整进度，增加5
        progressTimer.setType(CCProgressTimer.kCCProgressTimerTypeHorizontalBarRL);  // 进度的样式

        CCSprite sprite = CCSprite.sprite("image/fight/flagmeter.png");
        sprite.setPosition(CCDirector.sharedDirector().getWinSize().width - 80, 13);
        map.getParent().addChild(sprite);
        sprite.setScale(0.6f);
        CCSprite name = CCSprite.sprite("image/fight/FlagMeterLevelProgress.png");
        name.setPosition(CCDirector.sharedDirector().getWinSize().width - 80, 5);
        map.getParent().addChild(name);
        name.setScale(0.6f);
    }

    //存放植物安放点
    CGPoint[][] towers = new CGPoint[5][9];

    private void loadMap() {
        roadPoints = CommonUtil.getMapPoints(map, "road");
        for (int i = 1; i <= 5; i++) {
            List<CGPoint> mapPoints = CommonUtil.getMapPoints(map, String.format("tower%02d", i));
            for (int j = 0; j < mapPoints.size(); j++) {
                towers[i - 1][j] = mapPoints.get(j);
            }
        }
    }

    /**
     * 添加僵尸
     */
    public void addZombie(float t) {
        //随机一行添加僵尸
        Random random = new Random();
        int lineNum = random.nextInt(5);
        PrimaryZombies zombies = new PrimaryZombies(roadPoints.get(lineNum * 2),
                roadPoints.get(lineNum * 2 + 1));
        map.addChild(zombies);
        lines.get(lineNum).addZombies(zombies);
        progress+=5;
        progressTimer.setPercentage(progress);//设置新的进度
    }

    public void stopGame() {
        isStart = false;
    }

    private ShowPlant selectedPlant;    //玩家选择的植物
    private Plant installPlant; //安放的植物

    /**
     * 当游戏开始后处理触摸事件
     *
     * @param point 点击到的点
     */
    public void handTouch(CGPoint point) {
        CCNode chose = map.getParent().getChildByTag(FightLayer.TAG_CHOSE);
        if (CGRect.containsPoint(chose.getBoundingBox(), point)) {
            //玩家有可能选择了植物
            for (ShowPlant plant :
                    selectPlants) {
                CGRect boundingBox = plant.getShowSprite().getBoundingBox();
                if (CGRect.containsPoint(boundingBox, point)) {
                    //玩家选择了植物
                    selectedPlant = plant;
                    int id = selectedPlant.getId();
                    switch (id) {
                        case 1:
                            installPlant = new PeasePlant();
                            break;
                        case 4:
                            installPlant = new Nut();
                            break;
                    }
                }
            }
        } else {
            //玩家有可能是安放植物
            if (selectedPlant != null) {
                int row = (int) (point.x / 46) - 1; // 1-9 0-8
                int line = (int) ((CCDirector.sharedDirector().getWinSize().height - point.y) / 54) - 1;// 1-5
                //限制安放植物的范围
                if (row >= 0 && row <= 8 && line >= 0 && line <= 4) {
                    // 安放植物
                    installPlant.setLine(line);// 设置植物的行号
                    installPlant.setRow(row); // 设置植物的列号

                    installPlant.setPosition(towers[line][row]); // 修正了植物的坐标
                    FightLine fightLine = lines.get(line);
                    if (!fightLine.containsRow(row)) {  // 判断当前列是否已经添加了植物 如果添加了 就不能再添加了
                        fightLine.addPlant(installPlant);// 把植物记录到了行战场中
                        map.addChild(installPlant);
                    }
                }
                selectedPlant.getShowSprite().setOpacity(255);
                selectedPlant = null;   //下次重新选择
                installPlant = null;
            }
        }
    }
}
