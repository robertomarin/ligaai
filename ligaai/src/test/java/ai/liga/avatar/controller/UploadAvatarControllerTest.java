package ai.liga.avatar.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ai.liga.avatar.service.ImagesTransformationService;
import ai.liga.user.model.User;
import ai.liga.user.service.UserService;
import ai.liga.util.Constants;

public class UploadAvatarControllerTest {

	@Mock
	private HttpServletRequest request;

	@Mock
	private ImagesTransformationService imageService;

	@Mock
	private UserService userService;
	
	@Mock
	private MultipartFile file;

	private UploadAvatarController fileUpload;
	

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		fileUpload = new UploadAvatarController(imageService, userService);
	}

	@Test
	public void verificaSeMessagemDeErroELancadaSeArquivoComFormatoEstranhoEEnviado() throws IOException {
		when(request.getAttribute(Constants.USER)).thenReturn(createUser());
		when(file.getContentType()).thenReturn("rrr/txt");

		String expected = "Opa não entendemos o formato do arquivo enviado, lembrando que os formatos suportados são: gif, jpg e png.";
		assertEquals(expected, fileUpload.novoAvatar(file, request).getModel().get("msg"));
		
		verify(imageService, never()).saveImage(any(MultipartFile.class), anyLong());

	}

	private User createUser() {
		return new User();
	}

	@Test
	public void testPNGFile() throws IOException {

		InputStream is = this.getClass().getResourceAsStream("mario.png");

		MockMultipartFile mockMultipartFile = new MockMultipartFile("mario", "mario.png", "rrr/png", is);

		ModelAndView upload = fileUpload.novoAvatar(mockMultipartFile, request);
		Map<String, Object> model = upload.getModel();
		System.out.println(model.get("msg"));

	}

	@Test
	public void x() {
		assertTrue(fileUpload.regex.matcher("image/pjpeg").matches());
		assertTrue(fileUpload.regex.matcher("image/jpg").matches());
		assertTrue(fileUpload.regex.matcher("image/GIF").matches());
		assertTrue(fileUpload.regex.matcher("image/pnG").matches());
	}

}
