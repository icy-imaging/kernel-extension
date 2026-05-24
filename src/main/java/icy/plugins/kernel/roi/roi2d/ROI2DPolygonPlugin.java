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

package icy.plugins.kernel.roi.roi2d;

import fr.icy.extension.kernel.roi.roi2d.ROI2DPolygon;
import fr.icy.common.geom.point.Point5D;
import fr.icy.extension.plugin.abstract_.Plugin;
import fr.icy.extension.plugin.annotation_.*;
import fr.icy.extension.plugin.interface_.PluginROI;
import fr.icy.model.roi.ROI;
import org.jetbrains.annotations.NotNull;

/**
 * Plugin class for ROI2DPolygon.
 *
 * @author Stephane Dallongeville
 * @author Thomas Musset
 */
@IcyPluginName("Polygon")
@IcyPluginIcon(value = "roi/polygon", monochrome = true)
@IcyROIPlugin(type = ROIType.ROI2D, nbPoints = ROIPoints.INFINITE)
public class ROI2DPolygonPlugin extends Plugin implements PluginROI {
    @Override
    public @NotNull String getROIClassName() {
        return ROI2DPolygon.class.getName();
    }

    @Override
    public ROI createROI(final Point5D pt) {
        return new ROI2DPolygon(pt);
    }

    @Override
    public ROI createROI() {
        return new ROI2DPolygon();
    }
}
