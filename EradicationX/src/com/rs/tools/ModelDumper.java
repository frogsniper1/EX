package com.rs.tools;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.alex.store.Index;
import com.alex.store.Store;
import com.rs.cache.Cache;

public class ModelDumper {

	public static void main(String[] args) throws IOException {
		Cache.STORE = new Store(
				"C:/Users/Zar/Desktop/EradicationX/data/cache/",
				false);
		Index index = Cache.STORE.getIndexes()[7];
		System.out.println(73945);
		for (int i = 0; i < 73945; i++) {
			byte[] data = index.getFile(i);
			if (data == null)
				continue;
			 if(!(data[data.length + -1] == -1 && data[-2 + data.length] ==
			 -1))
			 if((data[-1 + data.length] ^ 0xffffffff) != 0 || data[-2 +
			 data.length] != -1)
			 System.out.println(i);
			writeFile(data, "C:/Users/Zar/Desktop/New folder/" + i
					+ ".dat");
		}

	}

	public static void writeFile(byte[] data, String fileName)
			throws IOException {
		OutputStream out = new FileOutputStream(fileName);
		out.write(data);
		out.close();
	}

}
