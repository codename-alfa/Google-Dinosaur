package com.dino.frontend.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import static com.dino.frontend.Constants.*;

public class Dino {
    private static Dino instance;

    private Texture idleTexture;
    private Texture runTexture;
    private Texture deadTexture;
    private TextureRegion idleRegion;
    private Animation<TextureRegion> runAnimation;
    private Sound jumpSound;

    private float y;
    private float velocityY;
    private Rectangle hitbox;

    private float stateTime;
    private boolean isJumping;
    private boolean isDead;

    private Dino(){
        idleTexture = new Texture("dinoIdle.png");
        runTexture = new Texture("dinoRun.png");
        deadTexture = new Texture("dinoDead.png");
        try {
            jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
        } catch (Exception e) {
            System.out.println("Warning: jump.wav not found. Sound will be disabled.");
            jumpSound = null;
        }

        idleRegion = new TextureRegion(idleTexture);

        TextureRegion[][] run = TextureRegion.split(runTexture, runTexture.getWidth() / 2, runTexture.getHeight());
        TextureRegion[] runFrames = new TextureRegion[2];
        runFrames[0] = run[0][0];
        runFrames[1] = run[0][1];
        runAnimation = new Animation<>(0.1f, runFrames);

        y = GROUND_LEVEL;
        velocityY = 0;
        isJumping = false;
        stateTime = 0f;

        hitbox = new Rectangle(DINO_X_POS, y, idleTexture.getWidth(), idleTexture.getHeight());

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

        if(isDead){
            return;
        }

        if((Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.UP)) && !isJumping){
            velocityY = JUMP_VELOCITY;
            isJumping = true;
            if (jumpSound != null) {
                jumpSound.play();
            }
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
        isDead = false;
        velocityY = 0f;
        stateTime = 0f;
        hitbox.setPosition(DINO_X_POS, y);
    }

    public void render(SpriteBatch batch){
        TextureRegion currentFrame;
        if(isDead){
            currentFrame = new TextureRegion(deadTexture);
        } else if(isJumping){
            currentFrame = idleRegion;
        } else{
            currentFrame = runAnimation.getKeyFrame(stateTime, true);
        }
        batch.draw(currentFrame, DINO_X_POS, y);
    }

    public Rectangle getHitbox(){
        return hitbox;
    }

    public void setDead(boolean isDead){
        this.isDead = isDead;
    }

    public void dispose(){
        idleTexture.dispose();
        runTexture.dispose();
        deadTexture.dispose();
        if (jumpSound != null) {
            jumpSound.dispose();
        }
        instance = null;
    }
}
