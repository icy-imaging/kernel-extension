/*
 * Copyright (c) 2010-2024. Institut Pasteur.
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

package plugins.kernel;

import org.bioimageanalysis.icy.extension.abstract_.Extension;
import org.bioimageanalysis.icy.extension.annotation_.IcyExtension;
import org.bioimageanalysis.icy.extension.plugin.abstract_.Plugin;
import plugins.kernel.roi.roi2d.ROI2DPointPlugin;

import java.util.ArrayList;

@IcyExtension
public class KernelExtension extends Extension {
    @Override
    public ArrayList<Class<? extends Plugin>> getPlugins() {
        final ArrayList<Class<? extends Plugin>> plugins = new ArrayList<>();

        plugins.add(ROI2DPointPlugin.class);

        return plugins;
    }
}
