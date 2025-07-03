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

package icy.plugins.kernel.roi.descriptor.logical;

import org.bioimageanalysis.extension.kernel.roi.descriptor.logical.ROIIntersectedDescriptor;
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
 * The {@link PluginROIDescriptor} implementing the <i>Intersected ROIs</i> ROI descriptor
 *
 * @author Stephane Dallongeville
 * @author Thomas Musset
 */
public class ROIIntersectedDescriptorPlugin extends Plugin implements PluginROIDescriptor {
    public static final String ID_INTERSECTED_ROIS = ROIIntersectedDescriptor.ID;

    public static final ROIIntersectedDescriptor intersectedDescriptor = new ROIIntersectedDescriptor();

    @Override
    public List<ROIDescriptor> getDescriptors() {
        final List<ROIDescriptor> result = new ArrayList<>();

        result.add(intersectedDescriptor);

        return result;
    }

    @Override
    public Map<ROIDescriptor, Object> compute(final ROI roi, final Sequence sequence) throws UnsupportedOperationException, InterruptedException {
        final Map<ROIDescriptor, Object> result = new HashMap<>();

        result.put(intersectedDescriptor, intersectedDescriptor.compute(roi, sequence));

        return result;
    }
}
