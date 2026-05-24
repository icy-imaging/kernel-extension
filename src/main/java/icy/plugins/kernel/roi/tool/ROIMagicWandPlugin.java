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

import fr.icy.common.geom.point.Point5D;
import fr.icy.extension.plugin.abstract_.Plugin;
import fr.icy.extension.plugin.annotation_.IcyPluginIcon;
import fr.icy.extension.plugin.annotation_.IcyPluginName;
import fr.icy.extension.plugin.annotation_.IcyROIPlugin;
import fr.icy.extension.plugin.annotation_.ROIType;
import fr.icy.extension.plugin.interface_.PluginROI;
import fr.icy.model.roi.ROI;
import fr.icy.model.roi.tool.ROIMagicWand;
import org.jetbrains.annotations.NotNull;

/**
 * Plugin class for ROIMagicWand.
 *
 * @author Stephane Dallongeville
 * @author Thomas Musset
 */
@IcyPluginName("Magic Wand")
@IcyPluginIcon(value = "roi/magic_wand", monochrome = true)
@IcyROIPlugin(type = ROIType.TOOL)
public class ROIMagicWandPlugin extends Plugin implements PluginROI {
    @Override
    public @NotNull String getROIClassName() {
        return ROIMagicWand.class.getName();
    }

    @Override
    public ROI createROI(final Point5D pt) {
        return new ROIMagicWand(pt);
    }

    @Override
    public ROI createROI() {
        return new ROIMagicWand();
    }
}
