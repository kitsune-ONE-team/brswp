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
import com.larvalabs.svgandroid.*;


public class Character {

    protected Resources resources;

    public Character(Resources resources) {
        this.resources = resources;
    }

    protected Picture getPicture(int resourceID) {
        SVG svg = SVGParser.getSVGFromResource(resources, resourceID);
        return svg.getPicture();
    }

    public void doDraw(Canvas canvas, Rect canvasBounds) {
    }

    public void doReset() {
    }
}
