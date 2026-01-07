package com.dino.frontend.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class UiButton {
    private Texture texture;
    private float x, y, width, height;
    private Rectangle bounds;

    public UiButton(Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public boolean isClicked() {
        if (Gdx.input.justTouched()) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
            return bounds.contains(mouseX, mouseY);
        }
        return false;
    }

    public void dispose() {
        texture.dispose();
    }
}
