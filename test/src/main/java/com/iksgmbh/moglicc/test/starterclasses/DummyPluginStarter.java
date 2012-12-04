package com.iksgmbh.moglicc.test.starterclasses;

import java.util.ArrayList;
import java.util.List;

import com.iksgmbh.moglicc.core.InfrastructureService;
import com.iksgmbh.moglicc.exceptions.MOGLiPluginException2;
import com.iksgmbh.moglicc.plugin.MOGLiPlugin2;
import com.iksgmbh.moglicc.plugin.type.basic.Generator;

public class DummyPluginStarter implements Generator, MOGLiPlugin2 {

	private static final String PLUGIN_ID = "DummyPlugin";
	private InfrastructureService infrastructure;

	public String getId() {
		return PLUGIN_ID;
	}

	@Override
	public PluginType getPluginType() {
		return MOGLiPlugin2.PluginType.GENERATOR;
	}

	@Override
	public void doYourJob() throws MOGLiPluginException2 {
		throw new MOGLiPluginException2("Testfehler");
	}

	@Override
	public void setMOGLiInfrastructure(final InfrastructureService infrastructure) {
		this.infrastructure = infrastructure;
	}

	@Override
	public List<String> getDependencies() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("OtherPlugin");
		return toReturn;
	}

	@Override
	public boolean unpackDefaultInputData() throws MOGLiPluginException2 {
		return false;
	}

	@Override
	public InfrastructureService getMOGLiInfrastructure() {
		return infrastructure;
	}
	
	@Override
	public boolean unpackPluginHelpFiles() throws MOGLiPluginException2 {
		return false;
	}

}
