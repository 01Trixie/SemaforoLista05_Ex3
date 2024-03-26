package controller;

import java.text.DecimalFormat;
import java.util.concurrent.Semaphore;

public class Sistema extends Thread{
	
	private int idConta;
	private Semaphore semaforoSaque;
	private Semaphore semaforoDeposito;
	private double saldo;

	
	public  Sistema(int idConta, Semaphore semaforoSaque ,  Semaphore semaforoDeposito, double saldo) {
		this.idConta = idConta;
		this.semaforoSaque = semaforoSaque;
		this.semaforoDeposito = semaforoDeposito;
		this.saldo = saldo;

	}
	
	public void run() {
		transacoes();
	}
	
	private void transacoes(){
		int limite = 1;  //Limite de transações.
		int cta = 1;
		int operacoes = 0;
		String msg = "";
		
		while(operacoes < limite) {
			int transicao = (int) ((Math.random() * 2)+1); // 1 - Saque, 2 - Depósito.
			operacoes += cta;
			msg += "O sistema realizou sua " + operacoes   + "° transação e foi do tipo " + transicao + " pela conta #" + idConta ;
			if(transicao == 1) {
				try {
					sleep (1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					semaforoSaque.acquire();
					System.out.println(msg);
					saque();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					semaforoSaque.release();
				}

			} else{		
				try {
					semaforoDeposito.acquire();
					System.out.println(msg);
					deposito();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					semaforoDeposito.release();
				}
			}
		}
	}
	
	private void saque() {
		int retirarDinheiro = (int) ((Math.random()* 500)+500);
		double sacar = (saldo - retirarDinheiro);
		String valorTotal = new DecimalFormat("#,##0.00").format(sacar);
		System.out.println( "A pessoa com o numero da conta #" + idConta + " escolheu a operação SAQUE e retirou da conta R$" + valorTotal);

	}
	
	private void deposito() {
		double deposito = (double) ((Math.random()* 500)+500);
		double depositar = (deposito + saldo);
		String valorTotal = new DecimalFormat("#,##0.00").format(depositar);
		System.out.println("A pessoa com o numero da conta #" + idConta + " recebeu um DEPÓSITO de R$" + valorTotal);
		
	}
	
	
}
