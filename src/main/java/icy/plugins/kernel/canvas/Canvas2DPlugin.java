/*
 * Copyright (c) 2010-2025. Institut Pasteur.
 *
 * This file is part of Icy.
 * Icy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Icy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Icy. If not, see <https://www.gnu.org/licenses/>.
 */

package icy.plugins.kernel.canvas;

import org.bioimageanalysis.icy.extension.plugin.abstract_.Plugin;
import org.bioimageanalysis.icy.extension.plugin.annotation_.IcyPluginIcon;
import org.bioimageanalysis.icy.extension.plugin.annotation_.IcyPluginName;
import org.bioimageanalysis.icy.extension.plugin.interface_.PluginCanvas;
import org.bioimageanalysis.icy.gui.canvas.Canvas2D;
import org.bioimageanalysis.icy.gui.viewer.Viewer;

/**
 * @author Stephane Dallongeville
 * @author Thomas Musset
 */
@IcyPluginName("Canvas 2D")
@IcyPluginIcon(value = "canvas/2d_canvas", monochrome = true)
public class Canvas2DPlugin extends Plugin implements PluginCanvas<Canvas2D> {
    @Override
    public String getCanvasClassName() {
        return Canvas2D.class.getName();
    }

    @Override
    public Canvas2D createCanvas(final Viewer viewer) {
        return new Canvas2D(viewer);
    }
}
