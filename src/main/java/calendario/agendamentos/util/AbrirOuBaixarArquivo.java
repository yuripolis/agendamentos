package calendario.agendamentos.util;

import java.io.File;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;


@Component
public class AbrirOuBaixarArquivo {

	private static final String FORCE_DOWNLOAD_HEADER = "Content-Disposition";

	private static final String FORCE_DOWNLOAD_VALUE = "attachment; filename=";

	@Autowired
	private HttpServletResponse response;

	@Deprecated
	public AbrirOuBaixarArquivo() {
	}


	public FileSystemResource open(File file) {
		return new FileSystemResource(file);
	}

	public FileSystemResource open(String file) {
		return new FileSystemResource(file);
	}

	public FileSystemResource download(File file) {
		response.setHeader(FORCE_DOWNLOAD_HEADER,
				FORCE_DOWNLOAD_VALUE + file.getName().replace(", ", "_").replace(",", "_"));
		return new FileSystemResource(file);
	}

	public boolean arquivoExiste(String arquivo) {
		return new File(arquivo).exists();
	}

}
