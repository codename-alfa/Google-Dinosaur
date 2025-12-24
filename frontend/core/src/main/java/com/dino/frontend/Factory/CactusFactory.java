package com.dino.frontend.Factory;

import com.badlogic.gdx.graphics.Texture;
import com.dino.frontend.Entities.Cactus;
import com.dino.frontend.Entities.CactusType;

public class CactusFactory {
    private Texture singleCactusTx;
    private Texture tripleCactusTx;

    public CactusFactory(){
        singleCactusTx = new Texture("1Cactus.png");
        tripleCactusTx = new Texture("3Cactus.png");
    }

    public Cactus CreateCactus(float x, CactusType type){
        switch(type){
            case TRIPLE:
                return new Cactus(x, tripleCactusTx);
            case SINGLE:
            default:
                return new Cactus(x, singleCactusTx);
        }
    }

    public void dispose(){
        singleCactusTx.dispose();
        tripleCactusTx.dispose();
    }
}
