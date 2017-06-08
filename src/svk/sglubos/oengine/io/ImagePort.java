package svk.sglubos.oengine.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import svk.sglubos.oengine.utils.debug.MessageHandler;

public class ImagePort {
	public static BufferedImage getImageAsResource(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(ImagePort.class.getResource(path));
		} catch (IOException e) {
			MessageHandler.printMessage("IMAGE_PORT", MessageHandler.ERROR, "IOException occured when loading image as resource:" + path);
			e.printStackTrace();
		}
		return image;
	}
	
	public static BufferedImage loadImage(String path) {
		BufferedImage image = null;
		try{
			image = ImageIO.read(new File(path));			
		}catch (IOException e){
			MessageHandler.printMessage("IMAGE_PORT", MessageHandler.ERROR, "IOException occured when loading image:" + path);
			e.printStackTrace();
		}
		return image;
	}
	
	public static BufferedImage loadImage(File file) {
		BufferedImage image = null;
		try{
			image = ImageIO.read(file);			
		}catch (IOException e){
			MessageHandler.printMessage("IMAGE_PORT", MessageHandler.ERROR, "IOException occured when loading image:" + file);
			e.printStackTrace();
		}
		return image;
	}
	
	public static void exportImage(BufferedImage image, String path, String name, String format) {
		if(path.endsWith("/")) {
			path += name + "." + format;
		} else if(!path.isEmpty()) {
			path += "/" + name + "." + format;
		} else {
			path += name + "." + format;
		}
		
		try {
			ImageIO.write(image,format , new File(path));
			System.out.println(new File(path).getAbsolutePath());
		} catch (Exception e) {
			MessageHandler.printMessage("IMAGE_PORT", MessageHandler.ERROR, "Exception occured when writing image " + image +" to: " + path);
			e.printStackTrace();
		}
	}
}
