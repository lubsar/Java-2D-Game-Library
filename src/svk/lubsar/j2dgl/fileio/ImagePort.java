package svk.lubsar.j2dgl.fileio;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import svk.lubsar.j2dgl.utils.debug.MessageHandler;

/**
 * Utility class with static methods that load image files as {@link java.awt.image.BufferedImage BufferedImage} and also export images to files.
 * It also provides functionality to get images as resources (see documentation).
 * 
 * @see #loadImage(File)
 * @see #getImageAsResource(String)
 * @see #exportImage(BufferedImage, String, String, String)
 */
public class ImagePort {
	/**
	 * Loads image on given path as class resource (see documentation).
	 * @param path name of resource
	 * @return resource as buffered image
	 * @throws IOException if error occurs while loading the resource
	 */
	public static BufferedImage getImageAsResource(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(ImagePort.class.getResource(path));
		} catch (IOException e) {
			MessageHandler.print("IMAGE_PORT", MessageHandler.ERROR, "IOException occured when loading image as resource:" + path);
			e.printStackTrace();
		}
		
		return image;
	}
	
	/**
	 * Loads image file on given path.
	 * It creates {@link {@link java.io.File File} object with given path as argument (see {@link java.io.File#File(String)}) for more details.
	 * @param path name of resource
	 * @return File on given path as {@link java.awt.image.BufferedImage BufferedImage} or <code><b>null</b></code> if loading fails
	 */
	public static BufferedImage loadImage(String path) {
		BufferedImage image = null;
		try{
			image = ImageIO.read(new File(path));			
		}catch (IOException e){
			MessageHandler.print("IMAGE_PORT", MessageHandler.ERROR, "IOException occured when loading image:" + path);
			e.printStackTrace();
		}
		return image;
	}
	
	/**
	 * Loads image from given {@link java.io.File File}.
	 * @param file image file to be loaded
	 * @return {@link java.awt.image.BufferedImage BufferedImage} or <code><b>null</b></code> if loading fails
	 */
	public static BufferedImage loadImage(File file) {
		BufferedImage image = null;
		try{
			image = ImageIO.read(file);			
		}catch (IOException e){
			MessageHandler.print("IMAGE_PORT", MessageHandler.ERROR, "IOException occured when loading image:" + file);
			e.printStackTrace();
		}
		return image;
	}
	
	/**
	 * Creates image file of given formant with specified parameters.
	 * @param image BufferedImage to be saved
	 * @param path wherever image file will be saved (can be path to directory with or without / in the end), if path is empty, the file of a given name and format will be saved next to the application jar file
	 * @param name to be used in path
	 * @param format of the exported image
	 * @return <code><b>true</b></code> if image file is created successfully <code><b>false</b></code> otherwise
	 */
	public static boolean exportImage(BufferedImage image, String path, String name, String format) {
		if(path.endsWith("/")) {
			path = String.format("%s%s.%s", path, name, format);
		} else if(!path.isEmpty()) {
			path = String.format("%s/%s.%s", path, name, format);
		} else {
			path = String.format("%s.%s", path, name, format);
		}
		
		try {
			ImageIO.write(image, format, new File(path));
			return true;
		} catch (Exception e) {
			MessageHandler.print("IMAGE_PORT", MessageHandler.ERROR, "Exception occured when writing image " + image +" to: " + path);
			e.printStackTrace();
		}
		
		return false;
	}
}
