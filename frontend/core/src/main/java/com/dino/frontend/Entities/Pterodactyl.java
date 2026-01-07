package com.dino.frontend.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Pterodactyl {
    private Texture tx;
    private Rectangle hitbox;
    private float x, y;
    private Animation<TextureRegion> animasi;

    public Pterodactyl(){
        tx = new Texture("pterodactyl.png");
        TextureRegion[][] pterodactyl = TextureRegion.split(tx, tx.getWidth()/2, tx.getHeight());
        TextureRegion[] frames = new TextureRegion[2];
        frames[0] = pterodactyl[0][0];
        frames[1] = pterodactyl[0][1];
        animasi = new Animation<>(0.33f, frames);
    }
}
