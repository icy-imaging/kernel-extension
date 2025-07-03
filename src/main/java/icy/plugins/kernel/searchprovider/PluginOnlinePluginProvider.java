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
package icy.plugins.kernel.searchprovider;

import org.bioimageanalysis.icy.extension.plugin.abstract_.Plugin;
import org.bioimageanalysis.icy.extension.plugin.annotation_.IcyPluginName;
import org.bioimageanalysis.icy.extension.plugin.interface_.PluginSearchProvider;
import org.bioimageanalysis.icy.network.search.SearchResultProducer;
import org.bioimageanalysis.icy.searchproducer.OnlinePluginSearchResultProducer;

/**
 * @author Stephane Dallongeville
 * @author Thomas Musset
 */
@IcyPluginName("Online Plugin Search Provider")
public class PluginOnlinePluginProvider extends Plugin implements PluginSearchProvider {
    @Override
    public Class<? extends SearchResultProducer> getSearchProviderClass() {
        return OnlinePluginSearchResultProducer.class;
    }
}
