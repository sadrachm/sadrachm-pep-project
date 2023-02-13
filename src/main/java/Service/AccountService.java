package Service;

import java.util.List;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;
    public AccountService() {
        this.accountDAO = new AccountDAO();
    }
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();        
    }
    public Account addAccount(Account account) {
        return accountDAO.insertAccount(account);
    }
    public Account getAccountByUserAndPass(Account account) {
        return accountDAO.getAccountByUserAndId(account);
    }
    public Account getAccountByID(int id) {
        return accountDAO.getAccountByID(id);
    }

}
