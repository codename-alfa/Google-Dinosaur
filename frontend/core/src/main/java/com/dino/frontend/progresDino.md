### Rangkuman Teknis Proyek Dino Game

Sesi ini berfokus pada melanjutkan pengembangan dan memperbaiki bug pada game endless runner berbasis LibGDX.

#### Progres Perubahan Kode & Fitur

1.  **Aktivasi Menu Utama**:
    *   Alur aplikasi diubah di `Main.java` untuk memulai dari `MainMenuScreen`, bukan langsung ke `GameScreen`.

2.  **Implementasi Musuh Baru (`Pterodactyl`)**:
    *   Kelas `Pterodactyl.java` disempurnakan menjadi entitas game yang fungsional, lengkap dengan animasi, logika pergerakan, dan `hitbox`.
    *   `PterodactylFactory` dan `PterodactylPool` dibuat untuk manajemen objek yang efisien.
    *   Logika kemunculan Pterodactyl ditambahkan ke `GameScreen`, di mana mereka mulai muncul setelah skor pemain melebihi 300.

3.  **Sistem Skor & Kesulitan Progresif**:
    *   `GameScreen` di-update untuk menyertakan variabel `score`, `highScore`, dan `gameSpeed`.
    *   Skor bertambah seiring waktu berdasarkan `gameSpeed`.
    *   `gameSpeed` meningkat secara bertahap dari `INITIAL_SPEED` ke `MAX_SPEED` untuk menambah tingkat kesulitan.

4.  **Perbaikan Stabilitas & Bug**:
    *   **Crash Akibat Aset Hilang**: Crash yang disebabkan oleh file suara (`.wav`) dan gambar UI (`.png`) yang hilang telah diperbaiki.
        *   Kelas `Dino`, `GameScreen`, dan `UiButton` dimodifikasi untuk memuat aset di dalam blok `try-catch`.
        *   `UiButton` di-refactor untuk menerima `String` path gambar (bukan objek `Texture`), membuatnya bertanggung jawab atas pemuatan dan penanganan errornya sendiri.
    *   **Crash `gdx-freetype`**: Crash `SharedLibraryLoadRuntimeException` yang disebabkan oleh dependensi native `gdx-freetype` (untuk font kustom) telah diatasi.
        *   Penggunaan `FreeTypeFontGenerator` di `GameScreen` diganti dengan `new BitmapFont()` standar yang tidak memerlukan dependensi native.

5.  **Penyesuaian Gameplay & Keseimbangan**:
    *   **Resolusi Layar**: Konstanta di `Constants.java` diubah menjadi `WORLD_WIDTH = 1280f` dan `WORLD_HEIGHT = 720f` untuk menyesuaikan dengan resolusi target.
    *   **Tinggi Kaktus**: Logika di `Cactus.java` diubah untuk menskalakan tinggi kaktus agar sepadan dengan tinggi Dino (ditetapkan ke `60f`), membuat lompatan lebih adil.
    *   **Tinggi Lompatan**: `JUMP_VELOCITY` di `Constants.java` ditingkatkan menjadi `850f` untuk menggandakan ketinggian lompatan.
    *   **Kecepatan Awal**: `INITIAL_SPEED` di `Constants.java` ditingkatkan menjadi `450f` (3x lipat) untuk membuat awal permainan lebih cepat.
    *   **Restart dengan Spasi**: `GameScreen` dimodifikasi agar pemain dapat memulai ulang permainan dari layar "Game Over" dengan menekan tombol spasi.

---

### Struktur Class Terbaru

*   **`Main.java`**: Titik masuk aplikasi. Mengatur `SpriteBatch` dan memulai `MainMenuScreen`.

*   **`Constants.java`**: Menyimpan semua konstanta game. Nilai-nilai penting yang telah diubah:
    *   `WORLD_WIDTH`: `1280f`
    *   `WORLD_HEIGHT`: `720f`
    *   `JUMP_VELOCITY`: `850f`
    *   `INITIAL_SPEED`: `450f`

*   **`Screens/`**:
    *   **`GameScreen.java`**: Kelas utama yang mengelola seluruh logika permainan, termasuk:
        *   Game loop (`render`).
        *   Manajemen state (`RUNNING`, `PAUSED`, `GAME_OVER`).
        *   Memunculkan dan mengelola `Cactus`, `Pterodactyl`, dan `Cloud` melalui *factories* dan *pools*.
        *   Logika skor, high score, dan peningkatan kecepatan.
        *   Memutar efek suara (kematian, skor) dan menangani input (pause, restart).
        *   Menggunakan `BitmapFont` standar untuk rendering teks.
    *   **`MainMenuScreen.java`**: Layar menu utama dengan tombol "Play" untuk memulai `GameScreen`.

*   **`Entities/`**:
    *   **`Dino.java`**: Entitas pemain (Singleton). Mengelola logika lompatan (termasuk suara) dan status (hidup/mati).
    *   **`Cactus.java`**: Entitas rintangan darat. Sekarang secara otomatis menskalakan ukurannya saat dibuat agar tingginya tetap (`60f`).
    *   **`Pterodactyl.java`**: Entitas rintangan terbang. Memiliki beberapa kemungkinan ketinggian terbang.
    *   **`Ground.java`**, **`Cloud.java`**: Entitas latar belakang yang bergerak.

*   **`UI/`**:
    *   **`UiButton.java`**: Kelas tombol yang telah di-refactor. Sekarang lebih tangguh karena memuat teksturnya sendiri dari path `String` dan dapat menangani kasus di mana file gambar tidak ada tanpa menyebabkan crash.

*   **`Factory/`** & **`Pools/`**:
    *   Berisi kelas-kelas `Factory` dan `Pool` untuk `Cactus`, `Cloud`, dan `Pterodactyl` yang menerapkan pola desain *Object Pooling* untuk manajemen memori yang efisien.
