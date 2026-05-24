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

package icy.plugins.kernel.roi.descriptor.intensity;

import fr.icy.extension.kernel.roi.descriptor.intensity.*;
import fr.icy.extension.plugin.abstract_.Plugin;
import fr.icy.extension.plugin.interface_.PluginROIDescriptor;
import fr.icy.model.roi.ROI;
import fr.icy.model.roi.ROIDescriptor;
import fr.icy.model.roi.ROIUtil;
import fr.icy.model.roi.descriptor.IntensityDescriptorInfos;
import fr.icy.model.sequence.Sequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This {@link PluginROIDescriptor} implements the following "intensity" ROI descriptors:<br>
 * <ul>
 * <li>Minimum intensity</li>
 * <li>Mean intensity</li>
 * <li>Maximum intensity</li>
 * <li>Sum intensity</li>
 * <li>Standard deviation</li>
 * </ul>
 *
 * @author Stephane Dallongeville
 * @author Thomas Musset
 */
public class ROIIntensityDescriptorsPlugin extends Plugin implements PluginROIDescriptor {
    public static final String ID_MIN_INTENSITY = ROIMinIntensityDescriptor.ID;
    public static final String ID_MEAN_INTENSITY = ROIMeanIntensityDescriptor.ID;
    public static final String ID_MAX_INTENSITY = ROIMaxIntensityDescriptor.ID;
    public static final String ID_SUM_INTENSITY = ROISumIntensityDescriptor.ID;
    public static final String ID_STANDARD_DEVIATION = ROIStandardDeviationDescriptor.ID;

    public static final ROIMinIntensityDescriptor minIntensityDescriptor = new ROIMinIntensityDescriptor();
    public static final ROIMeanIntensityDescriptor meanIntensityDescriptor = new ROIMeanIntensityDescriptor();
    public static final ROIMaxIntensityDescriptor maxIntensityDescriptor = new ROIMaxIntensityDescriptor();
    public static final ROISumIntensityDescriptor sumIntensityDescriptor = new ROISumIntensityDescriptor();
    public static final ROIStandardDeviationDescriptor standardDeviationDescriptor = new ROIStandardDeviationDescriptor();

    @Override
    public List<ROIDescriptor<?>> getDescriptors() {
        final List<ROIDescriptor<?>> result = new ArrayList<>();

        result.add(minIntensityDescriptor);
        result.add(meanIntensityDescriptor);
        result.add(maxIntensityDescriptor);
        result.add(sumIntensityDescriptor);
        result.add(standardDeviationDescriptor);

        return result;
    }

    @Override
    public Map<ROIDescriptor<?>, Object> compute(final ROI roi, final Sequence sequence) throws UnsupportedOperationException {
        final Map<ROIDescriptor<?>, Object> result = new HashMap<>();
        try {
            // compute intensity descriptors
            final IntensityDescriptorInfos intensityInfos = ROIUtil.computeIntensityDescriptors(roi, sequence, false);

            result.put(minIntensityDescriptor, Double.valueOf(intensityInfos.min));
            result.put(meanIntensityDescriptor, Double.valueOf(intensityInfos.mean));
            result.put(maxIntensityDescriptor, Double.valueOf(intensityInfos.max));
            result.put(sumIntensityDescriptor, Double.valueOf(intensityInfos.sum));
            result.put(standardDeviationDescriptor, Double.valueOf(intensityInfos.deviation));
        }
        catch (final Exception e) {
            throw new UnsupportedOperationException(getClass().getSimpleName() + ": cannot compute descriptors for '" + roi.getName() + "'", e);
        }

        return result;
    }
}
