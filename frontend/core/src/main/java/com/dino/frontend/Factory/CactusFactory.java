package com.dino.frontend.Factory;

import com.badlogic.gdx.graphics.Texture;
import com.dino.frontend.Entities.Cactus;
import com.dino.frontend.Enums.CactusType;
import com.dino.frontend.Pools.CactusPool;

public class CactusFactory {
    private Texture singleCactusTx;
    private Texture tripleCactusTx;
    private final CactusPool cactusPool;

    public CactusFactory(){
        singleCactusTx = new Texture("1Cactus.png");
        tripleCactusTx = new Texture("3Cactus.png");
        cactusPool = new CactusPool();
    }

    public Cactus obtainCactus(float x, CactusType type){
        Cactus cactus = cactusPool.obtain();
        Texture texture;
        switch(type){
            case TRIPLE:
                texture = tripleCactusTx;
                break;
            case SINGLE:
            default:
                texture = singleCactusTx;
                break;
        }
        cactus.init(x, texture);
        return cactus;
    }

    public void freeCactus(Cactus cactus){
        cactusPool.free(cactus);
    }

    public void dispose(){
        singleCactusTx.dispose();
        tripleCactusTx.dispose();
        cactusPool.clear();
    }
}
