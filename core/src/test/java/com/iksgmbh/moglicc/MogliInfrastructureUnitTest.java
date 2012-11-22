package com.iksgmbh.moglicc;

import static com.iksgmbh.moglicc.MogliSystemConstants.DIR_INPUT_FILES;
import static com.iksgmbh.moglicc.MogliSystemConstants.DIR_LOGS_FILES;
import static com.iksgmbh.moglicc.MogliSystemConstants.DIR_OUTPUT_FILES;
import static com.iksgmbh.moglicc.MogliSystemConstants.DIR_TEMP_FILES;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.iksgmbh.moglicc.data.InfrastructureInitData;
import com.iksgmbh.moglicc.infrastructure.MogliInfrastructure;
import com.iksgmbh.moglicc.plugin.PluginExecutable;
import com.iksgmbh.moglicc.plugin.type.basic.DataProvider;
import com.iksgmbh.moglicc.plugin.type.basic.EngineProvider;
import com.iksgmbh.moglicc.plugin.type.basic.Generator;
import com.iksgmbh.moglicc.plugin.type.basic.ModelProvider;
import com.iksgmbh.moglicc.test.CoreTestParent;
import com.iksgmbh.moglicc.utils.MogliFileUtil;
import com.iksgmbh.utils.FileUtil;

public class MogliInfrastructureUnitTest extends CoreTestParent {
	
	public static final String PLUGIN_TEST_ID = "PLUGIN_TEST_ID";
	public static final String TEST_PROPERTY = "Test";
	
	private InfrastructureInitData infrastructureInitData;

	@Before
	public void setup() {
		super.setup();
		final List<PluginExecutable> plugins = getPluginListForTest();
		initPropertiesWith(PLUGIN_TEST_ID + " = " + TEST_PROPERTY);
		infrastructureInitData = createInfrastructureInitData(applicationProperties, null, null);
		infrastructureInitData.pluginList = plugins;
	}
	
	// *****************************  test methods  ************************************
	
	@Test
	public void returnsMogliProperty() {
		// prepare test
		infrastructureInitData.idOfThePluginToThisInfrastructure = PLUGIN_TEST_ID;
		final MogliInfrastructure infrastructure = new MogliInfrastructure(infrastructureInitData);
		
		// call functionality under test
		final String property = infrastructure.getWorkspaceProperty(PLUGIN_TEST_ID);

		// verify test result
		assertNotNull(property);
		assertStringEquals("property", TEST_PROPERTY, property);
	}
	
	@Test
	public void returnsLogDir() {
		// prepare test
		infrastructureInitData.idOfThePluginToThisInfrastructure = PLUGIN_TEST_ID;
		final MogliInfrastructure infrastructure = new MogliInfrastructure(infrastructureInitData);
		final File logsDir = MogliFileUtil.getNewFileInstance(DIR_LOGS_FILES);
		FileUtil.deleteDirWithContent(logsDir);
		assertFalse("LogDir not deleted: " + logsDir.getAbsolutePath(), logsDir.exists());
		
		// call functionality under test
		File actualLogsDir = infrastructure.getWorkspaceLogsDir();

		// verify test result
		assertStringEquals("Wrong logDir created", logsDir.getAbsolutePath(), actualLogsDir.getAbsolutePath());
	}

	@Test
	public void returnsPluginInputDir() {
		// prepare test
		infrastructureInitData.idOfThePluginToThisInfrastructure = PLUGIN_TEST_ID;
		final MogliInfrastructure infrastructure = new MogliInfrastructure(infrastructureInitData);
		
		// call functionality under test
		File actualPluginInputDir = infrastructure.getPluginInputDir();

		// verify test result
		final File applicationInputDir = MogliFileUtil.getNewFileInstance(DIR_INPUT_FILES);
		assertStringEquals("Wrong logDir created", applicationInputDir.getAbsolutePath() + "\\" + PLUGIN_TEST_ID, actualPluginInputDir.getAbsolutePath());
	}
	
	@Test
	public void createsPluginTempDir() {
		// prepare test
		infrastructureInitData.idOfThePluginToThisInfrastructure = PLUGIN_TEST_ID;
		final MogliInfrastructure infrastructure = new MogliInfrastructure(infrastructureInitData);
		final File tempDir = MogliFileUtil.getNewFileInstance(DIR_TEMP_FILES);
		FileUtil.deleteDirWithContent(tempDir);
		assertFileDoesNotExist(tempDir);
		
		// call functionality under test
		final File actualPluginTempDir = infrastructure.getPluginTempDir();
		
		// verify test result
		assertStringEquals("Wrong tempDir created", tempDir.getAbsolutePath() + "\\" + PLUGIN_TEST_ID, actualPluginTempDir.getAbsolutePath());
	}

