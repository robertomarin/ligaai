package ai.liga.urlrewrite;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.Test;

public class UrlRewriterTest {

	@Test
	public void testExpand() {
		Pattern regex = Pattern.compile("/([0-9a-zA-Z]+)$");
		assertFalse(regex.matcher("/css/ligaai.css").matches());
		assertTrue(regex.matcher("/8jd832jf").matches());
		assertTrue(regex.matcher("/sdfVsdve").matches());
		
	}
}
