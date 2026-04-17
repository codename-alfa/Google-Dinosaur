package com.dino.frontend.Pools;

import com.badlogic.gdx.utils.Pool;
import com.dino.frontend.Entities.Pterodactyl;

public class PterodactylPool extends Pool<Pterodactyl> {
    @Override
    protected Pterodactyl newObject() {
        return new Pterodactyl();
    }
}
