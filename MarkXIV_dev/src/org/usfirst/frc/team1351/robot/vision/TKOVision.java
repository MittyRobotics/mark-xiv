package org.usfirst.frc.team1351.robot.vision;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 * 
 * Main Vision Class.
 * To be used by a controller.
 * 
 * @author LookLotsOfPeople
 *
 */
public class TKOVision {
	static UsbCamera[] camera;
	private static VisionThread visionThread[];
	public static double centerX[];
	private final static Object imgLock = new Object();
	
	/**
	 * 
	 * Used to initialize cameras.
	 * The camera does not have to be used for vision if initialized here.
	 * 
	 * @param id of the camera
	 */
	public static void initCamera(char id) {
		camera[id] = CameraServer.getInstance().startAutomaticCapture("gear", 0);
        camera[id].setResolution(160, 120);
        camera[id].setFPS(30);
	}
	
	/**
	 * 
	 * Camera must already be initialized.
	 * 
	 * @param id of the camera
	 */
	public static void runVision(char id) {
		prepareForVision(id);
		activateVisionThread(id);
	}
	
	/**
	 * 
	 * Creates the thread for use of the camera of the same id.
	 * 
	 * @param id of camera
	 */
	private static void prepareForVision(char id) {
		visionThread[id] = new VisionThread(camera[id], new TKOVisionProcessing(), pipeline -> {
			if (!pipeline.filterContoursOutput().isEmpty()) {
				Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
				synchronized (imgLock) {
					centerX[id] = r.x + (r.width / 2);
					System.out.println(centerX);
					}
				}
			});
	}
	
	/**
	 * 
	 * Starts up the created vision thread.
	 * 
	 * @param id of the camera
	 */
	private static void activateVisionThread(char id) {
		visionThread[id].start();
	}
	
}