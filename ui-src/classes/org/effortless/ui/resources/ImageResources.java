package org.effortless.ui.resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.effortless.core.FilenameUtils;
import org.effortless.core.IOUtils;
import org.effortless.core.ModelException;
import org.effortless.core.ResourcesUtil;
import org.effortless.server.ServerContext;
//import org.effortless.transform.ui.ImageResources;
import org.effortless.ui.icons.Icons;

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.ResampleOp;

public class ImageResources {

	public static void copyApp(String key, String target, int width) {
		// TODO Auto-generated method stub
//		target = MySession.getRootContext() + MySession.getResourcesContext() + File.separator + target;
//		target = MySession.getRootContext() + File.separator + target;
		File targetFile = new File(target);
		if (targetFile != null && !targetFile.exists()) {
			try {
				String src = select(key, width);
				if (src != null) {
					String[] split = src.split("##");
					String zip = split[0];
					String entry = split[1];
					
					targetFile.getParentFile().mkdirs();
					FileOutputStream output = new FileOutputStream(target);
					copyEntry(zip, entry, output);
					rescale(targetFile, width);
				}
			} catch (URISyntaxException e) {
				throw new ModelException(e);
			} catch (IOException e) {
				throw new ModelException(e);
			}
//			IOUtils.copyFile(src, target);
		}
	}
	
	public static void copy(String appId, String key, String target, int width) {
		// TODO Auto-generated method stub
//		target = MySession.getRootContext() + MySession.getResourcesContext() + File.separator + target;
		target = ServerContext.getRootContext() + appId + File.separator + "resources" + File.separator + "img" + File.separator + target;
		copyApp(key, target, width);
	}
	
