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
import org.bioimageanalysis.icy.common.geom.point.Point5D;
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
 * This {@link PluginROIDescriptor} implements the position ROI descriptors:<br>
 * <ul>
 * <li>Position X (in pixel)</li>
 * <li>Position Y (in pixel)</li>
 * <li>Position C (in pixel)</li>
 * <li>Position Z (in pixel)</li>
 * <li>Position T (in pixel)</li>
 * </ul>
 *
 * @author Stephane Dallongeville
 * @author Thomas Musset
 */
public class ROIPositionDescriptorsPlugin extends Plugin implements PluginROIDescriptor {
    public static final String ID_POSITION_X = ROIPositionXDescriptor.ID;
    public static final String ID_POSITION_Y = ROIPositionYDescriptor.ID;
    public static final String ID_POSITION_Z = ROIPositionZDescriptor.ID;
    public static final String ID_POSITION_T = ROIPositionTDescriptor.ID;
    public static final String ID_POSITION_C = ROIPositionCDescriptor.ID;

    public static final ROIPositionXDescriptor positionXDescriptor = new ROIPositionXDescriptor();
    public static final ROIPositionYDescriptor positionYDescriptor = new ROIPositionYDescriptor();
    public static final ROIPositionZDescriptor positionZDescriptor = new ROIPositionZDescriptor();
    public static final ROIPositionTDescriptor positionTDescriptor = new ROIPositionTDescriptor();
    public static final ROIPositionCDescriptor positionCDescriptor = new ROIPositionCDescriptor();

    @Override
    public List<ROIDescriptor<?>> getDescriptors() {
        final List<ROIDescriptor<?>> result = new ArrayList<>();

        result.add(positionXDescriptor);
        result.add(positionYDescriptor);
        result.add(positionZDescriptor);
        result.add(positionTDescriptor);
        result.add(positionCDescriptor);

        return result;
    }

    @Override
    public Map<ROIDescriptor<?>, Object> compute(final ROI roi, final Sequence sequence) throws UnsupportedOperationException {
        final Map<ROIDescriptor<?>, Object> result = new HashMap<>();

        try {
            // compute position descriptors
            final Point5D position = roi.getPosition5D();

            result.put(positionXDescriptor, Double.valueOf(ROIPositionXDescriptor.getPositionX(position)));
            result.put(positionYDescriptor, Double.valueOf(ROIPositionYDescriptor.getPositionY(position)));
            result.put(positionZDescriptor, Double.valueOf(ROIPositionZDescriptor.getPositionZ(position)));
            result.put(positionTDescriptor, Double.valueOf(ROIPositionTDescriptor.getPositionT(position)));
            result.put(positionCDescriptor, Double.valueOf(ROIPositionCDescriptor.getPositionC(position)));
        }
        catch (final Exception e) {
            final String mess = getClass().getSimpleName() + ": cannot compute descriptors for '" + roi.getName() + "'";
            throw new UnsupportedOperationException(mess, e);
        }

        return result;
    }
}
