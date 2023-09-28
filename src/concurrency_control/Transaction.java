package concurrency_control;

import java.util.concurrent.atomic.AtomicInteger;

//Classe que representa uma transação
class Transaction extends Thread {
 private static AtomicInteger transactionCounter = new AtomicInteger(1);
 private int id;
 private int ts;
 private DataBase database;
 private boolean completed = false;

 // Construtor da classe
 public Transaction(DataBase database) {
     this.id = transactionCounter.getAndIncrement();
     this.database = database;
 }

 // Método para obter o carimbo de data e hora da transação
 public int getTS() {
     return ts;
 }

 // Método para obter o ID da transação
 public long getId() {
     return id;
 }

 // Método para rollback da transação em caso de conflito
 public void rollback() {
     System.out.println("Transação " + id + " foi abortada.");
     completed = true; // Conclui a thread após o rollback
 }

 // Método para concluir a transação com sucesso
 public void complete() {
     completed = true;
 }

 @Override
 public void run() {
     while (!completed) { // Executa transações até que a transação seja concluída
         ts = id;

         int readResult = database.read(this);
         if (readResult != -1) {
             int newValue = readResult + 1;
             database.write(this, newValue);
         }
     }
 }
}