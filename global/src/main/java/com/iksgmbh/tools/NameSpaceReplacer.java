package com.iksgmbh.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.iksgmbh.utils.FileUtil;

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
public class NameSpaceReplacer {

	private static final String defaultWorkspaceDir = "D:\\Reik\\dev\\git\\automobile\\automobile repo2";
	private static final String sourceNamespace = "de.iks_gmbh.automobile";
	private static final String targetNamespace = "com.iksgmbh.moglicc";
	private static final String[] directoriesToIgnore = { "target", ".git" };
	private static final String[] filesToParse = { ".xml", ".txt", ".java", ".properties", ".mf", 
		".exsd", ".product", ".project" };


	public static void main(String[] args) {
		try {
			System.out.println("Starting NamespaceReplacer...");
			System.out.println("");
			final String workspaceDir = getWorkspaceDir(args);
			(new NameSpaceReplacer()).replaceWorkspace(workspaceDir);
			System.out.println("");
			System.out.println("Done!");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	private static String getWorkspaceDir(final String[] args) {
		if (args == null || args.length < 1) {
			return defaultWorkspaceDir;
		}
		return args[0];
	}
	
	
	private String sourcePackageStructure;
	private String targetPackageStructure;

	
	public void replaceWorkspace(final String workspaceDir) throws Exception {		
		System.out.println("");
		sourcePackageStructure  = sourceNamespace.replace('.', '\\');
		targetPackageStructure  = targetNamespace.replace('.', '\\');

		final File workspace = new File(workspaceDir);
		final File[] files = workspace.listFiles();
		if (files == null) {
			throw new IllegalArgumentException("workspace Directory not found: " + workspace.getAbsolutePath());
		}
		for (int i = 0; i < files.length; i++) {
			System.out.println("");
			File file = files[i];
			if (file.isDirectory()) {
				replaceDirectory(file, workspaceDir);
			} else {
				replaceFile(file);
			}
		}
	}

	private void replaceDirectory(final File dir, final String targetDirPath) throws Exception {
		assert (dir.isDirectory());

		if (isSourceDirectory(dir)) {
			replaceJavaDirectory(dir);
			return;
		}
		
		if (! isDirectoryToIgnore(dir.getName())) {
			final File[] children = dir.listFiles();
			if (children != null) {
				for (int i = 0; i < children.length; i++) {
					final File childOfDir = children[i];
					if (childOfDir.isDirectory()) {
						replaceDirectory(childOfDir, targetDirPath);
					} else {
						replaceFile(childOfDir);
					}
				}
			}
		}
	}

	private void replaceJavaDirectory(File sourceDir) throws Exception {
		assert(sourceDir.exists());
		final File targetDir;
		
		if (isSourceDirectory(sourceDir)) {
			System.out.println("Package-Struktur des Verzeichnisses '" + sourceDir + "' wird " +
					           "ge�ndert von <" + sourcePackageStructure + "> auf <" + targetPackageStructure + ">.");
		}
		
		final String targetPath = sourceDir.getAbsolutePath().replace(sourcePackageStructure, targetPackageStructure);
		targetDir = new File(targetPath);
		
		FileUtil.deleteDirWithContent(targetDir);
		
		final File[] children = sourceDir.listFiles();
		for (int i = 0; i < children.length; i++) {
			if (children[i].isDirectory()) {
				replaceJavaDirectory(children[i]);
			} else {
				replaceFile(children[i], targetDir);
			}
		}
	}
	
	private boolean isSourceDirectory(final File sourceDir) {
		return sourceDir.getAbsolutePath().endsWith(sourcePackageStructure);
	}

	private void replaceFile(final File sourceFile) throws Exception {
		replaceFile(sourceFile, sourceFile.getParentFile());
	}
	
	private void replaceFile(final File sourceFile, final File targetDir) throws Exception {
		assert (sourceFile.isFile());

		if (isFileToParse(sourceFile)) {
			final ArrayList<String> targetFileContent = replaceContent(sourceFile);

			sourceFile.delete();
			
			targetDir.mkdirs();
			final File targetFile = new File(targetDir, sourceFile.getName());
			if (!targetFile.createNewFile())
			{
				throw new RuntimeException("folgende Datei kann nicht erzeugt werden: " + targetFile.getAbsoluteFile());
			}
			System.out.println(targetFile.getAbsolutePath());
			PrintWriter writer = new PrintWriter(targetFile);
			for (int i = 0; i < targetFileContent.size(); i++) {
				writer.println(targetFileContent.get(i));
			}
			writer.close();
		}
	}

	protected ArrayList<String> replaceContent(final File file) throws FileNotFoundException, IOException {
		final ArrayList<String> targetFileContent = new ArrayList<String>();
		final String pathPlusFileName = file.getAbsolutePath();
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
			System.out.println("In der Datei " + file.getAbsoluteFile() + " gab es " + numMatches + " Ersetzungen.");
		}
		
		return targetFileContent;
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

}
