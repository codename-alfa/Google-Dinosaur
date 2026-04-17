package com.dino.frontend.Factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.dino.frontend.Constants;
import com.dino.frontend.Entities.Cloud;
import com.dino.frontend.Pools.CloudPool;

public class CloudFactory {
    private Texture cloudTexture;
    private final CloudPool cloudPool;

    public CloudFactory() {
        cloudTexture = new Texture("cloud.png");
        cloudPool = new CloudPool();
    }

    public Cloud createCloud(float x) {
        Cloud cloud = cloudPool.obtain();
        // Random ketinggian awan
        float randomY = MathUtils.random(Constants.CLOUD_MIN_Y, Constants.CLOUD_MAX_Y);

        cloud.init(x, randomY, cloudTexture);
        return cloud;
    }

    public void freeCloud(Cloud cloud) {
        cloudPool.free(cloud);
    }

    public void dispose() {
        cloudTexture.dispose();
        cloudPool.clear();
    }
}
