package org.effortless.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

import org.effortless.core.Hex;
import org.effortless.core.ModelException;

import net.jpountz.xxhash.StreamingXXHash32;
import net.jpountz.xxhash.XXHash32;
import net.jpountz.xxhash.XXHashFactory;


public class FileHashes extends Object {

	protected FileHashes () {
		super();
		initiate();
	}
	
	protected void initiate () {
	}
	
	protected static FileHashes instance;
	
	public static FileHashes getInstance () {
		if (instance == null) {
			instance = new FileHashes();
		}
		return instance;
	}

	public static final boolean ENABLE_HASH_32 = true;
	
	
	public static final boolean ENABLE_HASH1 = true;
	public static final boolean ENABLE_HASH2 = false;
	public static final boolean ENABLE_HASH3 = false;

	
	protected CRC32 crc32;
	protected StreamingXXHash32 hash32;

    protected static final int HASH32_SEED = 0x9747b28c; // used to initialize the hash value, use whatever
	
	
	protected void initHash1 () {
    	if (ENABLE_HASH1) {
    		if (ENABLE_HASH_32) {
	    	    XXHashFactory factory = XXHashFactory.fastestJavaInstance();
	    	    this.hash32 = factory.newStreamingHash32(HASH32_SEED);
    		}
    	    if (this.hash32 == null) {
    	    	this.crc32 = new CRC32();
    	    }
    	}
	}
	
//	public String md5 (String text) {
//		String result = null;
//		if (text != null) {
//			if (this.md5 == null) {
//				try {
//					this.md5 = MessageDigest.getInstance("MD5");
//				} catch (NoSuchAlgorithmException e) {
//					throw new RuntimeException(e);
//				}
//			}
//			result = Hex.toHex(this.md5.digest(text.getBytes()));
//		}
//		return result;
//	}
	
	protected MessageDigest md5;

	protected void initHash2 () throws NoSuchAlgorithmException {
    	if (ENABLE_HASH2) {
    		this.md5 = MessageDigest.getInstance("MD5");
    	}
	}

	protected MessageDigest sha;
	
	protected void initHash3 () throws NoSuchAlgorithmException {
    	if (ENABLE_HASH3) {
    		this.sha = MessageDigest.getInstance("SHA-256");
    	}
	}

	
	protected void updateHash1 (byte[] buffer, int off, int len) {
    	if (ENABLE_HASH1) {
    		if (this.hash32 != null) {
    			this.hash32.update(buffer, off, len);    			
    		}
    		else if (this.crc32 != null) {
    			this.crc32.update(buffer, off, len);
    		}
    	}
	}
	
	protected void updateHash2 (byte[] buffer, int off, int len) {
    	if (ENABLE_HASH2) {
    		this.md5.update(buffer, off, len);
    	}
	}
	
	protected void updateHash3 (byte[] buffer, int off, int len) {
    	if (ENABLE_HASH3) {
    		this.sha.update(buffer, off, len);
    	}
	}
	

	protected String endHash1 () {
		String result = null;
    	if (ENABLE_HASH1) {
    		if (this.hash32 != null) {
    		    int hash = this.hash32.getValue();
    		    result = Long.toHexString((long)hash);
    		}
    		else if (this.crc32 != null) {
    			result = Long.toHexString(this.crc32.getValue());
    		}
    	}
    	return result;
	}
	
	protected String endHash2 () {
		String result = null;
    	if (ENABLE_HASH2) {
    		result = Hex.toHex(this.md5.digest());
    	}
    	return result;
	}
	
	protected String endHash3 () {
		String result = null;
    	if (ENABLE_HASH3) {
    		result = Hex.toHex(this.sha.digest());
    	}
    	return result;
	}

	public String[] tryToHashes (File file) {
		String[] result = null;
		try {
			result = toHashes(file);
		}
		catch (IOException e) {
			throw new ModelException(e);
		}
		return result;
	}
	
	
	public String[] toHashes (File file) throws IOException {
		String[] result = null;
//		long time = System.currentTimeMillis();
		result = new String[3];
		if (file != null) {
			FileInputStream is = null;
	        try {
	        	initHash1();
	        	initHash2();
	        	initHash3();

				is = new FileInputStream(file);
		        int read = -1;
		        byte[] buffer = new byte[8192];

		        while ((read = is.read(buffer)) > 0) {
		        	updateHash1(buffer, 0, read);
		        	updateHash2(buffer, 0, read);
		        	updateHash3(buffer, 0, read);
		        }

		        result[0] = endHash1();
		        result[1] = endHash2();
		        result[2] = endHash3();
	        } 
	        catch (Throwable t) {
	        	throw new ModelException(t);
	        }
	        finally {
	        	if (is != null) {
	        		is.close();
	        	}
	        }
		}
//		time = System.currentTimeMillis() - time;
//		System.out.println("HASHES time = " + time);
		return result;
	}
	
}
