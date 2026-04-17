package com.dino.frontend.Pools;

import com.badlogic.gdx.utils.Pool;
import com.dino.frontend.Entities.Cloud;

public class CloudPool extends Pool<Cloud> {
    @Override
    protected Cloud newObject() {
        return new Cloud();
    }
}
