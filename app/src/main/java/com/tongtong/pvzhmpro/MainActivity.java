package com.tongtong.pvzhmpro;

import android.os.Bundle;

import com.tongtong.pvzhmpro.base.BaseActivity;
import com.tongtong.pvzhmpro.layer.WelcomeLayer;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends BaseActivity {

    private CCDirector ccDirector;  //导演对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CCGLSurfaceView surfaceView = new CCGLSurfaceView(this);
        setContentView(surfaceView);
        //创建导演对象
        ccDirector = CCDirector.sharedDirector();
        //开启线程
        ccDirector.attachInView(surfaceView);
        //显示帧率
        ccDirector.setDisplayFPS(true);
        //锁定帧率(向下锁定),帧率太高的话对CPU消耗比较严重，一般维持在>30左右即可
        ccDirector.setAnimationInterval(1.0f / 30);
        //自动屏幕适配
        ccDirector.setScreenSize(480, 320);
        //设置屏幕方向
        ccDirector.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);
        //创建场景
        CCScene ccScene = CCScene.node();
        //将图层添加至场景
        ccScene.addChild(new WelcomeLayer());
        //添加场景
        ccDirector.runWithScene(ccScene);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ccDirector.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ccDirector.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ccDirector.end();
    }
}
