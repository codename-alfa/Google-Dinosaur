package com.dino.frontend.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;

import static com.dino.frontend.Constants.GROUND_LEVEL;

public class Cactus implements Pool.Poolable{
    private Texture texture;
    private float x, y;
    private float width, height; // New fields for scaled dimensions
    private Rectangle hitbox;
    private boolean remove;

    public Cactus(){
        this.y = GROUND_LEVEL;
        this.hitbox = new Rectangle();
    }

    public void init(float startX, Texture texture) {
        this.texture = texture;
        this.x = startX;
        this.y = GROUND_LEVEL;
        this.remove = false;

        // Scale the cactus to a height of 60, similar to the dino
        float newHeight = 60f;
        float newWidth = texture.getWidth() * (newHeight / texture.getHeight()); // Maintain aspect ratio
        this.width = newWidth;
        this.height = newHeight;
        this.hitbox.set(x, y, newWidth, newHeight);
    }

    public void update(float delta, float speed){
        x -= speed * delta;
        hitbox.setPosition(x, y);
        if(x < -width){ // Use scaled width for removal check
            remove = true;
        }
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, x, y, width, height); // Draw with scaled dimensions
    }

    public Rectangle getHitbox(){
        return hitbox;
    }

    public boolean getRemove(){
        return remove;
    }

    @Override
    public void reset() {
        this.x = 0;
        this.y = GROUND_LEVEL;
        this.width = 0;
        this.height = 0;
        this.remove = false;
        this.texture = null;
        this.hitbox.set(0, 0, 0, 0);
    }
}
