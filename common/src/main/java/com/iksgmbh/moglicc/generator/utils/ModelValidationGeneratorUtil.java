package com.iksgmbh.moglicc.generator.utils;

import com.iksgmbh.moglicc.exceptions.MogliPluginException;

public class ModelValidationGeneratorUtil {

	public static boolean validateModel(final String nameOfValidModel, final String modelName) throws MogliPluginException {
		if (nameOfValidModel == null) {
			return true; // ok, template has to be applied to all models
		}
		if (nameOfValidModel.equals(modelName)) {
			return true; // ok, template has to be applied to this model				
		}
		
		return false;
	}

}