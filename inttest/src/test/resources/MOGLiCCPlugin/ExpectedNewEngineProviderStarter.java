package com.iksgmbh.moglicc.generator.classbased.velocity;

import java.io.File;
import java.util.List;

import com.iksgmbh.moglicc.core.InfrastructureService;
import com.iksgmbh.moglicc.exceptions.MOGLiPluginException;
import com.iksgmbh.moglicc.generator.utils.*;
import com.iksgmbh.moglicc.plugin.type.*;
import com.iksgmbh.moglicc.plugin.type.basic.*;
import com.iksgmbh.moglicc.provider.model.standard.Model;
import com.iksgmbh.moglicc.provider.model.standard.metainfo.*;
import com.iksgmbh.utils.ImmutableUtil;
import com.iksgmbh.moglicc.generator.utils.helper.*;
import com.iksgmbh.utils.FileUtil;

/**
* Raw draft of the starter class of the MyEngineProvider plugin.
* TODO: Complete the implementation manually.
* @author generated by MOGLi Code Creator
*/
public class NewEngineProviderStarter implements EngineProvider {

	public static final String PLUGIN_ID = "MyEngineProvider";
	public static final String MODEL_PROVIDER_ID = "StandardModelProvider";
	public static final String ENGINE_PROVIDER_ID = "VelocityEngineProvider";

	public static final String PLUGIN_PROPERTIES_FILE = "generator.properties";
	public static final String MAIN_TEMPLATE_IDENTIFIER = "Main";

	private InfrastructureService infrastructure;
	private Object engineData;

	@Override
	public void setMOGLiInfrastructure(final InfrastructureService infrastructure) {
		this.infrastructure = infrastructure;
	}

	@Override
	public void doYourJob() throws MOGLiPluginException {
		// engine providers have nothing to do here (see startEngine)
	}


	String findMainTemplate(final File templateDir) throws MOGLiPluginException {
		return TemplateUtil.findMainTemplate(templateDir, MAIN_TEMPLATE_IDENTIFIER);
	}

	@Override
	public boolean unpackDefaultInputData() throws MOGLiPluginException {
		infrastructure.getPluginLogger().logInfo("initDefaultInputData");
		final PluginPackedData defaultData = new PluginPackedData(this.getClass(), DEFAULT_DATA_DIR);

		//  TODO: add defaultData files here

		PluginDataUnpacker.doYourJob(defaultData, infrastructure.getPluginInputDir(), infrastructure.getPluginLogger());
		return true;
	}

	@Override
	public PluginType getPluginType() {
		return PluginType.ENGINE_PROVIDER;
	}

	@Override
	public String getId() {
		return PLUGIN_ID;
	}

	@Override
	public List<String> getDependencies() {
		return ImmutableUtil.getImmutableListOf(MODEL_PROVIDER_ID);
	}


	@Override
	public InfrastructureService getMOGLiInfrastructure() {
		return infrastructure;
	}

	@Override
	public boolean unpackPluginHelpFiles() throws MOGLiPluginException {
		infrastructure.getPluginLogger().logInfo("unpackPluginHelpFiles");
		final PluginPackedData helpData = new PluginPackedData(this.getClass(), HELP_DATA_DIR);

		//  TODO: add help files here

		PluginDataUnpacker.doYourJob(helpData, infrastructure.getPluginHelpDir(), infrastructure.getPluginLogger());
		return true;
	}

	@Override
	public void setEngineData(Object engineData) throws MOGLiPluginException {

		if (engineData == null) {
			throw new MOGLiPluginException("Parameter 'engineData' must not be null!");
		}

		// TODO add further validations of enginaData here

		this.engineData = engineData;
		infrastructure.getPluginLogger().logInfo("Setting engine data:\n '" + engineData + "'...");
	}

	@Override
	public Object startEngine() throws MOGLiPluginException {
		// TODO implement the engine's job here
		return null;
	}

}
