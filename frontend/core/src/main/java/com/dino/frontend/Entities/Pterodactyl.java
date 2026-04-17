package com.dino.frontend.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.dino.frontend.Constants;

public class Pterodactyl implements Pool.Poolable {
    // Possible flight levels for the pterodactyl
    private static final float[] FLY_LEVELS = {Constants.GROUND_LEVEL + 20f, Constants.GROUND_LEVEL + 60f};

    private static Texture texture;
    private static Animation<TextureRegion> flyAnimation;

    private float x, y;
    private Rectangle hitbox;
    private float stateTime;
    private boolean remove;

    public Pterodactyl() {
        if (texture == null) {
            texture = new Texture("pterodactyl.png");
        }
        if (flyAnimation == null) {
            TextureRegion[][] split = TextureRegion.split(texture, texture.getWidth() / 2, texture.getHeight());
            flyAnimation = new Animation<>(0.2f, split[0][0], split[0][1]);
            flyAnimation.setPlayMode(Animation.PlayMode.LOOP);
        }
        this.hitbox = new Rectangle();
        this.remove = false;
    }

    public void init(float startX) {
        this.x = startX;
        // Randomly choose a flight level
        this.y = FLY_LEVELS[MathUtils.random(0, FLY_LEVELS.length - 1)];
        this.stateTime = 0f;
        this.remove = false;
        this.hitbox.set(x, y, texture.getWidth() / 2f, texture.getHeight());
    }

    public void update(float delta, float speed) {
        stateTime += delta;
        x -= speed * delta;
        hitbox.setPosition(x, y);
        if (x < -(texture.getWidth() / 2f)) {
            remove = true;
        }
    }

    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = flyAnimation.getKeyFrame(stateTime);
        batch.draw(currentFrame, x, y);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public boolean getRemove() {
        return remove;
    }

    @Override
    public void reset() {
        this.x = 0;
        this.y = 0;
        this.stateTime = 0f;
        this.remove = false;
        this.hitbox.set(0, 0, 0, 0);
    }

    public static void dispose() {
        if (texture != null) {
            texture.dispose();
            texture = null; // Clear static reference
            flyAnimation = null; // Clear static reference
        }
    }
}
