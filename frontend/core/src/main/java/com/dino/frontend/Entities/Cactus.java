package com.dino.frontend.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;

import static com.dino.frontend.Constants.GROUND_LEVEL;

public class Cactus implements Pool.Poolable{
    private Texture texture;
    private float x, y;
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
        this.hitbox.set(x, y, texture.getWidth(), texture.getHeight());
    }

    public void update(float delta, float speed){
        x -= speed * delta; // speed dinamis
        hitbox.setPosition(x, y);
        if(x < -texture.getWidth()){
            remove = true;
        }
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, x, y);
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
        this.remove = false;
        this.texture = null; // Lepas tekstur agar tidak memory leak
        this.hitbox.set(0, 0, 0, 0); // Nol-kan hitbox
    }
}
