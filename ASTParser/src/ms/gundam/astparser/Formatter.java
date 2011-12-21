package ms.gundam.astparser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Formatter {
	public static String format(String sourcestr) {
		StringBuilder formattedstr = new StringBuilder();
		try {
			File tempfile = File.createTempFile("ast", ".java");
			OutputStream output = new FileOutputStream(tempfile);
  			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
   			writer.write(sourcestr.toString());
   			writer.close();

  			String[] cmdarray = {"/Users/tetsuo/bin/astyle", "-npUH", "--mode=java", tempfile.getAbsolutePath()};
   			ProcessBuilder b = new ProcessBuilder(cmdarray);
   			b.redirectErrorStream(true);
   			Process process = b.start();
   			process.waitFor();

   			InputStream input = new FileInputStream(tempfile);
   			BufferedReader br = new BufferedReader(new InputStreamReader(input));
   			String line;
   			while ((line = br.readLine()) != null) {
   				formattedstr.append(line);
   				formattedstr.append("\n");
   			}
   			br.close();
   			
   			tempfile.delete();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return formattedstr.toString();
	}
}
