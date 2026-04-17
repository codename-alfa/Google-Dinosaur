package com.dino.frontend.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pool;
import com.dino.frontend.Constants;

public class Cloud implements Pool.Poolable {
    private Texture texture;
    private float x, y;
    private boolean remove;

    public Cloud() {
        this.remove = false;
    }

    public void init(float startX, float startY, Texture texture) {
        this.x = startX;
        this.y = startY;
        this.texture = texture;
        this.remove = false;
    }

    public void update(float delta) {
        x -= Constants.CLOUD_SPEED * delta;
        if (x + texture.getWidth() < 0) {
            remove = true;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public boolean getRemove() {
        return remove;
    }

    @Override
    public void reset() {
        this.x = 0;
        this.y = 0;
        this.remove = false;
        this.texture = null;
    }
}
