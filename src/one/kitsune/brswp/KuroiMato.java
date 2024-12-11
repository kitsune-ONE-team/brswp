/*
* Copyright (C) 2014 Yonnji Nyyoka, yonnji@kitsune.one
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
package one.kitsune.brswp;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.Rect;
import one.kitsune.brswp.R;


public class KuroiMato extends Character {

    private Picture body;
    private Picture[] base;
    private Picture h1;
    private Picture h2;
    private Picture sp;
    private int baseID = 0;

    private Rect bounds = null;

    public KuroiMato(Resources resources) {
        super(resources);
        body = getPicture(R.raw.star_eyes_base);
        base = new Picture[] {
            getPicture(R.raw.star_eyes_base_180),
            getPicture(R.raw.star_eyes_base_183),
            getPicture(R.raw.star_eyes_base_186),
            getPicture(R.raw.star_eyes_base_189),
            getPicture(R.raw.star_eyes_base_192)
        };
        h1 = getPicture(R.raw.star_eyes_h1);
        h2 = getPicture(R.raw.star_eyes_h2);
        sp = getPicture(R.raw.star_eyes_sp);
    }

    @Override
    public void doDraw(Canvas canvas, Rect canvasBounds) {
        if (bounds == null) {
            // fit to height
            int height = canvasBounds.height();
            //  width = aspect ratio * height
            int width = (int) ((float) body.getWidth() / (float) body.getHeight() * height);
            // align center
            int x = canvasBounds.left + (canvasBounds.width() - width) / 2;
            int y = canvasBounds.top;
            /*if (canvasBounds.width() < (width / 3f * 2f)) {
                // fit 2/3 of original width
                width = (int) (canvasBounds.width() / 2f * 3f);
                // height = width / aspect ratio
                height = (int) (width / ((float) eyes.getWidth() / (float) eyes.getHeight()));
                // align bottom right
                x = canvasBounds.left + canvasBounds.width() - width;
                y = canvasBounds.top + canvasBounds.height() - height;
                }*/
            bounds = new Rect(x, y, x + width, y + height);
        }
        canvas.drawPicture(body, bounds);
        canvas.drawPicture(base[baseID], bounds);
        if (baseID == 2 || baseID == 4) { // H2
            canvas.drawPicture(h2, bounds);
        } else {
            canvas.drawPicture(h1, bounds);
        }
        canvas.drawPicture(sp, bounds);
        if (baseID >= base.length - 1) {
            baseID = 0;
        } else {
            baseID++;
        }
    }

    @Override
    public void doReset() {
        bounds = null;
    }
}
