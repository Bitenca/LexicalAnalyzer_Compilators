
import java.io.BufferedReader;
import java.io.FileReader;

public class GeradorTabelas {

	public static void main(String[] args) {
		
		try {
			FileReader arq = new FileReader("C:\\Users\\felip\\workspace\\AnalisadorLexico\\src\\fatorial.l2s");
			BufferedReader lerArq = new BufferedReader(arq);
			
			String linha = lerArq.readLine();
			
			while ( linha != null) {
				System.out.printf("%s", linha);
				
				linha = lerArq.readLine();
			}
			
			arq.close();
			
		} catch (Exception e) {
			
	        System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

		
		System.out.println();
	}
}
