package com.tongtong.pvzhmpro.engine;

/**
 * 把一行的操作抽取出来
 * 每一行可以添加僵尸，安放植物，植物能攻击僵尸，僵尸能攻击植物
 * Created by allen on 2017/11/7.
 */

public class FightLine {

    private int lineNum;    //行号

    public FightLine(int lineNum) {
        this.lineNum = lineNum;
    }
}
