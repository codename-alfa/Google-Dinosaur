package com.dino.frontend.Pools;

import com.badlogic.gdx.utils.Pool;
import com.dino.frontend.Entities.Cactus;

public class CactusPool extends Pool<Cactus> {
    @Override
    protected Cactus newObject() {
        return new Cactus();
    }
}
