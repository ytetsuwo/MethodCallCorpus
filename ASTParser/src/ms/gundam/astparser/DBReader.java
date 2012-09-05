package ms.gundam.astparser;

import java.io.File;
import java.util.Collections;
import java.util.List;

import ms.gundam.astparser.DB.DB;
import ms.gundam.astparser.DB.Value;

/**
 * 指定したクラス名とメソッド名の後か前にある呼び出し文一覧をDBから取得し表示
 * 引数
 *  一つ目 DBのディレクトリ
 *  二つ目 クラス名
 *  三つ目 メソッド名
 *  四つ目 前から後か．それぞれ，a か b を指定 
 */
public class DBReader {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
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

	/**
	 * Read.
	 *
	 * @param dbDir DBのディレクトリ
	 * @param classname クラス名
	 * @param methodname メソッド名
	 * @param which trueなら後に続く呼び出し文一覧，falseなら前にある呼び出し文一覧
	 */
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
