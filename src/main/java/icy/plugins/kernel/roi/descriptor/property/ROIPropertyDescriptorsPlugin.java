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

package icy.plugins.kernel.roi.descriptor.property;

import org.bioimageanalysis.extension.kernel.roi.descriptor.property.*;
import org.bioimageanalysis.icy.extension.plugin.abstract_.Plugin;
import org.bioimageanalysis.icy.extension.plugin.interface_.PluginROIDescriptor;
import org.bioimageanalysis.icy.model.roi.ROI;
import org.bioimageanalysis.icy.model.roi.ROIDescriptor;
import org.bioimageanalysis.icy.model.sequence.Sequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This {@link PluginROIDescriptor} implements the properties ROI descriptors:<br>
 * <ul>
 * <li>Name</li>
 * <li>Color</li>
 * <li>Opacity</li>
 * <li>Read only</li>
 * </ul>
 *
 * @author Stephane Dallongeville
 * @author Thomas Musset
 */
public class ROIPropertyDescriptorsPlugin extends Plugin implements PluginROIDescriptor {
    public static final String ID_ID = ROIIdDescriptor.ID;
    public static final String ID_ICON = ROIIconDescriptor.ID;
    public static final String ID_NAME = ROINameDescriptor.ID;
    // public static final String ID_GROUPID = ROIGroupIdDescriptor.ID;
    public static final String ID_COLOR = ROIColorDescriptor.ID;
    public static final String ID_OPACITY = ROIOpacityDescriptor.ID;
    public static final String ID_READONLY = ROIReadOnlyDescriptor.ID;

    public static final ROIIdDescriptor idDescriptor = new ROIIdDescriptor();
    public static final ROIIconDescriptor iconDescriptor = new ROIIconDescriptor();
    public static final ROINameDescriptor nameDescriptor = new ROINameDescriptor();
    // public static final ROIGroupIdDescriptor groupIdDescriptor = new ROIGroupIdDescriptor();
    public static final ROIColorDescriptor colorDescriptor = new ROIColorDescriptor();
    public static final ROIOpacityDescriptor opacityDescriptor = new ROIOpacityDescriptor();
    public static final ROIReadOnlyDescriptor readOnlyDescriptor = new ROIReadOnlyDescriptor();

    @Override
    public List<ROIDescriptor> getDescriptors() {
        final List<ROIDescriptor> result = new ArrayList<>();

        result.add(idDescriptor);
        result.add(iconDescriptor);
        result.add(nameDescriptor);
        // result.add(groupIdDescriptor);
        result.add(colorDescriptor);
        result.add(opacityDescriptor);
        result.add(readOnlyDescriptor);

        return result;
    }

    @Override
    public Map<ROIDescriptor, Object> compute(final ROI roi, final Sequence sequence) throws UnsupportedOperationException {
        final Map<ROIDescriptor, Object> result = new HashMap<>();

        try {
            // compute descriptors
            result.put(idDescriptor, Integer.valueOf(ROIIdDescriptor.getId(roi)));
            result.put(iconDescriptor, ROIIconDescriptor.getIcon(roi));
            result.put(nameDescriptor, ROINameDescriptor.getName(roi));
            // result.put(groupIdDescriptor, ROIGroupIdDescriptor.getGroupId(roi));
            result.put(colorDescriptor, ROIColorDescriptor.getColor(roi));
            result.put(opacityDescriptor, Float.valueOf(ROIOpacityDescriptor.getOpacity(roi)));
            result.put(readOnlyDescriptor, Boolean.valueOf(ROIReadOnlyDescriptor.getReadOnly(roi)));
        }
        catch (final Exception e) {
            final String mess = getClass().getSimpleName() + ": cannot compute descriptors for '" + roi.getName() + "'";
            throw new UnsupportedOperationException(mess, e);
        }

        return result;
    }
}
