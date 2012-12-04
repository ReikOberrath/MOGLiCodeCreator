package com.iksgmbh.moglicc.helper;

import static com.iksgmbh.moglicc.MOGLiTextConstants2.TEXT_DUPLICATE_PLUGINIDS;
import static com.iksgmbh.moglicc.MOGLiTextConstants2.TEXT_STARTERCLASS_UNKNOWN;
import static com.iksgmbh.moglicc.MOGLiTextConstants2.TEXT_STARTERCLASS_WRONG_TYPE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.iksgmbh.moglicc.PluginMetaData;
import com.iksgmbh.moglicc.PluginMetaData.PluginStatus;
import com.iksgmbh.moglicc.exceptions.DuplicatePluginIdException;
import com.iksgmbh.moglicc.exceptions.MOGLiCoreException2;
import com.iksgmbh.moglicc.plugin.MOGLiPlugin2;
import com.iksgmbh.moglicc.utils.MOGLiLogUtil2;

/**
 * Helps MOGLi class to do its job
 * @author Reik Oberrath
 */
public class PluginLoader {

	PluginLoader() {}
	
	public static List<MOGLiPlugin2> doYourJob(List<PluginMetaData> pluginMetaDataList) {
		return new PluginLoader().loadPlugins(pluginMetaDataList);
	}

	List<MOGLiPlugin2> loadPlugins(List<PluginMetaData> pluginMetaDataList) {
		List<MOGLiPlugin2> plugins = new ArrayList<MOGLiPlugin2>(); 

		if (pluginMetaDataList.size() == 0) {
			return plugins;
		}
		
		for (PluginMetaData pluginMetaData : pluginMetaDataList) {
			if (pluginMetaData.isStatusOK()) {
				LoadResult loadResult = loadThisPlugin(pluginMetaData.getStarterClass());
				if (loadResult.plugin == null) {
					pluginMetaData.setInfoMessage(loadResult.errorMessage);
					MOGLiLogUtil2.logWarning(pluginMetaData.toString());
				} else {
					plugins.add(loadResult.plugin);
					updateMetaData(pluginMetaData, loadResult.plugin);
				}
			}
		}
		
		checkPluginIdsUnique(plugins);
		
		return plugins;
	}

	LoadResult loadThisPlugin(String starterClassName) {
		LoadResult loadResult = new LoadResult();
		try {
			loadResult.plugin = (MOGLiPlugin2) Class.forName(starterClassName).newInstance();
		} catch (ClassNotFoundException e) {
			loadResult.errorMessage = TEXT_STARTERCLASS_UNKNOWN;
		} catch (ClassCastException e) {
			loadResult.errorMessage = TEXT_STARTERCLASS_WRONG_TYPE;
		} catch (Exception e) {
			throw new MOGLiCoreException2("Unexpected Error instanziating " + starterClassName,  e);
		}
		return loadResult;
	}

	// *****************************  private methods  ************************************
	
	private void checkPluginIdsUnique(List<MOGLiPlugin2> plugins) {
		HashSet<String> hs = new HashSet<String>();
		for (int i = 0; i < plugins.size(); i++) {
			String id = plugins.get(i).getId();
			if (hs.contains(id)) {
				MOGLiLogUtil2.logError(TEXT_DUPLICATE_PLUGINIDS + id);
				throw new DuplicatePluginIdException(id);
			}
			hs.add(id);
		}
	}

	private void updateMetaData(PluginMetaData pluginMetaData,
			                            MOGLiPlugin2 loadedPlugin) {
		pluginMetaData.setDependencies(loadedPlugin.getDependencies());
		pluginMetaData.setId(loadedPlugin.getId());
		pluginMetaData.setPluginType(loadedPlugin.getPluginType());
		pluginMetaData.setPluginStatus(PluginStatus.LOADED);
	}
	
	class LoadResult {
		MOGLiPlugin2 plugin;
		String errorMessage;
	}

}
