package view;
import controller.Sistema;
import java.util.concurrent.Semaphore;

public class Banco {

	public static void main(String[] args) {
		int permissao = 1;
		int permissao2 = 1;
		
		Semaphore semaforoSaque = new Semaphore (permissao);
		Semaphore semaforoDeposito = new Semaphore (permissao2);

		
		for(int idConta = 0; idConta < 20; idConta++) {
			double saldo = (double)((Math.random()* 2001)+1000);
			Thread tOperacao = new Sistema (idConta, semaforoSaque, semaforoDeposito, saldo);
			tOperacao.start();
		}
	}

}
