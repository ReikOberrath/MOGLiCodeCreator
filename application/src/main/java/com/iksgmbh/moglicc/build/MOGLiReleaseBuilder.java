package com.iksgmbh.moglicc.build;

import static com.iksgmbh.moglicc.MOGLiSystemConstants.DIR_LIB;
import static com.iksgmbh.moglicc.MOGLiSystemConstants.DIR_PLUGIN;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.iksgmbh.moglicc.build.helper.ReleaseFileCollector;
import com.iksgmbh.moglicc.build.helper.ReleaseFileCollector.FileCollectionData;
import com.iksgmbh.moglicc.build.helper.MavenExecutor;
import com.iksgmbh.moglicc.build.helper.MavenExecutor.MavenData;
import com.iksgmbh.moglicc.build.helper.VersionReplacer;
import com.iksgmbh.moglicc.exceptions.MOGLiCoreException;
import com.iksgmbh.utils.FileUtil;
import com.iksgmbh.utils.ImmutableUtil;
import com.iksgmbh.utils.ZipUtil;

public class MOGLiReleaseBuilder {
	
	public static final String FILENAME_BUILD_PROPERTIES = "build.properties";
	public static final String FILENAME_STARTBAT = "startMOGLiCodeCreator.bat";
	public static final String FILENAME_README = "readme.htm";
	public static final String RELEASE_DATA_SOURCE_SUBDIR = "release";
	public static final String RELEASE_ARCHIV_DIR = "releasedBuilds";
	
	public static final String PROPERTY_CURRENT_VERSION = "CurrentVersion";
	public static final String PROPERTY_RELEASE_VERSION = "ReleaseVersion";
	public static final String PROPERTY_NEXT_VERSION = "NextVersion";
	public static final String PROPERTY_RELEASE_DIR= "ReleaseDir";
	public static final String PROPERTY_MAVEN_INSTALL_DIR = "MavenRootDir";
	public static final String PROPERTY_MAVEN_REPOSITORY_DIR = "MavenRepositoryDir";

	public static final String USER_DIR = System.getProperty("user.dir");
	public static final File WORKSPACE = new File(USER_DIR).getParentFile();
	
	private static final String PARENT_MODULE = "parent";
	private static final String USER_DIR_PREFIX = "<RootDir>";
	private static final String MAVEN_INSTALL_DIR_PREFIX = "<MavenRootDir>";
	private static final String RELEASE_DEFAULT_DIR = USER_DIR_PREFIX + "/release";
	private static final String RELEASE_DEFAULT_FILENAME = "Mogli";
	private static final String ARTEFACT_GROUP_ID = "com.iksgmbh.moglicc";
	
	public static final List<String> FILES_TO_INSTALL_IN_ROOT = ImmutableUtil.getImmutableListOf(
			                          FILENAME_STARTBAT, FILENAME_README);  
	public static final List<String> CORE_MODULES = ImmutableUtil.getImmutableListOf(
			                          "global", "common", "core", "interfaces");  // basic mandatory modules for release 
	public static final List<String> PLUGIN_MODULES = ImmutableUtil.getImmutableListOf(
			                          "provider.model.standard", "provider.engine.velocity",
			                          "inserter.modelbased.velocity",
			                          "generator.classbased.velocity");  // optional modules, but relevant for release 
	public static final List<String> DEVELOPMENT_MODULES = ImmutableUtil.getImmutableListOf(
			                          PARENT_MODULE, "application", "test", "parent.plugin",
			                          "inttest");  // only necassary for the development infrastructure
	public static final List<String> THIRD_PARTY_LIBRARIES = ImmutableUtil.getImmutableListOf(
            "org/apache/velocity/velocity/1.7/velocity-1.7.jar", 
            "commons-lang/commons-lang/2.4/commons-lang-2.4.jar",
            "commons-collections/commons-collections/3.2.1/commons-collections-3.2.1.jar");  //mandatory for release 

	
	private static String applicationRootDir = USER_DIR + "/target/classes";
	private static String sourceDirForFilesToInstallInRoot = applicationRootDir + "/" 
	                                                         + RELEASE_DATA_SOURCE_SUBDIR;

	public enum VERSION_TYPE {Current, Release, Next};

	
	private Properties buildProperties;

