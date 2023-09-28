package concurrency_control;

import java.util.concurrent.atomic.AtomicInteger;

// Classe que representa o banco de dados
class DataBase {
    private int data;
    private AtomicInteger readTS;
    private AtomicInteger writeTS;

    // Construtor da classe
    public DataBase() {
        data = 0;
        readTS = new AtomicInteger(0);
        writeTS = new AtomicInteger(0);
    }

    // Método para leitura de dados
    public synchronized int read(Transaction transaction) {
        int currentReadTS = readTS.get();
        int currentWriteTS = writeTS.get();

        // Verifica se a leitura é válida com base nos carimbos de data e hora
        if (currentWriteTS > transaction.getTS()) {
            transaction.rollback();
            return -1;
        }

        int value = data;

        try {
            Thread.sleep(1000); // Simula uma operação de leitura mais lenta
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Transação " + transaction.getId() + " leu o valor " + value);
        readTS.set(transaction.getTS());
        transaction.complete(); // Conclui a thread após a operação bem-sucedida

        return value;
    }

    // Método para escrita de dados
    public synchronized void write(Transaction transaction, int value) {
        int currentReadTS = readTS.get();
        int currentWriteTS = writeTS.get();

        // Verifica se a escrita é válida com base nos carimbos de data e hora
        if (currentReadTS > transaction.getTS() || currentWriteTS > transaction.getTS()) {
            transaction.rollback();
            return;
        }

        try {
            Thread.sleep(1000); // Simula uma operação de escrita mais lenta
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        data = value;
        System.out.println("Transação " + transaction.getId() + " escreveu o valor " + value);
        writeTS.set(transaction.getTS());
        transaction.complete(); // Conclui a thread após a operação bem-sucedida
    }
}