	public static void rescale (File input, int width) throws IOException {
		BufferedImage src = ImageIO.read(input);
		int height = width;
		int srcWidth = src.getWidth();
		int srcHeight = src.getHeight();
		ResampleOp resampleOp = new ResampleOp(width, height);
		resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Normal);
		BufferedImage rescaled = resampleOp.filter(src, null);
		ImageIO.write(rescaled, "png", input);
	}
	
	public static void copyEntry (String zip, String entry, OutputStream output) throws IOException {
		ZipFile zf = new ZipFile(zip);

        // first, copy contents from existing war
        Enumeration<? extends ZipEntry> entries = zf.entries();
        while (entries.hasMoreElements()) {
            ZipEntry e = entries.nextElement();
            String entryName = e.getName();
            if (entryName != null && entryName.equals(entry)) {
            	InputStream input = zf.getInputStream(e);
            	IOUtils.copy(input, output);
            	IOUtils.closeQuietly(output);
            	IOUtils.closeQuietly(input);
            	break;
            }
        }					
	}
	
	public static String select (String key, int width) throws URISyntaxException, IOException {
		String result = null;
		List<String> images = null;
		Map<String, List<String>> map = null;
//		if (width == 48) {
//			if (ImageResources.IMAGES_48 == null) {
//				ImageResources.IMAGES_48 = buildImages(48);
//			}
//			map = ImageResources.IMAGES_48;
//		}
//		else if (width == 64) {
//			if (ImageResources.IMAGES_64 == null) {
//				ImageResources.IMAGES_64 = buildImages(64);
//			}
//			map = ImageResources.IMAGES_64;
//		}
//		else if (width == 24) {
//			if (ImageResources.IMAGES_24 == null) {
//				ImageResources.IMAGES_24 = buildImages(24);
//			}
//			map = ImageResources.IMAGES_24;
//		}
//		else {
//			if (ImageResources.IMAGES_24 == null) {
//				ImageResources.IMAGES_24 = buildImages(24);
//			}
//			map = ImageResources.IMAGES_24;
//		}
		if (ImageResources.IMAGES == null) {
			ImageResources.IMAGES = buildImages(-1);
		}
		map = ImageResources.IMAGES;
		
		if (map != null) {
			images = map.get(key);
			if (images == null || images.size() <= 0) {
				Set<String> keys = map.keySet();
				if (keys != null && keys.size() > 0) {
					int idx = RANDOM.nextInt(keys.size());
					Iterator<String> it = keys.iterator();
					String selectKey = null;
					while (it.hasNext()) {
						selectKey = it.next();
						if (idx == 0) {
							break;
						}
						idx -= 1;
					}
					images = map.get(selectKey);
				}
			}
		}
		
		if (images != null && images.size() > 0) {
			int idx = RANDOM.nextInt(images.size());
			result = images.get(idx);
		}
		return result;
	}
	
	protected static Map<String, List<String>> buildImages(int width) throws URISyntaxException, IOException {
		Map<String, List<String>> result = null;
		result = new HashMap<String, List<String>>();
		String id = (width < 0 ? null : "-" + width);
		List<String> iconFiles = getIconFiles(id);
		if (iconFiles != null) {
			for (String iconFile : iconFiles) {
				Map<String, List<String>> icons = undecode(iconFile);
				Set<String> keys = icons.keySet();
				for (String key : keys) {
					List<String> toAdd = icons.get(key);
					List<String> orig = result.get(key);
					orig = (orig != null ? orig : new ArrayList<String>());
					result.put(key, orig);
					if (toAdd != null) {
						orig.addAll(toAdd);
					}
				}
			}
		}
//		String[] files = ResourcesUtil.getResourceListing(Icons.class, "" + width);
//		if (files != null) {
//			for (String file : files) {
//				String baseName = FilenameUtils.getBaseName(file);
//				String[] keys = baseName.split("-");
//				System.out.println("file = " + file);
//			}
//		}
		return result;
	}

	protected static final Random RANDOM = new Random();
	
	protected static Map<String, List<String>> IMAGES;
	protected static Map<String, List<String>> IMAGES_48;
	protected static Map<String, List<String>> IMAGES_64;
	protected static Map<String, List<String>> IMAGES_24;

	public static List<String> getIconFiles (String id) {
		List<String> result = null;
		
		id = (id != null ? id.trim() : "");
		result = new ArrayList<String>();
		String jarPath = ResourcesUtil.getJarFile(Icons.class);
		File jarFile = new File(jarPath);
		if (jarFile != null && jarFile.exists()) {
			File folder = jarFile.getParentFile();
			File[] files = folder.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file != null && file.isFile()) {
						String filePath = file.getAbsolutePath();
						String fileName = FilenameUtils.getName(filePath);
						if (fileName.endsWith("ef-icons" + id + ".zip")) {
							result.add(filePath);
						}
					}
				}
			}
		}
		return result;
	}
	
	public static Map<String, List<String>> undecode (String fileZip) throws IOException {
		Map<String, List<String>> result = null;
		result = new HashMap<String, List<String>>();
		
		// Locate the Jar file
//		String fileZip = "lib/aJarFile.zip";
		if (false) {
			FileSystemManager fsManager = VFS.getManager();
			FileObject zipFile = fsManager.resolveFile("zip:" + fileZip);

			// List the children of the Jar file
			FileObject[] children = zipFile.getChildren();
			for (FileObject file : children) {
				String fileName = file.getName().getPath();
				String baseName = FilenameUtils.getBaseName(fileName);
				String addr = fileZip + "##" + fileName;
				
				String[] keys = baseName.split("-");
				for (String key : keys) {
					List<String> files = result.get(key);
					files = (files != null ? files : new ArrayList<String>());
					result.put(key, files);
					if (!files.contains(addr)) {
						files.add(addr);
					}
				}
			}
		}
		else {
			ZipFile zf = new ZipFile(fileZip);
			try {
			  for (Enumeration<? extends ZipEntry> e = zf.entries();
			        e.hasMoreElements();) {
			    ZipEntry ze = e.nextElement();
			    String fileName = ze.getName();
			    
			    
				String baseName = FilenameUtils.getBaseName(fileName);
				String addr = fileZip + "##" + fileName;
				
				String[] keys = baseName.split("-");
				for (String key : keys) {
					List<String> files = result.get(key);
					files = (files != null ? files : new ArrayList<String>());
					result.put(key, files);
					if (!files.contains(addr)) {
						files.add(addr);
					}
				}
			  }
			} finally {
			  zf.close();
			}			
		}
		
		return result;
	}
	
}
