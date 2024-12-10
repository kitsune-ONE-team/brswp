/*
* Copyright (C) 2014 Yonnji Nyyoka, yonnji@miqote.com
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 3
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
*/
package com.miqote.brswp;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;
import android.util.Log;
import java.io.*;
import com.larvalabs.svgandroid.*;

public class LiveWallpaperService extends WallpaperService {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Engine onCreateEngine() {
        return new LiveWallpaperEngine();
    }

    public class LiveWallpaperEngine extends Engine implements Runnable,
                                                               SharedPreferences.OnSharedPreferenceChangeListener {
        private Handler handler = new Handler();
        private SharedPreferences preferences;
        private boolean isVisible = true;
        private int rotate = 0;
        private int frameRate = 15;
        private Picture[] backgrounds;
        private int backgroundID = 0;
        private Character[] characters;
        private int characterID = 0;
        private Rect canvasBounds = null;
        private Rect wallpaperBounds = null;

        public LiveWallpaperEngine() {
            preferences = getSharedPreferences("settings", 0);
            preferences.registerOnSharedPreferenceChangeListener(this);
            onSharedPreferenceChanged(preferences, null);
            backgrounds = new Picture[] {
                getPicture(R.raw.bg_chains),
                getPicture(R.raw.bg_spikes),
                getPicture(R.raw.bg_spikes_dark),
                getPicture(R.raw.bg_abyss),
                getPicture(R.raw.bg_cube),
                getPicture(R.raw.star_eyes_floor)
            };
            characters = new Character[] {
                new BRS(getResources()),
                new IBRS(getResources()),
                new BRSPower(getResources()),
                new Strength(getResources()),
                new KuroiMato(getResources())
            };
        }

        private Picture getPicture(int resourceID) {
            //InputStream in = getResources().openRawResource(resourceID);
            //SVG svg = SVGParser.getSVGFromInputStream(in);
            SVG svg = SVGParser.getSVGFromResource(getResources(), resourceID);
            return svg.getPicture();
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            handler.removeCallbacks(this);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            isVisible = visible;
            if (isVisible) {
                run();
            } else {
                handler.removeCallbacks(this);
            }
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            isVisible = false;
            handler.removeCallbacks(this);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onOffsetsChanged(float xOffset, float yOffset,
                                     float xStep, float yStep, int xPixels, int yPixels) {
        }

        @Override
        public final void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
            backgroundID = Integer.parseInt(prefs.getString("background", "0"));
            characterID = Integer.parseInt(prefs.getString("character", "0"));
            rotate = Integer.parseInt(prefs.getString("rotate", "0"));
            frameRate = Integer.parseInt(prefs.getString("framerate", "15"));
            canvasBounds = null;
            wallpaperBounds = null;
        }

        private void doDraw(Canvas canvas) {
            if (canvasBounds == null) {
                int x = canvas.getClipBounds().left;
                int y = canvas.getClipBounds().top;
                int width = canvas.getWidth();
                int height = canvas.getHeight();
                if (rotate == 90 || rotate == 270) {
                    int temp = width;
                    width = height;
                    height = temp;
                }
                canvasBounds = new Rect(x, y, x + width, y + height);
                characters[characterID].doReset();
            }
            if (wallpaperBounds == null) {
                // fit to height
                int height = canvasBounds.height();
                //  width = aspect ratio * height
                int width = (int) ((float) backgrounds[backgroundID].getWidth() / (float) backgrounds[backgroundID].getHeight() * height);
                // center horizontally
                int x = (int) (canvasBounds.left + (canvasBounds.width() - width) / 2f);
                int y = canvasBounds.top;
                if (canvasBounds.width() > width) {
                    // fit to width
                    width = canvasBounds.width();
                    // height = width / aspect ratio
                    height = (int) (width / ((float) backgrounds[backgroundID].getWidth() / (float) backgrounds[backgroundID].getHeight()));
                    // center vertically
                    x = canvasBounds.left;
                    y = (int) (canvasBounds.top + (canvasBounds.height() - height) / 2f);
                }
                wallpaperBounds = new Rect(x, y, x + width, y + height);
            }
            if (rotate != 0) {
                canvas.save();
                int lessSide = Math.min(canvas.getWidth(), canvas.getHeight());
                canvas.rotate(canvas.getHeight() <= canvas.getWidth() ? -90 : 90,
                              lessSide / 2f,
                              lessSide / 2f);
            }
            if (rotate >= 180) {
                canvas.rotate(180, canvas.getHeight() / 2f, canvas.getWidth() / 2f);
            }
            canvas.drawPicture(backgrounds[backgroundID], wallpaperBounds);
            characters[characterID].doDraw(canvas, canvasBounds);
            if (rotate != 0) {
                canvas.restore();
            }
        }

        @Override
        public final void run() {
            SurfaceHolder holder = getSurfaceHolder();
            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas();
                if (canvas != null) {
                    doDraw(canvas);
                }
            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
            handler.removeCallbacks(this);
            if (isVisible) {
                handler.postDelayed(this, 1000 / frameRate);
            }
        }
    }
}