	public static void main(String[] args) {
		try {
			final MOGLiReleaseBuilder releaseBuilder = new MOGLiReleaseBuilder();
			releaseBuilder.doYourJob();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	/*
	 * must be callable from PomVersionReplacer.main
	 */
	public MOGLiReleaseBuilder() {
		try {
			buildProperties = readBuildPropertiesFile();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Properties readBuildPropertiesFile() throws FileNotFoundException, IOException {
		final String propertiesPath = applicationRootDir + "/" + FILENAME_BUILD_PROPERTIES;
		final File propertiesFile = new File(propertiesPath);
		if (propertiesFile.exists()) {
			Properties buildProperties = new Properties();
			FileInputStream fileInputStream = new FileInputStream(propertiesPath);
			buildProperties.load(fileInputStream);
			return buildProperties;
		} else {
			throw new FileNotFoundException("Properties File not found: " + propertiesFile.getAbsolutePath());
		}
	}
	
	
	public boolean doYourJob() {
		boolean toReturn = true;
		final String[] pomFiles = getPomFiles();
		VersionReplacer.doYourJob(getVersion(VERSION_TYPE.Current), 
				                     getVersion(VERSION_TYPE.Release), pomFiles);
		
		final String result = MavenExecutor.doYourJob(new MavenData("clean install", getMavenRootDir(), 
				                getParentBuildDir()));
		if (MavenExecutor.EXECUTION_OK.equals(result)) {
			ReleaseFileCollector.doYourJob(createFileCollectionData());
			buildReleaseZipFile();
			copyReleaseToArchive();
		} else {
			System.out.println(result);
			System.out.println("###########################################");
			System.err.println("Terminated due to Maven Failure!");
			toReturn = false;
		}
		VersionReplacer.doYourJob(getVersion(VERSION_TYPE.Release), 
                                     getVersion(VERSION_TYPE.Next), pomFiles);		
		return toReturn;
	}

	protected void copyReleaseToArchive() {
		final File oldFile = new File(RELEASE_ARCHIV_DIR, getReleaseZipFile().getName());
		if (oldFile.exists()) {
			boolean ok = oldFile.delete();
			if (!  ok) {
				throw new RuntimeException("Cannot delete old file: " + oldFile.getAbsolutePath());
			}
		}
		FileUtil.copyBinaryFile(getReleaseZipFile(), oldFile);
	}

	void buildReleaseZipFile() {
		final File releaseDir = getReleaseDir();
		final File[] cleanUpFiles = releaseDir.listFiles();
		try {
			ZipUtil.zipDir(releaseDir.getAbsolutePath(), getReleaseZipFile().getAbsolutePath());
		} catch (Exception e) {
			throw new MOGLiCoreException("Unknown error creating releaseZipFile!", e);
		}
		FileUtil.deleteFiles(cleanUpFiles);
	}

	private FileCollectionData createFileCollectionData() {
		final FileCollectionData fileCollectionData = new FileCollectionData();
		fileCollectionData.libSubdir = DIR_LIB;
		fileCollectionData.pluginsSubdir = DIR_PLUGIN;
		fileCollectionData.sourceDir = new File(sourceDirForFilesToInstallInRoot);
		fileCollectionData.releaseDir = getReleaseDir();
		fileCollectionData.fileListForRootDir = FILES_TO_INSTALL_IN_ROOT;
		fileCollectionData.jarsOfCoreComponents = getJarFiles(getListOfCoreModules());
		fileCollectionData.jarsOfPlugins = getJarFiles(getListOfPluginModules());
		fileCollectionData.thirdPartyJars = getThirdPartyJars();
		return fileCollectionData;
	}

	public File getReleaseDir() {
		String releaseDir = buildProperties.getProperty(PROPERTY_RELEASE_DIR);
		if (releaseDir == null) {
			releaseDir = RELEASE_DEFAULT_DIR;
		} else {
			releaseDir = releaseDir.replace(USER_DIR_PREFIX, USER_DIR);
		}
		return new File(releaseDir);
	}
	
	public File getReleaseZipFile() {
		String releaseDir = buildProperties.getProperty(PROPERTY_RELEASE_DIR);
		if (releaseDir == null) {
			releaseDir = RELEASE_DEFAULT_DIR;
		} else {
			releaseDir = releaseDir.replace(USER_DIR_PREFIX, USER_DIR);
		}
		return new File(getReleaseDir(), RELEASE_DEFAULT_FILENAME + "-" 
				+ getVersion(VERSION_TYPE.Release) + ".zip");
	}

	
	public File[] getJarFiles(final List<String> mavenModuleList) {
		final File[] jarFiles = new File[mavenModuleList.size()];
		for (int i = 0; i < jarFiles.length; i++) {
			String artefactGroup = ARTEFACT_GROUP_ID;
			if ("global".equals(mavenModuleList.get(i))) {
				artefactGroup = "com.iksgmbh";
			}
			final String filename = getModuleParentDir(mavenModuleList.get(i)).getAbsolutePath() 
			              + "/target/" + artefactGroup + "." + mavenModuleList.get(i) + "-" 
			              +  getVersion(VERSION_TYPE.Release) + ".jar";
			jarFiles[i] = new File(filename);
		}
		return jarFiles;
	}
	
	public File[] getThirdPartyJars() {
		File[] files = new File[THIRD_PARTY_LIBRARIES.size()];
		for (int i = 0; i < files.length; i++) {
			String filename = getMavenRepositoryDir() + "/" + THIRD_PARTY_LIBRARIES.get(i);
			files[i] = new File(filename);
		}
		return files;
	}

	File getParentBuildDir() {
		return getModuleParentDir(PARENT_MODULE);
	}

	/*
	 * must be callable from PomVersionReplacer.main
	 */
	public String[] getPomFiles() {
		List<String> mavenModuleList = getListOfAllMavenModules();
		String[] pomFiles = new String[mavenModuleList.size()];
		for (int i = 0; i < pomFiles.length; i++) {
			pomFiles[i] = getModuleParentDir(mavenModuleList.get(i)).getAbsolutePath() 
			              + "/pom.xml";
		}
		return pomFiles;
	}

	public List<String> getListOfCoreModules() {
		return CORE_MODULES;
	}
	
	public List<String> getListOfPluginModules() {
		return PLUGIN_MODULES;
	}
	
	/**
	 * Returns list of all known Maven modules in the Mogli workspace
	 * @return
	 */
	@SuppressWarnings("unchecked")
	List<String> getListOfAllMavenModules() {
		return ImmutableUtil.getImmutableListFromLists(DEVELOPMENT_MODULES, PLUGIN_MODULES, CORE_MODULES);
	}
	
	File getModuleParentDir(String module) {
		return new File (WORKSPACE.getAbsolutePath() + "/" + module);
	}

	String getMavenRootDir() {
		String mavenPath = buildProperties.getProperty(PROPERTY_MAVEN_INSTALL_DIR);
		if (mavenPath == null) {
			throw new MOGLiCoreException("Unknown Maven install dir.");
		}
		return mavenPath;
	}
	

	String getMavenRepositoryDir() {
		String mavenPath = buildProperties.getProperty(PROPERTY_MAVEN_REPOSITORY_DIR);
		if (mavenPath == null) {
			throw new MOGLiCoreException("Unknown Maven rrepository dir.");
		}
		return mavenPath.replace(MAVEN_INSTALL_DIR_PREFIX, getMavenRootDir());
	}
	
	public String getVersion(VERSION_TYPE type) {
		String version = null;
		switch (type) {
			case Current: version = buildProperties.getProperty(PROPERTY_CURRENT_VERSION);
				 break;
			case Release: version = buildProperties.getProperty(PROPERTY_RELEASE_VERSION);
				 break;
			case Next: version = buildProperties.getProperty(PROPERTY_NEXT_VERSION);
				 break;
		}
		if (version == null) {
			throw new MOGLiCoreException("Unknown " + type.name() + " Version!");
		}
		return version;
	}
	
	/**
	 * FOR TEST PURPOSE ONLY
	 */
	Properties getProperties() {
		return buildProperties;
	}
	
	/**
	 * FOR TEST PURPOSE ONLY
	 */
	public static String getApplicationRootDir() {
		return applicationRootDir;
	}

	/**
	 * FOR TEST PURPOSE ONLY
	 */
	public static void setApplicationRootDir(String applicationRootDir) {
		MOGLiReleaseBuilder.applicationRootDir = applicationRootDir;
		MOGLiReleaseBuilder.sourceDirForFilesToInstallInRoot = 
			applicationRootDir + RELEASE_DATA_SOURCE_SUBDIR;
	}
}

