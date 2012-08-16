package ms.gundam.astparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileAnalyzer {
	private StringBuffer sb = new StringBuffer();
	private String packagename = null;
	private String fqdn = null;
	private String sourcepath[]  = new String[1];
	private final String separator = File.separator.equals("\\") ? "\\\\" : File.separator;
	
	public void analyze(File file) {
		String pathname = null;
		try {
			BufferedReader br;
			br = new BufferedReader(new InputStreamReader( new FileInputStream(file)));
			String line;
			Pattern p = Pattern.compile("package ([^\\s]*) *;");
			boolean matchPackage = false;
			while ((line = br.readLine()) != null){
				if (!matchPackage) {
					Matcher m = p.matcher(line);
					if (m.matches()) {
						packagename = m.group(1);
						Matcher match = Pattern.compile("\\.").matcher(packagename);
						pathname = match.replaceAll(separator);
						matchPackage = true;
					}
				}
				sb.append(line+"\n");
			}
		} catch (FileNotFoundException e) {
		    System.err.println("File " + file.getAbsolutePath() + " not found.");
	    	System.exit(1);
		} catch (IOException e) {
		    System.err.println("File " + file.getAbsolutePath() + " I/O Error.");
	    	System.exit(1);
		}
		Matcher matchpath = null;
		if (pathname != null) {
			String quotepath = java.util.regex.Pattern.quote(pathname);
			matchpath = Pattern.compile(quotepath+separator+file.getName()).matcher(file.getAbsolutePath());
		} else {
			matchpath = Pattern.compile(file.getName()).matcher(file.getAbsolutePath());
		}
		sourcepath[0] = matchpath.replaceAll("");
		String classname[] = file.getName().split("\\.java");
		if (packagename != null)
			fqdn = packagename + "." +  classname[0];
		else
			fqdn = classname[0];
	}

	public StringBuffer getSb() {
		return sb;
	}

	public String getFQDN() {
		return fqdn;
	}

	public String[] getSourcepath() {
		return sourcepath;
	}
}
