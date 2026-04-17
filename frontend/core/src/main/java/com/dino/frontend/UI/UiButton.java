package com.dino.frontend.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class UiButton {
    private Texture texture;
    private float x, y, width, height;
    private Rectangle bounds;

    public UiButton(String texturePath, float x, float y) {
        try {
            this.texture = new Texture(texturePath);
            this.width = texture.getWidth();
            this.height = texture.getHeight();
        } catch (Exception e) {
            System.out.println("Warning: Button texture not found at '" + texturePath + "'. Button will not be functional or visible.");
            this.texture = null;
            this.width = 0;
            this.height = 0;
        }
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void render(SpriteBatch batch) {
        if (texture != null) {
            batch.draw(texture, x, y);
        }
    }

    public boolean isClicked() {
        if (texture == null || !Gdx.input.justTouched()) {
            return false;
        }
        float mouseX = Gdx.input.getX();
        // Invert Y coordinate because input origin is top-left, but world/camera is bottom-left
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        return bounds.contains(mouseX, mouseY);
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}
