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

public class IBRS extends BRS {

    private Picture[] glow;
    private int hairID = 0;
    private int glowID = 0;

    public IBRS(Resources resources) {
        super(resources);
        eyes = getPicture(R.raw.ibrs_eyes);
        glow = new Picture[] {
            getPicture(R.raw.ibrs_glow1),
            getPicture(R.raw.ibrs_glow2),
            getPicture(R.raw.ibrs_glow3),
            getPicture(R.raw.ibrs_glow4),
            getPicture(R.raw.ibrs_glow5),
            getPicture(R.raw.ibrs_glow6),
            getPicture(R.raw.ibrs_glow7),
            getPicture(R.raw.ibrs_glow8),
            getPicture(R.raw.ibrs_glow9),
            getPicture(R.raw.ibrs_glow10)
        };
    }

    @Override
    public void doDraw(Canvas canvas, Rect canvasBounds) {
        super.doDraw(canvas, canvasBounds);
        canvas.drawPicture(glow[glowID], bounds);
        glowID++;
        if (glowID >= glow.length) {
            glowID = 0;
        }
    }
}
