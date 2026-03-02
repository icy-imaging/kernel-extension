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

package icy.plugins.kernel.roi.descriptor.measure;

import fr.icy.extension.kernel.roi.descriptor.measure.*;
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
 * This {@link PluginROIDescriptor} implements the following basic measures ROI descriptors:<br>
 * <ul>
 * <li>Contour (in pixel)</li>
 * <li>Interior (in pixel)</li>
 * <li>Perimeter (pixel size unit - 2D ROI only)</li>
 * <li>Surface Area (pixel size unit - 3D ROI only)</li>
 * <li>Area (pixel size unit - 2D ROI only)</li>
 * <li>Volume (pixel size unit - 3D ROI only)</li>
 * </ul>
 *
 * @author Stephane Dallongeville
 * @author Thomas Musset
 */
public class ROIBasicMeasureDescriptorsPlugin extends Plugin implements PluginROIDescriptor {
    public static final String ID_CONTOUR = ROIContourDescriptor.ID;
    public static final String ID_INTERIOR = ROIInteriorDescriptor.ID;
    public static final String ID_PERIMETER = ROIPerimeterDescriptor.ID;
    public static final String ID_AREA = ROIAreaDescriptor.ID;
    public static final String ID_SURFACE_AREA = ROISurfaceAreaDescriptor.ID;
    public static final String ID_VOLUME = ROIVolumeDescriptor.ID;

    public static final ROIContourDescriptor contourDescriptor = new ROIContourDescriptor();
    public static final ROIInteriorDescriptor interiorDescriptor = new ROIInteriorDescriptor();
    public static final ROIPerimeterDescriptor perimeterDescriptor = new ROIPerimeterDescriptor();
    public static final ROIAreaDescriptor areaDescriptor = new ROIAreaDescriptor();
    public static final ROISurfaceAreaDescriptor surfaceAreaDescriptor = new ROISurfaceAreaDescriptor();
    public static final ROIVolumeDescriptor volumeDescriptor = new ROIVolumeDescriptor();

    @Override
    public List<ROIDescriptor<?>> getDescriptors() {
        final List<ROIDescriptor<?>> result = new ArrayList<>();

        result.add(contourDescriptor);
        result.add(interiorDescriptor);
        result.add(perimeterDescriptor);
        result.add(areaDescriptor);
        result.add(surfaceAreaDescriptor);
        result.add(volumeDescriptor);

        return result;
    }

    @Override
    public Map<ROIDescriptor<?>, Object> compute(final ROI roi, final Sequence sequence) throws UnsupportedOperationException, InterruptedException {
        final Map<ROIDescriptor<?>, Object> result = new HashMap<>();

        // use the contour and interior to compute others descriptors
        final double contour = ROIContourDescriptor.computeContour(roi);
        final double interior = ROIInteriorDescriptor.computeInterior(roi);

        result.put(contourDescriptor, Double.valueOf(contour));
        result.put(interiorDescriptor, Double.valueOf(interior));

        int notComputed = 0;

        try {
            result.put(perimeterDescriptor, Double.valueOf(ROIPerimeterDescriptor.computePerimeter(roi, sequence)));
        }
        catch (final UnsupportedOperationException e) {
            result.put(perimeterDescriptor, null);
            notComputed++;
        }
        try {
            result.put(areaDescriptor, Double.valueOf(ROIAreaDescriptor.computeArea(interior, roi, sequence)));
        }
        catch (final UnsupportedOperationException e) {
            result.put(areaDescriptor, null);
            notComputed++;
        }
        try {
            result.put(surfaceAreaDescriptor, Double.valueOf(ROISurfaceAreaDescriptor.computeSurfaceArea(roi, sequence)));
        }
        catch (final UnsupportedOperationException e) {
            result.put(surfaceAreaDescriptor, null);
            notComputed++;
        }
        try {
            result.put(volumeDescriptor, Double.valueOf(ROIVolumeDescriptor.computeVolume(interior, roi, sequence)));
        }
        catch (final UnsupportedOperationException e) {
            result.put(volumeDescriptor, null);
            notComputed++;
        }

        if (notComputed == 4) {
            throw new UnsupportedOperationException(getClass().getSimpleName() + ": cannot compute any of the descriptors for '" + roi.getName() + "'");
        }

        return result;
    }
}
