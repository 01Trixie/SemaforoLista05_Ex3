package controller;
import java.text.DecimalFormat;
import java.util.concurrent.Semaphore;
public class Sistema extends Thread{
	
	private int idConta;
	private Semaphore semaforo;
	private double saldo;
	
	public  Sistema(int idConta, Semaphore semaforo , double saldo) {
		this.idConta = idConta;
		this.semaforo = semaforo;

		this.saldo = saldo;
	}
	
	public void run() {
	
		transacoes();
		try {
			semaforo.acquire();
			saque();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaforo.release();
		}
		try {
			semaforo.acquire();
			deposito();
			} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaforo.release();
		}

	}
	
	private void transacoes(){
		int limite = 20;  //Limite de transações.
		int cta = 1;
		int operacoes = 0;
		while(operacoes < limite) {
			int transicao = (int) ((Math.random() + 0.1)+1);; // 1 - Saque, 2 - Depósito.
			operacoes += cta;
			System.out.println("O sistema realizou sua " + operacoes + "° operação e foi do tipo " + transicao + " pela conta #" + idConta );
			if(transicao == 1) {
				saque();
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else{		
				deposito();
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("O sistema realizou sua " + operacoes + "° operação e foi do tipo " + transicao + " pela conta #" + idConta );
		}
	}
	
	private void saque() {
		int retirarDinheiro = (int) ((Math.random()* 500)+500);
		double sacar = (saldo - retirarDinheiro);
		String valorTotal = new DecimalFormat("#,##0.00").format(sacar);
		System.out.println("A pessoa com o numero da conta #" + idConta + " escolheu a operação SAQUE e retirou da conta R$" + valorTotal);

	}
	
	private void deposito() {
		double deposito = (double) ((Math.random()* 500)+500);
		double depositar = (deposito + saldo);
		String valorTotal = new DecimalFormat("#,##0.00").format(depositar);
		System.out.println("A pessoa com o numero da conta #" + idConta + " recebeu um DEPÓSITO de R$" + valorTotal);
		
	}
	
	
}
