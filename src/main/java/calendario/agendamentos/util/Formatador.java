package calendario.agendamentos.util;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Formatador {

public String formataData(Calendar dataCalendar){

    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String dataFormatada = sdf.format(dataCalendar.getTime());

		System.out.println("data " + dataFormatada);

		return dataFormatada;

	}
}