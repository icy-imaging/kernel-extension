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
package org.bioimageanalysis.icy.searchproducer;

import org.bioimageanalysis.icy.Icy;
import org.bioimageanalysis.icy.extension.plugin.*;
import org.bioimageanalysis.icy.gui.plugin.PluginDetailPanel;
import org.bioimageanalysis.icy.io.xml.XMLUtil;
import org.bioimageanalysis.icy.network.NetworkUtil;
import org.bioimageanalysis.icy.network.search.*;
import org.bioimageanalysis.icy.system.thread.ThreadUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to provide online plugin elements to the search engine.
 *
 * @author Stephane Dallongeville
 * @author Thomas Musset
 */
public class OnlinePluginSearchResultProducer extends OnlineSearchResultProducer {
    /**
     * @author Stephane
     */
    public static class OnlinePluginResult extends PluginSearchResult {
        public OnlinePluginResult(final SearchResultProducer provider, final PluginDescriptor plugin, final String text, final List<SearchWord> searchWords, final int priority) {
            super(provider, plugin, text, searchWords, priority);
        }

        @Override
        public String getTooltip() {
            return "Left click: Install and Run   -   Right click: Online documentation";
        }

        @Override
        @SuppressWarnings("resource")
        public void execute() {
            // can take sometime, better to execute it in background
            ThreadUtil.bgRun(() -> {
                // plugin locally installed ? (result transfer to local plugin provider)
                /*if (PluginLoader.isLoaded(plugin.getClassName())) {
                    if (plugin.isActionable())
                        PluginLauncher.start(plugin);
                    else {
                        ThreadUtil.invokeLater(() -> new PluginDetailPanel(plugin));
                    }
                }
                else {
                    // install and run the plugin (if user ok with install)
                    PluginInstaller.install(plugin, true);
                    // wait for installation complete
                    PluginInstaller.waitInstall();

                    // find the new installed plugin
                    final PluginDescriptor localPlugin = PluginLoader.getPlugin(plugin.getClassName());
                    // plugin found ?
                    if (localPlugin != null) {
                        // launch it if actionable
                        if (localPlugin.isActionable())
                            PluginLauncher.start(localPlugin);
                        else {
                            // just display info
                            ThreadUtil.invokeLater(() -> new PluginDetailPanel(localPlugin));
                        }
                    }
                }*/
            });
        }
    }

    private static final String ID_SEARCH_RESULT = "searchresult";
    private static final String ID_PLUGIN = "plugin";
    private static final String ID_CLASSNAME = "classname";
    // private static final String ID_NAME = "name";
    private static final String ID_TEXT = "string";

    @Override
    public int getOrder() {
        // should be right after local plugin
        return 6;
    }

    @Override
    public String getName() {
        return "Online plugins";
    }

    @Override
    public String getTooltipText() {
        return "Result(s) from online plugin";
    }

    @Override
    public void doSearch(final Document doc, final String text, final SearchResultConsumer consumer) {
        // Online plugin loader failed --> exit
        if (!ensureOnlineLoaderLoaded())
            return;

        // no need to spent more time here...
        if (hasWaitingSearch())
            return;

        final List<SearchWord> words = getSearchWords(text);

        if (words.isEmpty())
            return;

        // get online plugins
        final List<PluginDescriptor> onlinePlugins = PluginRepositoryLoader.getPlugins();
        // get online result node
        final Element resultElement = XMLUtil.getElement(doc.getDocumentElement(), ID_SEARCH_RESULT);

        if (resultElement == null)
            return;

        // get the local plugin search provider from search engine
        final SearchEngine se = Icy.getMainInterface().getSearchEngine();
        LocalPluginSearchResultProducer lpsrp = null;

        if (se != null) {
            for (final SearchResultProducer srp : se.getSearchResultProducers())
                if (srp instanceof LocalPluginSearchResultProducer)
                    lpsrp = (LocalPluginSearchResultProducer) srp;
        }

        final List<SearchResult> tmpResults = new ArrayList<>();
        final boolean startWithOnly = getShortSearch(words);

        for (final Element plugin : XMLUtil.getElements(resultElement, ID_PLUGIN)) {
            // abort
            if (hasWaitingSearch())
                return;

            final SearchResult result = getResult(consumer, onlinePlugins, plugin, words, startWithOnly, lpsrp);

            if (result != null)
                tmpResults.add(result);
        }

        // use a copy to avoid future concurrent accesses
        results = new ArrayList<>(tmpResults);
        consumer.resultsChanged(this);

        // load descriptions
        for (final SearchResult result : tmpResults) {
            // abort
            if (hasWaitingSearch())
                return;

            //((OnlinePluginResult) result).getPlugin().loadDescriptor();
            consumer.resultChanged(this, result);
        }

        // load images
        for (final SearchResult result : tmpResults) {
            // abort
            if (hasWaitingSearch())
                return;

            //((OnlinePluginResult) result).getPlugin().loadImages();
            consumer.resultChanged(this, result);
        }
    }

    private static boolean ensureOnlineLoaderLoaded() {
        PluginRepositoryLoader.waitLoaded();

        // repository loader failed --> retry once
        if (PluginRepositoryLoader.failed() && NetworkUtil.hasInternetAccess()) {
            PluginRepositoryLoader.reload();
            PluginRepositoryLoader.waitLoaded();
        }

        return !PluginRepositoryLoader.failed();
    }

    private OnlinePluginResult getResult(final SearchResultConsumer consumer, final List<PluginDescriptor> onlinePlugins, final Element pluginNode, final List<SearchWord> words, final boolean startWithOnly, final LocalPluginSearchResultProducer lpsrp) {
        final String className = XMLUtil.getElementValue(pluginNode, ID_CLASSNAME, "");
        final String text = XMLUtil.getElementValue(pluginNode, ID_TEXT, "");
        int priority;

        final PluginDescriptor localPlugin = null;//PluginLoader.getPlugin(className);
        // exists in local ?
        if (localPlugin != null) {
            // if we have the local search provider, we try to add result if not already existing
            if (lpsrp != null) {
                final List<SearchResult> localResults = lpsrp.getResults();
                boolean alreadyExists = false;

                synchronized (localResults) {
                    for (final SearchResult result : localResults) {
                        if (((LocalPluginSearchResultProducer.LocalPluginResult) result).getPlugin() == localPlugin) {
                            alreadyExists = true;
                            break;
                        }
                    }
                }

                // not already present in local result --> add it
                if (!alreadyExists) {
                    priority = LocalPluginSearchResultProducer.searchInPlugin(localPlugin, words, startWithOnly);

                    // not found in local description --> assume low priority
                    if (priority == 0)
                        priority = 1;

                    lpsrp.addResult(new LocalPluginSearchResultProducer.LocalPluginResult(lpsrp, localPlugin, text, words, priority), consumer);
                }
            }

            // don't return it for online result
            return null;
        }

        final PluginDescriptor onlinePlugin = null;//PluginDescriptor.getPlugin(onlinePlugins, className);
        // cannot be found in online ? --> no result
        if (onlinePlugin == null)
            return null;

        // try to get priority on result
        //onlinePlugin.loadDescriptor();
        priority = LocalPluginSearchResultProducer.searchInPlugin(onlinePlugin, words, startWithOnly);

        // only keep high priority info from local data
        if (priority <= 5)
            priority = 1;

        return new OnlinePluginResult(this, onlinePlugin, text, words, priority);
    }
}
