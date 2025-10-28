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

import org.bioimageanalysis.extension.kernel.roi.descriptor.measure.*;
import org.bioimageanalysis.icy.common.geom.point.Point5D;
import org.bioimageanalysis.icy.extension.plugin.abstract_.Plugin;
import org.bioimageanalysis.icy.extension.plugin.interface_.PluginROIDescriptor;
import org.bioimageanalysis.icy.model.roi.ROI;
import org.bioimageanalysis.icy.model.roi.ROIDescriptor;
import org.bioimageanalysis.icy.model.roi.ROIUtil;
import org.bioimageanalysis.icy.model.sequence.Sequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This {@link PluginROIDescriptor} implements the mass center ROI descriptors:<br>
 * <ul>
 * <li>Mass center X (in pixel)</li>
 * <li>Mass center Y (in pixel)</li>
 * <li>Mass center C (in pixel)</li>
 * <li>Mass center Z (in pixel)</li>
 * <li>Mass center T (in pixel)</li>
 * </ul>
 *
 * @author Stephane Dallongeville
 * @author Thomas Musset
 */
public class ROIMassCenterDescriptorsPlugin extends Plugin implements PluginROIDescriptor {
    public static final String ID_MASS_CENTER_X = ROIMassCenterXDescriptor.ID;
    public static final String ID_MASS_CENTER_Y = ROIMassCenterYDescriptor.ID;
    public static final String ID_MASS_CENTER_Z = ROIMassCenterZDescriptor.ID;
    public static final String ID_MASS_CENTER_T = ROIMassCenterTDescriptor.ID;
    public static final String ID_MASS_CENTER_C = ROIMassCenterCDescriptor.ID;

    public static final ROIMassCenterXDescriptor massCenterXDescriptor = new ROIMassCenterXDescriptor();
    public static final ROIMassCenterYDescriptor massCenterYDescriptor = new ROIMassCenterYDescriptor();
    public static final ROIMassCenterZDescriptor massCenterZDescriptor = new ROIMassCenterZDescriptor();
    public static final ROIMassCenterTDescriptor massCenterTDescriptor = new ROIMassCenterTDescriptor();
    public static final ROIMassCenterCDescriptor massCenterCDescriptor = new ROIMassCenterCDescriptor();

    @Override
    public List<ROIDescriptor<?>> getDescriptors() {
        final List<ROIDescriptor<?>> result = new ArrayList<>();

        result.add(massCenterXDescriptor);
        result.add(massCenterYDescriptor);
        result.add(massCenterZDescriptor);
        result.add(massCenterTDescriptor);
        result.add(massCenterCDescriptor);

        return result;
    }

    @Override
    public Map<ROIDescriptor<?>, Object> compute(final ROI roi, final Sequence sequence) throws InterruptedException {
        final Map<ROIDescriptor<?>, Object> result = new HashMap<>();

        // compute mass center descriptors
        final Point5D massCenter = ROIUtil.computeMassCenter(roi);

        result.put(massCenterXDescriptor, Double.valueOf(ROIMassCenterXDescriptor.getMassCenterX(massCenter)));
        result.put(massCenterYDescriptor, Double.valueOf(ROIMassCenterYDescriptor.getMassCenterY(massCenter)));
        result.put(massCenterZDescriptor, Double.valueOf(ROIMassCenterZDescriptor.getMassCenterZ(massCenter)));
        result.put(massCenterTDescriptor, Double.valueOf(ROIMassCenterTDescriptor.getMassCenterT(massCenter)));
        result.put(massCenterCDescriptor, Double.valueOf(ROIMassCenterCDescriptor.getMassCenterC(massCenter)));

        return result;
    }
}
