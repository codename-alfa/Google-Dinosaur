package com.dino.frontend.Factory;

import com.dino.frontend.Entities.Pterodactyl;
import com.dino.frontend.Pools.PterodactylPool;

public class PterodactylFactory {
    private final PterodactylPool pterodactylPool;

    public PterodactylFactory() {
        this.pterodactylPool = new PterodactylPool();
    }

    public Pterodactyl obtainPterodactyl(float x) {
        Pterodactyl ptero = pterodactylPool.obtain();
        ptero.init(x);
        return ptero;
    }

    public void freePterodactyl(Pterodactyl ptero) {
        pterodactylPool.free(ptero);
    }

    public void dispose() {
        Pterodactyl.dispose(); // Call the static dispose method
        pterodactylPool.clear();
    }
}
