package com.dino.frontend.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.dino.frontend.Constants;

import static com.dino.frontend.Constants.*;

public class Dino {
    private static Dino instance;

    private Texture idleTexture;
    private Texture runTexture;
    private TextureRegion idleRegion;
    private Animation<TextureRegion> runAnimation;

    private float y;
    private float velocityY;
    private Rectangle hitbox;

    private float stateTime;
    private boolean isJumping;

    private Dino(){
        idleTexture = new Texture("dinoIdle.png"); // dino diam
        runTexture = new Texture("dinoRun.png"); // dino lari

        idleRegion = new TextureRegion(idleTexture);

        // texture lari
        TextureRegion[][] run = TextureRegion.split(runTexture, runTexture.getWidth() / 2, runTexture.getHeight()); // bagi sprite menjadi 2 (kanan dan kiri) karena ada 2 frames
        TextureRegion[] runFrames = new TextureRegion[2]; // 2 karena terdapat 2 gambar hasil pemotongan
        runFrames[0] = run[0][0]; // gambar kiri
        runFrames[1] = run[0][1]; // gambar kanan
        runAnimation = new Animation<>(0.1f, runFrames); // durasi tampilan tiap frame

        y = GROUND_LEVEL;
        velocityY = 0;
        isJumping = false;
        stateTime = 0f;

        hitbox = new Rectangle(DINO_X_POS, y, idleTexture.getWidth(), idleTexture.getHeight()); // hitbox rectangle (x, y, width, height)

        reset();
    }

    public static Dino getInstance(){
        if(instance == null){
            instance = new Dino();
        }
        return instance;
    }

    public void update(float delta){
        stateTime += delta;

        if((Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.UP)) && !isJumping){
            velocityY = JUMP_VELOCITY;
            isJumping = true;
        }

        velocityY += GRAVITY * delta;
        y += velocityY * delta;

        if (y < GROUND_LEVEL) {
            y = GROUND_LEVEL;
            velocityY = 0;
            isJumping = false;
        }
        hitbox.setPosition(DINO_X_POS, y);
    }

    public void reset(){
        y = GROUND_LEVEL;
        isJumping = false;
        velocityY = 0f;
        stateTime = 0f;
        hitbox.setPosition(DINO_X_POS, y);
    }

    public void render(SpriteBatch batch){
        TextureRegion currentFrame;

        if(isJumping){
            currentFrame = idleRegion;
        } else{
            currentFrame = runAnimation.getKeyFrame(stateTime, true);
        }
        batch.draw(currentFrame, DINO_X_POS, y);
    }

    public Rectangle getHitbox(){
        return hitbox;
    }

    public void dispose(){
        idleTexture.dispose();
        runTexture.dispose();
        instance = null;
    }
}
