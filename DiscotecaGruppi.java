import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DiscotecaGruppi {

    private int personeDentro = 0;

    // Metodo sincronizzato per far entrare un gruppo di persone
    public synchronized void entraGruppo(int nPersone) {
        personeDentro += nPersone;
        System.out.println(Thread.currentThread().getName() + " è entrato con " + nPersone + " persone. Persone dentro: " + personeDentro);
    }

    // Metodo sincronizzato per far uscire un gruppo di persone
    public synchronized void esceGruppo(int nPersone) {
        personeDentro -= nPersone;
        System.out.println(Thread.currentThread().getName() + " è uscito con " + nPersone + " persone. Persone dentro: " + personeDentro);
    }

    // Metodo per ottenere il numero corrente di persone dentro
    public synchronized int getPersoneDentro() {
        return personeDentro;
    }

    public static void main(String[] args) throws InterruptedException {
        DiscotecaGruppi discoteca = new DiscotecaGruppi();
        Random random = new Random();

        // Creiamo un pool di thread per simulare i gruppi (es: 5 gruppi)
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // Creazione e avvio dei thread (gruppi)
        for (int i = 0; i < 5; i++) {
            int nPersone = random.nextInt(5) + 5; // Ogni gruppo avrà tra 5 e 10 persone
            executor.submit(() -> {
                try {
                    while (true) {
                        // Il gruppo entra in discoteca
                        discoteca.entraGruppo(nPersone);
                        // Rimane all'interno per un tempo casuale (es: tra 2 e 4 secondi)
                        Thread.sleep(random.nextInt(2000) + 2000);
                        // Il gruppo esce dalla discoteca
                        discoteca.esceGruppo(nPersone);
                        // Rimane fuori per un tempo casuale prima di rientrare (es: tra 1 e 3 secondi)
                        Thread.sleep(random.nextInt(2000) + 1000);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Stampa periodica del numero di persone dentro la discoteca ogni 2 secondi
        while (true) {
            System.out.println("Numero di persone attualmente nella discoteca: " + discoteca.getPersoneDentro());
            Thread.sleep(2000); // Stampa ogni 2 secondi
        }
    }
}
