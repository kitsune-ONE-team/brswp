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

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.Rect;

public class BRS extends Character {

    protected Picture eyes;
    private Picture face;
    private Picture[] hair;
    private int hairID = 0;
    protected Rect bounds = null;

    public BRS(Resources resources) {
        super(resources);
        eyes = getPicture(R.raw.brs_eyes);
        face = getPicture(R.raw.brs_face);
        hair = new Picture[] {
            getPicture(R.raw.brs_h1),
            getPicture(R.raw.brs_h2),
            getPicture(R.raw.brs_h3),
            getPicture(R.raw.brs_h4),
            getPicture(R.raw.brs_h5),
            getPicture(R.raw.brs_h6),
            getPicture(R.raw.brs_h7),
            getPicture(R.raw.brs_h8),
            getPicture(R.raw.brs_h9),
            getPicture(R.raw.brs_h10),
            getPicture(R.raw.brs_h11),
            getPicture(R.raw.brs_h12),
            getPicture(R.raw.brs_h13),
            getPicture(R.raw.brs_h14)
        };
    }

    @Override
    public void doDraw(Canvas canvas, Rect canvasBounds) {
        if (bounds == null) {
            // fit to height
            int height = canvasBounds.height();
            //  width = aspect ratio * height
            int width = (int) ((float) eyes.getWidth() / (float) eyes.getHeight() * height);
            // align right
            int x = canvasBounds.left + canvasBounds.width() - width;
            int y = canvasBounds.top;
            if (canvasBounds.width() < (width / 3f * 2f)) {
                // fit 2/3 of original width
                width = (int) (canvasBounds.width() / 2f * 3f);
                // height = width / aspect ratio
                height = (int) (width / ((float) eyes.getWidth() / (float) eyes.getHeight()));
                // align bottom right
                x = canvasBounds.left + canvasBounds.width() - width;
                y = canvasBounds.top + canvasBounds.height() - height;
            }
            if (canvasBounds.width() > width) {
                // align left
                x = canvasBounds.left;
            }
            bounds = new Rect(x, y, x + width, y + height);
        }
        canvas.drawPicture(eyes, bounds);
        canvas.drawPicture(face, bounds);
        canvas.drawPicture(hair[hairID], bounds);
        hairID++;
        if (hairID >= hair.length) {
            hairID = 0;
        }
    }

    @Override
    public void doReset() {
        bounds = null;
    }
}
