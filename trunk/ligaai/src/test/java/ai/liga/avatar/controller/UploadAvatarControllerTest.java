package ai.liga.avatar.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ai.liga.avatar.service.ImagesTransformationService;
import ai.liga.user.model.User;
import ai.liga.user.service.UserService;
import ai.liga.util.Constants;

public class UploadAvatarControllerTest {

	private static final String IMAGEM_FORMATO_VALIDO = "rrr/png";

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
	public void verificaSeMessagemDeErroELancadaSeArquivoComFormatoEstranhoEEnviado() {
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
	public void verificaSeUmaImagemDeFormatoValidoEhSalva() {
		when(request.getAttribute(Constants.USER)).thenReturn(createUser());
		when(file.getContentType()).thenReturn(IMAGEM_FORMATO_VALIDO);

		fileUpload.novoAvatar(file, request);

		verify(imageService, times(1)).saveImage(any(MultipartFile.class), anyLong());

	}

	@Test
	public void naoDeveSalvarImagemSeUltrapassar10mb() {
		when(request.getAttribute(Constants.USER)).thenReturn(createUser());
		when(file.getContentType()).thenReturn(IMAGEM_FORMATO_VALIDO);
		when(file.getSize()).thenReturn((long) UploadAvatarController.MAX_FILE_SIZE + 1);

		ModelAndView mav = fileUpload.novoAvatar(file, request);
		assertEquals("Tamanho máxio permitido é de 10 megas", mav.getModel().get("msg"));

		verify(imageService, never()).saveImage(any(MultipartFile.class), anyLong());

	}

	@Test
	public void x() {
		assertTrue(fileUpload.regex.matcher("image/pjpeg").matches());
		assertTrue(fileUpload.regex.matcher("image/jpg").matches());
		assertTrue(fileUpload.regex.matcher("image/GIF").matches());
		assertTrue(fileUpload.regex.matcher("image/pnG").matches());
	}

}
