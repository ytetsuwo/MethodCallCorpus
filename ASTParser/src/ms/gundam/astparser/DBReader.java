package ms.gundam.astparser;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class DBReader {
	public static void main(String args[]) {
		DBReader reader = new DBReader();
		if (args.length != 4) {
			System.err.println("Specify DB diretory, Classname, Methodname, and whcih DB");
			return;
		}
		File dbDir = new File(args[0]);
		if (!dbDir.exists()) {
			System.err.println("No such direcotry" + args[0]);
			return;
		}
		boolean which = true;
		switch (args[3].charAt(0)) {
		case 'a':
			which = true;
			break;
		case 'b':
			which = false;
			break;
		} 
		reader.read(dbDir, args[1], args[2], which);
	}

	private void read(File dbDir, String classname, String methodname, boolean which) {
		DB db = new DB();
		db.open(dbDir, true);
		List<Value> list = db.get(classname, methodname, which);
		Collections.sort(list);
		for (Value v : list) {
			System.out.println(v.toString());
		}
	}
}
