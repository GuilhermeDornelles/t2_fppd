import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

public class Bank extends UnicastRemoteObject implements IBank {
    private int slotLimit = 1;
    private Semaphore semaphore;
    private int accountCounter;
    private ArrayList<Account> accounts;
    private ArrayList<Long> uniqueRequestKeys;

    public Bank() throws RemoteException {
        super();
        this.semaphore = new Semaphore(slotLimit);
        this.accountCounter = 0;
        this.accounts = new ArrayList<Account>();
        this.uniqueRequestKeys = new ArrayList<Long>();
    }

    @Override
    public Account createAccount(String name, Long uniqueKey) {
        try {
            semaphore.acquire();
            if (!checkUniqueRequestKey(uniqueKey)) {
                Utils.printRequest("createAccount", uniqueKey, LocalDateTime.now());
                semaphore.release();
                return null;
            }

            if (!isValidName(name)) {
                Utils.printRequest("createAccount", uniqueKey, LocalDateTime.now());
                semaphore.release();
                return null;
            }

            Account newAccount = null;
            name = name.toUpperCase();
            try {
                newAccount = new Account(++accountCounter, 0, name);
                this.accounts.add(newAccount);
            } catch (Exception e) {
                accountCounter--;
                semaphore.release();
                throw e;
            }
            Utils.printRequest("createAccount", uniqueKey, LocalDateTime.now());
            semaphore.release();
            return newAccount;
        } catch (Exception e) {
            System.out.println("ERROR IN CREATE ACCOUNT: " + e);
            return null;
        }
    }

    @Override
    public boolean closeAccount(String name) {
        Account account = this.getAccount(name);
        boolean op = this.accounts.remove(account);

        if (op) {
            this.accountCounter--;
        }
        Utils.printRequest("closeAccount", null, LocalDateTime.now());
        return op;
    }

    @Override
    public Account getAccount(String name) {
        if (accountCounter < 1)
            return null;
        List<Account> accountsList = this.accounts.stream()
                .filter(a -> a.getname().toLowerCase().equals(name.toLowerCase()))
                .collect(Collectors.toList());

        if (!accountsList.isEmpty()) {
            return accounts.get(0);
        }
        Utils.printRequest("getAccount", null, LocalDateTime.now());
        return null;
    }

    @Override
    public boolean isValidName(String name) {
        if (getAccount(name) == null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean withdraw(String name, float value, Long uniqueKey) {
        Account account = this.getAccount(name);
        if (!checkUniqueRequestKey(uniqueKey)) {
            Utils.printRequest("withDraw", uniqueKey, LocalDateTime.now());
            return false;
        }

        float balance = account.getBalance();
        if (balance > value) {
            account.withdraw(value);
            Utils.printRequest("withDraw", uniqueKey, LocalDateTime.now());
            return true;
        }
        Utils.printRequest("withDraw", uniqueKey, LocalDateTime.now());
        return false;
    }

    @Override
    public boolean deposit(String name, float value, Long uniqueKey) {
        if (!checkUniqueRequestKey(uniqueKey)) {
            Utils.printRequest("withDraw", uniqueKey, LocalDateTime.now());
            return false;
        }
        Utils.printRequest("withDraw", uniqueKey, LocalDateTime.now());
        return this.getAccount(name).deposit(value);
    }

    private boolean checkUniqueRequestKey(Long newKey) {
        List<Long> uniqueKey = uniqueRequestKeys.stream()
                .filter(key -> key.compareTo(newKey) == 0)
                .collect(Collectors.toList());

        if (uniqueKey.size() > 0) {
            return false;
        }

        return uniqueRequestKeys.add(newKey);
    }

    @Override
    public ArrayList<Account> getAccounts() {
        Utils.printRequest("getAccounts", null, LocalDateTime.now());
        return this.accounts;
    }
}