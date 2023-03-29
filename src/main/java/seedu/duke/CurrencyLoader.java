package seedu.duke;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class CurrencyLoader {

    private static CurrencyLoader instance;
    private HashMap<String, Double> currencies;

    private CurrencyLoader() {
        HashMap<String, Double> currencies = new HashMap<>();
        InputStream resourceStream = Duke.class.getClassLoader().getResourceAsStream("exchange_rates.txt");
        assert resourceStream != null : "exchange_rates.txt must be present";
        BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(": ");
                currencies.put(tokens[0], Double.parseDouble(tokens[1]));
            }
        } catch (IOException e) {
            System.err.println("error getting exchange_rates");
        }
        this.currencies = currencies;
    }

    public static CurrencyLoader getCurrencyLoader() {
        if (instance == null) {
            instance = new CurrencyLoader();
        }
        return instance;
    }

    public double getRate(String symbol) {
        return this.currencies.get(symbol);
    }

    public boolean currencyExists(String currency) {
        return this.currencies.containsKey(currency);
    }

    public HashMap<String, Double> getCurrencies() {
        return this.currencies;
    }
}
