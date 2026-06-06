import java.util.*;

class Stock {
    private String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }
}

class Portfolio {
    private HashMap<String, Integer> holdings = new HashMap<>();

    public void buyStock(String symbol, int quantity) {
        holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
    }

    public void sellStock(String symbol, int quantity) {
        if (holdings.containsKey(symbol)) {
            int current = holdings.get(symbol);

            if (current >= quantity) {
                holdings.put(symbol, current - quantity);

                if (holdings.get(symbol) == 0) {
                    holdings.remove(symbol);
                }
            } else {
                System.out.println("Not enough shares!");
            }
        } else {
            System.out.println("Stock not found in portfolio!");
        }
    }

    public void displayPortfolio() {
        System.out.println("\n===== PORTFOLIO =====");

        if (holdings.isEmpty()) {
            System.out.println("No stocks owned.");
            return;
        }

        for (String stock : holdings.keySet()) {
            System.out.println(stock + " : " + holdings.get(stock) + " shares");
        }
    }
}

class User {
    private String name;
    private double balance;
    private Portfolio portfolio;

    public User(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.portfolio = new Portfolio();
    }

    public double getBalance() {
        return balance;
    }

    public void deductBalance(double amount) {
        balance -= amount;
    }

    public void addBalance(double amount) {
        balance += amount;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }
}

 class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        HashMap<String, Stock> market = new HashMap<>();

        market.put("TCS", new Stock("TCS", 3500));
        market.put("INFY", new Stock("INFY", 1500));
        market.put("RELIANCE", new Stock("RELIANCE", 2800));

        User user = new User("Sagar", 100000);

        while (true) {

            System.out.println("\n===== STOCK TRADING PLATFORM =====");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Balance");
            System.out.println("6. Exit");
            System.out.print("Enter Choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("\nAvailable Stocks:");
                    for (Stock s : market.values()) {
                        System.out.println(s.getSymbol() + " - ₹" + s.getPrice());
                    }
                    break;

                case 2:
                    System.out.print("Enter Stock Symbol: ");
                    String buySymbol = sc.next().toUpperCase();

                    System.out.print("Enter Quantity: ");
                    int buyQty = sc.nextInt();

                    if (market.containsKey(buySymbol)) {

                        double cost = market.get(buySymbol).getPrice() * buyQty;

                        if (user.getBalance() >= cost) {

                            user.deductBalance(cost);
                            user.getPortfolio().buyStock(buySymbol, buyQty);

                            System.out.println("Stock Purchased Successfully!");
                        } else {
                            System.out.println("Insufficient Balance!");
                        }
                    } else {
                        System.out.println("Invalid Stock Symbol!");
                    }
                    break;

                case 3:
                    System.out.print("Enter Stock Symbol: ");
                    String sellSymbol = sc.next().toUpperCase();

                    System.out.print("Enter Quantity: ");
                    int sellQty = sc.nextInt();

                    if (market.containsKey(sellSymbol)) {

                        double value = market.get(sellSymbol).getPrice() * sellQty;

                        user.getPortfolio().sellStock(sellSymbol, sellQty);
                        user.addBalance(value);

                        System.out.println("Stock Sold Successfully!");
                    } else {
                        System.out.println("Invalid Stock Symbol!");
                    }
                    break;

                case 4:
                    user.getPortfolio().displayPortfolio();
                    break;

                case 5:
                    System.out.println("Current Balance: ₹" + user.getBalance());
                    break;

                case 6:
                    System.out.println("Thank You!");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}
