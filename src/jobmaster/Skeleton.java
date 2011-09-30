package jobmaster;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class Skeleton {

	private static final String SKELETON_DIR_NAME = "skeleton";

	public static void copyFiles(String dir) {
		File skeletonDir = new File(SKELETON_DIR_NAME);
		if (!skeletonDir.isDirectory()) {
			return;
		}

		String[] list = skeletonDir.list();
		for (String bone : list) {
			File boneFile = new File(SKELETON_DIR_NAME + "/" + bone);
			copyFile(boneFile, new File(dir + "/" + bone));
		}

	}

	private static void copyFile(File source, File dest) {
		if (!source.exists()) {
			return;
		}

		try {
			dest.createNewFile();
			FileChannel sourceCh = null;
			FileChannel destinationCh = null;
			sourceCh = new FileInputStream(source).getChannel();
			destinationCh = new FileOutputStream(dest).getChannel();
			if (destinationCh != null && sourceCh != null) {
				destinationCh.transferFrom(sourceCh, 0, sourceCh.size());
			}
			if (sourceCh != null) {
				sourceCh.close();
			}
			if (destinationCh != null) {
				destinationCh.close();
			}
		} catch (IOException e) {
			// TODO
		}
	}

}
