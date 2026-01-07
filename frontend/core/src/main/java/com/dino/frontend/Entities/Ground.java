package com.dino.frontend.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.dino.frontend.Constants.GROUND_LEVEL;

public class Ground {
    private Texture texture;
    private float x1, x2, y, width;

    public Ground(){
        this.texture = new Texture("ground.png");
        this.y = GROUND_LEVEL;
        this.width = texture.getWidth();
        this.x1 = 0;
        this.x2 = width;
    }

    public void update(float delta, float speed){
        x1 -= speed * delta;
        x2 -= speed * delta;
        if((x1 + width) < 0){
            x1 = x2 + width;
        }
        if((x2 + width) < 0){
            x2 = x1 + width;
        }
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, x1, y);
        batch.draw(texture, x2, y);
    }

    public void dispose(){
        texture.dispose();
    }
}
