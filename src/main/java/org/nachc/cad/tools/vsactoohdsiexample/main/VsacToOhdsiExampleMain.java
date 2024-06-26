package org.nachc.cad.tools.vsactoohdsiexample.main;

import java.io.File;
import java.sql.Connection;

import org.nachc.cad.tools.vsactoohdsiexample.util.connection.VsactToOhdsiExampleConnectionFactory;
import org.nachc.cad.tools.vsactoohdsiexample.util.vsactoohdsi.VsacToOhdsiExample;
import org.yaorma.database.Database;

import com.nach.core.util.file.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VsacToOhdsiExampleMain {

	private static final String TEST_FILE_NAME = "./test/zip/RetrieveMultipleValueSets_2.16.840.1.113762.1.4.1235.350.txt.zip";

	public static void main(String[] args) {
		Connection conn = null;
		try {
			log.info("Getting connection...");
			conn = VsactToOhdsiExampleConnectionFactory.getConnection();
			log.info("Testing connection...");
			VsactToOhdsiExampleConnectionFactory.testConnection(conn);
			log.info("Getting file...");
			File file = getFile(args);
			log.info("Generating OHDSI ids for VSAC concepts...");
			VsacToOhdsiExample.getConceptIdList(file, conn);
		} finally {
			log.info("Closing connection...");
			Database.close(conn);
			log.info("Connection closed.");
		}
		log.info("Done.");
	}

	private static File getFile(String[] args) {
		if (args == null || args.length == 0) {
			log.info("! ! ! FILE NAME NOT PROVIDED, USING TEST FILE ! ! !");
			File rtn = FileUtil.getFile(TEST_FILE_NAME);
			log.info("Using default file: " + FileUtil.getCanonicalPath(rtn));
			log.info("File Exists: " + rtn.exists());
			return rtn;
		} else {
			File rtn = new File(args[0]);
			log.info("Using user specifified file: " + FileUtil.getCanonicalPath(rtn));
			log.info("File Exists: " + rtn.exists());
			return rtn;
		}
	}

}
