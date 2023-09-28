package concurrency_control;

public class ConcurrencyController {
    public static void main(String[] args) {
        DataBase database = new DataBase();
        final int QTD_TRANS = 10; //define a quantidade de transações 

        // Inicia QTD_TRANS transações
        for (int i = 0; i < QTD_TRANS; i++) {
            Transaction transaction = new Transaction(database);
            transaction.start();
        }

        // Aguarda um tempo para a execução
        try {
            Thread.sleep(5000); // Aguarda por 5 segundos para visualizar as saídas
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Interrompe a execução após o tempo especificado
        System.exit(0);
    }
}
