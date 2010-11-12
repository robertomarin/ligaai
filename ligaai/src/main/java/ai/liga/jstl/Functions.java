package ai.liga.jstl;

import static java.lang.System.out;
import static java.util.regex.Pattern.CASE_INSENSITIVE;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ai.liga.ligaai.model.Contact;
import ai.liga.ligaai.model.ContactType;

public class Functions {

	private static Pattern DETECT_URL = Pattern.compile(".*\\b((https?|ftp|file)://[-A-Z0-9+&@#/%?=~_|!:,.;]*[-A-Z0-9+&@#/%=~_|]).*",
			CASE_INSENSITIVE);;

	private static final String A = "<a target=\"_blank\" rel=\"nofollow\" href=\"$1\">$1</a>";

	public static String getUrlContact(Contact contact) {
		StringBuilder url = new StringBuilder();
		if (contact != null) {
			ContactType type = contact.getType();
			if (type != null) {
				url.append(type.buildUrl(contact.getContent()));
			}
		}

		return url.toString();
	}

	public static boolean isLinkable(ContactType type) {
		if (type != null) {
			return type.isLinkable();
		}
		return false;
	}

	public static String buildLink(String s) {
		return s.replaceAll("(\\A|\\s)((http|https|ftp|mailto)://\\S+)(\\s|\\z)",
				"$1<a target=\"_blank\" rel=\"nofollow\" href=\"$2\">$2</a>$4");
	}

}
