package com.dino.frontend;

import com.badlogic.gdx.Game; // Gunakan Game, bukan ApplicationAdapter
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dino.frontend.Screens.GameScreen; // Pastikan package sesuai

public class Main extends Game { // Extends Game
    public SpriteBatch batch; // Public agar bisa diakses oleh Screen lain

    @Override
    public void create() {
        batch = new SpriteBatch();
        // Langsung pindah ke layar permainan
        this.setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        super.render(); // Penting! Delegasikan render ke Screen yang aktif
    }

    @Override
    public void dispose() {
        batch.dispose();
        // Screen dispose otomatis ditangani jika dikelola dengan baik,
        // tapi untuk simple project, batch.dispose() di sini cukup.
    }
}