	@Test
	public void createsWorkspaceTempDir() {
		// prepare test
		infrastructureInitData.idOfThePluginToThisInfrastructure = PLUGIN_TEST_ID;
		final MogliInfrastructure infrastructure = new MogliInfrastructure(infrastructureInitData);
		final File tempDir = MogliFileUtil.getNewFileInstance(DIR_TEMP_FILES);
		FileUtil.deleteDirWithContent(tempDir);
		assertFileDoesNotExist(tempDir);
		
		// call functionality under test
		final File actualWorkspaceTempDir = infrastructure.getWorkspaceTempDir();
		
		// verify test result
		assertStringEquals("Wrong tempDir created", tempDir.getAbsolutePath(), actualWorkspaceTempDir.getAbsolutePath());
	}

	@Test
	public void createsPluginOutputDir() {
		// prepare test
		infrastructureInitData.idOfThePluginToThisInfrastructure = PLUGIN_TEST_ID;
		final MogliInfrastructure infrastructure = new MogliInfrastructure(infrastructureInitData);
		final File resultDir = MogliFileUtil.getNewFileInstance(DIR_OUTPUT_FILES);
		FileUtil.deleteDirWithContent(resultDir);
		assertFileDoesNotExist(resultDir);
		
		// call functionality under test
		final File pluginResultDir = infrastructure.getPluginOutputDir();
		
		// verify test result
		assertFileExists(pluginResultDir);
		assertStringEquals("PluginResultDir", resultDir.getAbsolutePath() + "\\" + PLUGIN_TEST_ID, pluginResultDir.getAbsolutePath());
	}


	@Test
	public void createsPluginLogFile() throws IOException {
		// prepare test
		infrastructureInitData.idOfThePluginToThisInfrastructure = PLUGIN_TEST_ID;
		final MogliInfrastructure infrastructure = new MogliInfrastructure(infrastructureInitData);
		final File logDir = MogliFileUtil.getNewFileInstance(DIR_LOGS_FILES);
		FileUtil.deleteDirWithContent(logDir);
		assertFileDoesNotExist(logDir);
		
		// call functionality under test
		final File pluginLogFile = infrastructure.getPluginLogFile();
	
		// verify test result
		assertFileExists(pluginLogFile);
		assertStringEquals("PluginResultDir", logDir.getAbsolutePath() + "\\" + PLUGIN_TEST_ID + ".log", pluginLogFile.getAbsolutePath());
	}

	@Test
	public void returnsGenerator() {
		// prepare test
		infrastructureInitData.idOfThePluginToThisInfrastructure = PLUGIN_TEST_ID;
		final MogliInfrastructure infrastructure = new MogliInfrastructure(infrastructureInitData);
		
		// call functionality under test
		Generator generator = infrastructure.getGenerator("null");
		
		// verify test result
		assertNull("Null expected", generator);
		generator = infrastructure.getGenerator("DummyGenerator");
		assertNotNull("Not null expected", generator);
	}

	@Test
	public void returnsModelProvider() {
		// prepare test
		infrastructureInitData.idOfThePluginToThisInfrastructure = PLUGIN_TEST_ID;
		final MogliInfrastructure infrastructure = new MogliInfrastructure(infrastructureInitData);
		
		// call functionality under test
		ModelProvider modelProvider = infrastructure.getModelProvider("null");
		
		// verify test result
		assertNull("Null expected", modelProvider);
		modelProvider = infrastructure.getModelProvider("StandardModelProvider");
		assertNotNull("Not null expected", modelProvider);
	}
	
	@Test
	public void returnsDataProvider() {
		// prepare test
		infrastructureInitData.idOfThePluginToThisInfrastructure = PLUGIN_TEST_ID;
		final MogliInfrastructure infrastructure = new MogliInfrastructure(infrastructureInitData);
		
		// call functionality under test
		DataProvider dataProvider = infrastructure.getDataProvider("null");
			
		// verify test result
		assertNull("Null expected", dataProvider);
		dataProvider = infrastructure.getDataProvider("DummyDataProvider");
		assertNotNull("Not null expected", dataProvider);
	}

	@Test
	public void returnsEngineProvider() {
		// prepare test
		infrastructureInitData.idOfThePluginToThisInfrastructure = PLUGIN_TEST_ID;
		final MogliInfrastructure infrastructure = new MogliInfrastructure(infrastructureInitData);
	
		// call functionality under test
		EngineProvider engineProvider = infrastructure.getEngineProvider("null");
		
		// verify test result
		assertNull("Null expected", engineProvider);
		engineProvider = infrastructure.getEngineProvider("VelocityEngineProvider");
		assertNotNull("Not null expected", engineProvider);
	}

}