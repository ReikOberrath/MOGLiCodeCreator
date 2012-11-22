package com.iksgmbh.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Helfer, um den Namespace von Eclipse-Projekten in einem Workspace zu �ndern, z.B.
 * von "de.creditreform.aaa.orbis.client.rcp" in "de.iks.orbis.client".
 * 
 * Daf�r sind drei unterschiedliche Dinge zu tun: 
 * 1. Die Namen der Projektverzeichnisse m�ssen ge�ndert werden. 
 * 2. Die Package-Struktur innerhalb des src-Verzeichnisses muss ge�ndert werden. 
 * 3. In allen Dateien, die in ihrem Namen oder ihren Inhalt den Namespace enthalten,
 * 	  muss dieser ge�ndert werden.
 * 
 * Hinweise: 
 * 1. Es wird grunds�tzlich immer der ganze Workspace ersetzt.
 * 2. Die Bezeichnung des Namespace beginnt immer mit der obersten Ebene z.B. de oder com. 
 * 3. Die Bezeichnung des Namespace h�rt immer auf der "untersten Ebene" auf. 
 * 4. Die unterste Ebene des Namespaces endet auf dem letzten gemeinsamen Abschnitt der Projektnamen, z.B. client oder rcp.
 * 5. In der Verzeichnis-Hierarchie gibt es auf der oben genannten "untersten Ebene" des Namespaces noch
 *    keine Dateien, sondern erst in tieferen Verzeichnis-Ebenen. 
 * 6. Existiert das Verzeichnis des Ziel-Workspaces nicht, wird es erzeugt.
 * 7. Ist das Verzeichnis des Ziel-Workspaces nicht leer, wird kann es automatisch geleert werden (siehe initTarget).
 * 8. Der Quell-Workspace wird grunds�tzlich nicht ver�ndert!
 * 
 * @author OberratR
 */
public class NameSpaceReplacingWorkspaceDuplicator {

	private static final String defaultSourceWorkspaceDir = "C:/mystuff/howareyou release/sourcen2/howareyou2";
	private static final String defaultTargetWorkspaceDir = "C:/mystuff/howareyou release/sourcen2/howareyou2a";
	private static final String sourceNamespace = "com.iksgmbh.automobile";
	private static final String targetNamespace = "com.iksgmbh.moglicc";
	private static final String srcDir = "src";
	private static final String[] directoriesToIgnore = { "target" };
	private static final String[] filesToParse = { ".xml", ".txt", ".java", ".properties", ".mf", 
		".exsd", ".product", ".project" };


	public static void main(String[] args) {
		try {
			System.out.println("Starting NamespaceReplacer...");
			System.out.println("");
			final String sourceWorkspaceDir = getSourceWorkspaceDir(args);
			final String targetWorkspaceDir = getTargetWorkspaceDir(args);
			(new NameSpaceReplacingWorkspaceDuplicator()).replace(sourceWorkspaceDir, targetWorkspaceDir);
			System.out.println("");
			System.out.println("Done!");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	private static String getTargetWorkspaceDir(final String[] args) {
		if (args == null || args.length < 1) {
			return defaultTargetWorkspaceDir;
		}
		
		if (args.length == 1) {
			return args[0];
		}
		
		return args[1];
	}

	private static String getSourceWorkspaceDir(final String[] args) {
		if (args == null || args.length < 1) {
			return defaultSourceWorkspaceDir;
		}
	
		return args[0];
	}

	public void replace(final String sourceWorkspaceDir, final String targetWorkspaceDir) throws Exception {
		initTarget(targetWorkspaceDir);
		System.out.println("");
		final File source = new File(sourceWorkspaceDir);
		final File[] files = source.listFiles();
		System.out.println(source.getCanonicalPath());
		if (files == null) {
			throw new IllegalArgumentException("Source Directory not found: " + source.getAbsolutePath());
		}
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.isDirectory()) {
				replaceDirectory(file, targetWorkspaceDir);
			} else {
				// Dateien auf der Ebene des Workspace-Verzeichnisses werden
				// ignoriert
			}
		}
	}

	private void replaceDirectory(File sourceDir, String targetDirPath) throws Exception {
		assert (sourceDir.isDirectory());

		String name = sourceDir.getName();
		if (srcDir.equals(name)) {
			replaceJavaDirectory(sourceDir, targetDirPath);
			return;
		}

		String newName = replaceName(name, true);
		File targetDir = new File(targetDirPath + "/" + newName);
		//System.out.println("Neues Verzeichnis: " + targetDir.getAbsoluteFile());
		boolean ok = targetDir.mkdir();
		if (!ok) {
			throw new Exception(
					"Verzeichnis konnte nicht erstellt werden: "
							+ targetDir.getAbsolutePath());
		}
		
		if (! isDirectoryToIgnore(name)) {
			replaceDirectoryContent(sourceDir, targetDir);
		} else {
			//class-Dateien aus dem source-Verzeichnis werden ignoriert
		}
	}

	private void replaceJavaDirectory(File sourceDir, String targetDirPath) throws Exception {
		String sourcePackageStructure  = sourceNamespace.replace('.', '/');
		String targetPackageStructure  = targetNamespace.replace('.', '/');
		sourceDir = new File(sourceDir.getAbsoluteFile() + "/" + sourcePackageStructure);
		assert(sourceDir.exists());
		
		File targetDir = new File(targetDirPath + "/" + srcDir + "/" + targetPackageStructure);
		boolean ok = targetDir.mkdirs();
		if (!ok) {
			throw new Exception("Verzeichnis konnte nicht erstellt werden: "
							+ targetDir.getAbsolutePath());
		}
		System.out.println("Package-Struktur im " + srcDir + "-Verzeichnis ge�ndert von <" + sourcePackageStructure + "> auf <" + targetPackageStructure + ">.");
		
		File[] children = sourceDir.listFiles();
		for (int i = 0; i < children.length; i++) {
			replaceDirectory(children[i], targetDir.getAbsolutePath());
		}
	}

