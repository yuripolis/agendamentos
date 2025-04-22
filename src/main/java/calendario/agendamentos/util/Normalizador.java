package calendario.agendamentos.util;

import java.text.Normalizer;

public class Normalizador {

	public static String deTituloEDecricaoDoArtigo(String valor) {
		return Normalizer.normalize(valor, Normalizer.Form.NFD)
				.replaceAll("[^\\p{ASCII}]", "").toLowerCase().trim();
	}
	
	public static String deNomeArquivosOuDiretorios(String str) {
		return removeUnderlineDuplo(removeTracoDuplo(Normalizer.normalize(str, Normalizer.Form.NFD)
				.replaceAll("[^\\p{ASCII}]", "").replace(" ", "-").replaceAll("[^a-zA-Z0-9\\-_.]", "").toLowerCase()));
	}

	public static String deURL(String str) {
		return removeBarraDuplaDaURL(removeUnderlineDuplo(
				removeTracoDuplo(Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "")
						.replace(" ", "-").replaceAll("[^a-zA-Z0-9\\-_/.]", "").toLowerCase())));
	}

	private static String removeTracoDuplo(String valor) {
		if (valor != null && valor.contains("--")) {
			return removeTracoDuplo(valor.replace("--", "-"));
		} else {
			return valor;
		}
	}

	private static String removeUnderlineDuplo(String valor) {
		if (valor != null && valor.contains("__")) {
			return removeTracoDuplo(valor.replace("__", "_"));
		} else {
			return valor;
		}
	}

	private static String removeBarraDuplaDaURL(String valor) {
		if (valor != null && valor.contains("//")) {
			return valor.replace("//", "/");
		} else {
			return valor;
		}
	}
}
