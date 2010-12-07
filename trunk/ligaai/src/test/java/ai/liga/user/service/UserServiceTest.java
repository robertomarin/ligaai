package ai.liga.user.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ai.liga.dao.HibernateDAOFactory;
import ai.liga.user.dao.UserDao;
import ai.liga.user.model.User;

public class UserServiceTest {

	private static final String RECUPERAÇÃO_DE_SENHA = "Recuperação de Senha";

	private static final String MENSAGEM_RECUPERAÇÃO_SENHA = "Mensagem Recuperação Senha";

	private static final String EMAIL_VALIDO = "email@valido";

	@Mock
	private HibernateDAOFactory factory;

	@Mock
	private UserDao userDao;

	@Mock
	private MailService mail;

	private UserService service;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		when(factory.getUserDao()).thenReturn(userDao);
		service = new UserService(factory, mail);

	}

	@Test(expected = UserNotFoundException.class)
	public void deveLancarExecaoSeUsuarioQueEstaTentandoRecuperarSenhaNaoForEncontrado() throws Exception {
		when(userDao.findByEmail(any(User.class))).thenReturn(null);

		service.recoverPassword(createUser(), RECUPERAÇÃO_DE_SENHA, MENSAGEM_RECUPERAÇÃO_SENHA);
		verify(userDao).findByEmail(EMAIL_VALIDO);

	}

	@Test
	public void deveEnviarLinkDeRecuperacaoSenhaParaEmailInformado() throws Exception {
		User user = createUser();
		user.setEmail(EMAIL_VALIDO);
		when(userDao.findByEmail(user)).thenReturn(user);

		service.recoverPassword(user, RECUPERAÇÃO_DE_SENHA, MENSAGEM_RECUPERAÇÃO_SENHA);

		verify(mail).send(user.getEmail(), RECUPERAÇÃO_DE_SENHA, MENSAGEM_RECUPERAÇÃO_SENHA);

	}

	@Test(expected = MailServiceException.class)
	public void deveLancarExecaoSeNaoConseguiEnviarEmailRecuperacaoSenha() throws Exception {
		User user = createUser();
		user.setEmail(EMAIL_VALIDO);
		when(userDao.findByEmail(user)).thenReturn(user);
		doThrow(new MailServiceException("erro")).when(mail).send(anyString(), anyString(), anyString());
		service.recoverPassword(user, RECUPERAÇÃO_DE_SENHA, MENSAGEM_RECUPERAÇÃO_SENHA);

		verify(mail).send(user.getEmail(), RECUPERAÇÃO_DE_SENHA, MENSAGEM_RECUPERAÇÃO_SENHA);

	}

	@Test
	public void deveEncriptarSenhaUsuario() throws Exception {
		User user = createUser();
		user.setPassword("123456");
		String expected = DigestUtils.md5Hex("123456");
		assertTrue(expected.equals(service.encriptPassword(user.getPassword())));

	}

	private User createUser() {
		return new User();
	}

}
