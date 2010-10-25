package ai.liga.image;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import junit.framework.Assert;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class CropImageTest {

	@DataPoint
	public static InputStream CARRO = CropImageTest.class
			.getResourceAsStream("car.jpg");
	@DataPoint
	public static InputStream CASA = CropImageTest.class
			.getResourceAsStream("house.jpg");

	@DataPoint
	public static InputStream CASA_RETRATO = CropImageTest.class
			.getResourceAsStream("house_portrait.jpg");

	@DataPoint
	public static InputStream DOG_JPG = CropImageTest.class
			.getResourceAsStream("dog_jpg.jpg");

	@DataPoint
	public static InputStream DOG = CropImageTest.class
			.getResourceAsStream("dog.gif");

	@DataPoint
	public static InputStream SUPER_MARIO = CropImageTest.class
			.getResourceAsStream("supermario.png");
	@DataPoint
	public static InputStream MARIO = CropImageTest.class
			.getResourceAsStream("mario.png");

	@Theory
	public void testaCropSquereImage(InputStream file) throws IOException {
		
		InputStream teste = CropImageTest.class
		.getResourceAsStream("mario.png");

		BufferedImage image = ImageIO.read(file);
		image = new ImageTransform().makeSquareCrop(image);

		Assert.assertTrue(image.getWidth() == image.getHeight());

//		String nameFile = "file" + new Date().getTime() + ".jpg";
//		File fileOut = new File(nameFile);
//		OutputStream tmp = new FileOutputStream(fileOut);
//
//		ImageIO.write(image, "jpg", fileOut);
//		tmp.close();

	}

}
