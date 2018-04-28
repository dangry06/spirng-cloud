package com.aspire.imp.lucene;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public final class LuceneCenter {
	public static Directory directory;
	public static Analyzer analyzer;
	public static IndexWriterConfig idxWriterConfig;
	
	static {
		try {
			directory = FSDirectory.open(Paths.get("./LuceneIndex"));
			analyzer = new StandardAnalyzer();
			idxWriterConfig = new IndexWriterConfig(analyzer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
