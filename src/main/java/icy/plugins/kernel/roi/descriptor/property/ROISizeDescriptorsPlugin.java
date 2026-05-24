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

import fr.icy.extension.kernel.roi.descriptor.property.*;
import fr.icy.common.geom.rectangle.Rectangle5D;
import fr.icy.extension.plugin.abstract_.Plugin;
import fr.icy.extension.plugin.interface_.PluginROIDescriptor;
import fr.icy.model.roi.ROI;
import fr.icy.model.roi.ROIDescriptor;
import fr.icy.model.sequence.Sequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This {@link PluginROIDescriptor} implements the size ROI descriptors:<br>
 * <ul>
 * <li>Size X (in pixel)</li>
 * <li>Size Y (in pixel)</li>
 * <li>Size C (in pixel)</li>
 * <li>Size Z (in pixel)</li>
 * <li>Size T (in pixel)</li>
 * </ul>
 *
 * @author Stephane Dallongeville
 * @author Thomas Musset
 */
public class ROISizeDescriptorsPlugin extends Plugin implements PluginROIDescriptor {
    public static final String ID_SIZE_X = ROISizeXDescriptor.ID;
    public static final String ID_SIZE_Y = ROISizeYDescriptor.ID;
    public static final String ID_SIZE_Z = ROISizeZDescriptor.ID;
    public static final String ID_SIZE_T = ROISizeTDescriptor.ID;
    public static final String ID_SIZE_C = ROISizeCDescriptor.ID;

    public static final ROISizeXDescriptor sizeXDescriptor = new ROISizeXDescriptor();
    public static final ROISizeYDescriptor sizeYDescriptor = new ROISizeYDescriptor();
    public static final ROISizeZDescriptor sizeZDescriptor = new ROISizeZDescriptor();
    public static final ROISizeTDescriptor sizeTDescriptor = new ROISizeTDescriptor();
    public static final ROISizeCDescriptor sizeCDescriptor = new ROISizeCDescriptor();

    @Override
    public List<ROIDescriptor<?>> getDescriptors() {
        final List<ROIDescriptor<?>> result = new ArrayList<>();

        result.add(sizeXDescriptor);
        result.add(sizeYDescriptor);
        result.add(sizeZDescriptor);
        result.add(sizeTDescriptor);
        result.add(sizeCDescriptor);

        return result;
    }

    @Override
    public Map<ROIDescriptor<?>, Object> compute(final ROI roi, final Sequence sequence) throws UnsupportedOperationException {
        final Map<ROIDescriptor<?>, Object> result = new HashMap<>();

        try {
            // compute size descriptors
            final Rectangle5D size = roi.getBounds5D();

            result.put(sizeXDescriptor, Double.valueOf(ROISizeXDescriptor.getSizeX(size)));
            result.put(sizeYDescriptor, Double.valueOf(ROISizeYDescriptor.getSizeY(size)));
            result.put(sizeZDescriptor, Double.valueOf(ROISizeZDescriptor.getSizeZ(size)));
            result.put(sizeTDescriptor, Double.valueOf(ROISizeTDescriptor.getSizeT(size)));
            result.put(sizeCDescriptor, Double.valueOf(ROISizeCDescriptor.getSizeC(size)));
        }
        catch (final Exception e) {
            final String mess = getClass().getSimpleName() + ": cannot compute descriptors for '" + roi.getName() + "'";
            throw new UnsupportedOperationException(mess, e);
        }

        return result;
    }
}