	private String replaceName(String sourceName, boolean isDirectory) {
		String targetName = sourceName;
		int pos = sourceName.indexOf(sourceNamespace);
		if (pos > -1) {
			targetName = targetNamespace + sourceName.substring(pos + sourceNamespace.length());
			if (isDirectory) {
				System.out.println("");
				System.out.println("Der Verzeichnisname <" + sourceName + "> wurde ersetzt durch <" + targetName + ">.");
			} else {
				System.out.println("Die Datei <" + sourceName + "> wurde umbenannt in <" + targetName + ">.");
			}
		}
		return targetName;
	}

	private void replaceDirectoryContent(File sourceDir, File targetDir)
			throws Exception {
		File[] children = sourceDir.listFiles();
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				File childOfSourceDir = children[i];
				if (childOfSourceDir.isDirectory()) {
					replaceDirectory(childOfSourceDir, targetDir.getAbsolutePath());
				} else {
					replaceFile(childOfSourceDir, targetDir.getAbsolutePath());
				}
			}
		}
	}

	private void copyFile(File inputFile, File outputFile)
			throws FileNotFoundException, IOException {
		FileReader in = new FileReader(inputFile);
		FileWriter out = new FileWriter(outputFile);
		int c;

		while ((c = in.read()) != -1)
			out.write(c);

		in.close();
		out.close();
	}

	private void replaceFile(File sourceFile, String targetDirPath) throws Exception {
		assert (sourceFile.isFile());
		String newName = replaceName(sourceFile.getName(), false);
		File targetFile = new File(targetDirPath + "/" + newName);
		if (isFileToParse(sourceFile)) {
			replaceFileContent(sourceFile, targetFile);
		} else {
			copyFile(sourceFile, targetFile);
		}
	}

	private void replaceFileContent(File sourceFile, File targetFile) throws Exception {
		final ArrayList<String> targetFileContent = new ArrayList<String>();
		final String pathPlusFileName = sourceFile.getAbsolutePath();
		final BufferedReader reader = new BufferedReader(new FileReader(pathPlusFileName));
		//System.out.println("Reading: " + pathPlusFileName + "...");
		String line = reader.readLine();
		String replacedLine = null;
		int numMatches = 0;

		while (line != null) 
		{
			int pos = line.indexOf(sourceNamespace);
			if (pos > -1) {
				replacedLine = line;
				while (pos > -1) {
					replacedLine = replacedLine.substring(0, pos) + targetNamespace
							+ replacedLine.substring(pos + sourceNamespace.length());
					numMatches++;
					pos = replacedLine.indexOf(sourceNamespace);
				}
				targetFileContent.add(replacedLine);
			}
			else
			{
				targetFileContent.add(line);
			}
			line = reader.readLine();
		}
				 
		reader.close();
		if (numMatches > 0) {
			System.out.println("In der Datei " + targetFile.getAbsoluteFile() + " gab es " + numMatches + " Ersetzungen.");
		}
		
		if (!targetFile.createNewFile())
		{
			throw new RuntimeException("folgende Datei kann nicht erzeugt werden: " + targetFile.getAbsoluteFile());
		}
	
		PrintWriter writer = new PrintWriter(targetFile);
		for (int i = 0; i < targetFileContent.size(); i++) {
			writer.println(targetFileContent.get(i));
		}
		writer.close();
	}

	private boolean isFileToParse(final File file) {
		String name = file.getName();
		int pos = name.lastIndexOf('.');
		String ext = name.substring(pos);
		for (int i = 0; i < filesToParse.length; i++) {
			if (filesToParse[i].equalsIgnoreCase(ext)) {
				return true;
			}
		}
		return false;
	}

	private boolean isDirectoryToIgnore(final String name) {
		for (int i = 0; i < directoriesToIgnore.length; i++) {
			if (directoriesToIgnore[i].equals(name)) {
				return true;
			}
		}
		return false;
	}

	private void initTarget(final String targetWorkspaceDir) throws Exception {
		File target = new File(targetWorkspaceDir);
		if (!target.exists()) {
			boolean ok = target.mkdir();
			if (!ok) {
				throw new Exception(
						"Target existiert nicht und konnte nicht erzeugt werden: "
								+ target.getAbsolutePath());
			}
			System.out.println("Target Workspace wurde erzeugt.");
			return;
		}

		File[] files = target.listFiles();
		if (files == null || files.length == 0) {
			System.out.println("Target Workspace ist initialisiert.");
			return;
		}

		for (int i = 0; i < files.length; i++) {
			deleteDir(files[i]);
		}
		System.out.println("Target-Workspace wurde bereinigt.");
	}

	private void deleteDir(File dir) throws Exception {
		File[] files = dir.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDir(files[i]); // Verzeichnis leeren und anschlie�end
					// l�schen
				} else {
					boolean ok = files[i].delete(); // Datei l�schen
					if (!ok) {
						throw new Exception(
								"Datei konnte nicht gel�scht werden: "
										+ files[i].getAbsolutePath());
					}
				}
			}
			dir.delete(); // Ordner l�schen
		}
	}
}