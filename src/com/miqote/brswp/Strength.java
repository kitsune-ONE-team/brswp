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

public class Strength extends Character {

    private Picture[] zip;
    private Picture hood;
    private Picture hair;
    private Picture[] face;
    private Picture rarm;
    private Picture body;
    private Picture larm;
    private Picture[] tail;
    private int zipID = 0;
    private int zipDIR = 1; // forward
    private int faceID = 0;
    private int tailID = 0;
    private int tailDIR = 1; // forward

    private Rect bounds = null;

    public Strength(Resources resources) {
        super(resources);
        zip = new Picture[] {
            getPicture(R.raw.strength_zip1),
            getPicture(R.raw.strength_zip2),
            getPicture(R.raw.strength_zip3)
        };
        hood = getPicture(R.raw.strength_hood);
        hair = getPicture(R.raw.strength_hair);
        face = new Picture[] {
            getPicture(R.raw.strength_face1),
            getPicture(R.raw.strength_face2),
            getPicture(R.raw.strength_face3)
        };
        rarm = getPicture(R.raw.strength_rarm);
        body = getPicture(R.raw.strength_body);
        larm = getPicture(R.raw.strength_larm);
        tail = new Picture[] {
            /*getPicture(R.raw.strength_t208),
            getPicture(R.raw.strength_t206),
            getPicture(R.raw.strength_t204),
            getPicture(R.raw.strength_t202),*/
            getPicture(R.raw.strength_t200),
            getPicture(R.raw.strength_t198),
            getPicture(R.raw.strength_t196),
            getPicture(R.raw.strength_t194),
            getPicture(R.raw.strength_t156),
            getPicture(R.raw.strength_t162),
            getPicture(R.raw.strength_t165),
            getPicture(R.raw.strength_t168),
            getPicture(R.raw.strength_t171)
        };
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
        canvas.drawPicture(tail[tailID / 2], bounds);
        canvas.drawPicture(larm, bounds);
        canvas.drawPicture(body, bounds);
        canvas.drawPicture(rarm, bounds);
        int id = 0;
        switch (faceID) {
        default:
            id = 0; // open
            break;
        case 47:
            id = 1; // closing
            break;
        case 48:
            id = 2; // closed
            break;
        case 49:
            id = 2; // closed
            break;
        case 50:
            id = 1; // opening
            break;
        }
        canvas.drawPicture(face[id], bounds);
        canvas.drawPicture(hair, bounds);
        canvas.drawPicture(hood, bounds);
        canvas.drawPicture(zip[zipID / 2], bounds);
        tailID += 1 * tailDIR;
        if (tailID == 0) {
            tailDIR = 1;
        }
        if (tailID == tail.length * 2 - 1) {
            tailDIR = -1;
        }
        faceID++;
        if (faceID >= 50) {
            faceID = 0;
        }
        zipID += 1 * zipDIR;
        if (zipID == 0) {
            zipDIR = 1;
        }
        if (zipID == zip.length * 2 - 1) {
            zipDIR = -1;
        }

    }

    @Override
    public void doReset() {
        bounds = null;
    }
}
