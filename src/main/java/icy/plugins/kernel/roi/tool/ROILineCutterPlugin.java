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

package icy.plugins.kernel.roi.tool;

import org.bioimageanalysis.icy.common.geom.point.Point5D;
import org.bioimageanalysis.icy.extension.plugin.abstract_.Plugin;
import org.bioimageanalysis.icy.extension.plugin.annotation_.IcyPluginIcon;
import org.bioimageanalysis.icy.extension.plugin.annotation_.IcyPluginName;
import org.bioimageanalysis.icy.extension.plugin.annotation_.IcyROIPlugin;
import org.bioimageanalysis.icy.extension.plugin.annotation_.ROIType;
import org.bioimageanalysis.icy.extension.plugin.interface_.PluginROI;
import org.bioimageanalysis.icy.model.roi.ROI;
import org.bioimageanalysis.icy.model.roi.tool.ROILineCutter;
import org.jetbrains.annotations.NotNull;

/**
 * Plugin class for ROILineCutter.
 *
 * @author Stephane Dallongeville
 * @author Thomas Musset
 */
@IcyPluginName("ROI Cutter")
@IcyPluginIcon(path = "/icy/extension/kernel/icons/roi/cutter.svg", monochrome = true)
@IcyROIPlugin(type = ROIType.TOOL)
public class ROILineCutterPlugin extends Plugin implements PluginROI {
    @Override
    public @NotNull String getROIClassName() {
        return ROILineCutter.class.getName();
    }

    @Override
    public ROI createROI(final Point5D pt) {
        return new ROILineCutter(pt);
    }

    @Override
    public ROI createROI() {
        return new ROILineCutter();
    }
}
