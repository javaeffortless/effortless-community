package org.effortless.core;

import java.io.PrintWriter;
//import java.util.Arrays;

import org.apache.tika.metadata.Metadata;
import org.xml.sax.helpers.DefaultHandler;

public class NoDocumentMetHandler extends DefaultHandler {

    protected final Metadata metadata;

    protected PrintWriter writer;
    
    private boolean metOutput;

    public NoDocumentMetHandler(Metadata metadata, PrintWriter writer){
        this.metadata = metadata;
        this.writer = writer;
        this.metOutput = false;
    }
    
    @Override
    public void endDocument() {
//        String[] names = metadata.names();
//        Arrays.sort(names);
//        outputMetadata(names);
        writer.flush();
        this.metOutput = true;
    }
    
//    public void outputMetadata(String[] names) {
//       for (String name : names) {
//          writer.println(name + ": " + metadata.get(name));
//      }
//    }
    
    public boolean metOutput(){
        return this.metOutput;
    }
    
}
