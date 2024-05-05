package ma.enset.ebankingbackend;

import ma.enset.ebankingbackend.entities.AccountOperation;
import ma.enset.ebankingbackend.entities.CurrentAccount;
import ma.enset.ebankingbackend.entities.Customer;
import ma.enset.ebankingbackend.entities.SavingAccount;
import ma.enset.ebankingbackend.entities.BankAccount;
import ma.enset.ebankingbackend.enums.AccountStatus;
import ma.enset.ebankingbackend.enums.OperationType;
import ma.enset.ebankingbackend.repositories.AccountOperationRepository;
import ma.enset.ebankingbackend.repositories.BankAccountRepository;
import ma.enset.ebankingbackend.repositories.CustomerRepository;
import ma.enset.ebankingbackend.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }

//@Bean
    CommandLineRunner commandLineRunner(BankService bankService){
        return args -> {
         bankService.consulter();
        };
    }
    /** @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
    return args -> {
    Stream.of("Saad","LAAUMARI","Mohammed","Saad.LAAU").forEach(
    nom -> {
    CustomerDTO customer = new CustomerDTO();
    customer.setName(nom);
    customer.setEmail(nom+"@gmail.com");
    bankAccountService.saveCustomer(customer);
    }
    );

    bankAccountService.listCustomer().forEach(customerDTO -> {
    try{
    bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000,customerDTO.getId());
    bankAccountService.saveSavingBankAccountDTO(Math.random()*120000,5.5,customerDTO.getId());
    bankAccountService.bankAccountList().forEach(bankAccountDTO -> {
    for (int i=0;i<10;i++){
    try {
    String accountId;
    if(bankAccountDTO instanceof SavingBankAccountDTO) {

    accountId=((SavingBankAccountDTO) bankAccountDTO).getId();
    }
    else {

    accountId=((CurrentBanckAcountDTO) bankAccountDTO).getId();
    }
    bankAccountService.credit(accountId, 10000+Math.random()*12000,"CREDIT");
    bankAccountService.debit(accountId,10000+Math.random()*9000,"DEBIT");
    }
    catch (BankAccountNotFoundException e) {
    throw new RuntimeException(e);
    }
    catch (BalanceNotSufficientException e){
    throw new RuntimeException(e);
    }
    }
    });
    } catch (CustomerNotFoundException e){ throw  new RuntimeException(e); }
    });
    };
    }
     **/

    // @Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args -> {

            // test d'entité customer
             Stream.of("Saad","LAAUMARI","Mohammed","Saad.LAAU").forEach(name->{
               Customer customer = new Customer();
               customer.setName(name);
               customer.setEmail(name+"@gmail.com");
               customerRepository.save(customer);
             });

            // test d'entité bankAccount
            customerRepository.findAll().forEach(customer -> {
              // current account

                CurrentAccount account = new CurrentAccount();
                account.setId(UUID.randomUUID().toString());
                account.setBalance(Math.random()*90000);
                account.setCreateAt(new Date());
                account.setStatus(AccountStatus.CREATED);
                account.setCustomer(customer);
                account.setOverDrat(9000);
                bankAccountRepository.save(account);
                // saving account
                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreateAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(customer);
                savingAccount.setInterestRate(9000);
                bankAccountRepository.save( savingAccount);
            });
            // test d'entité List operation dans les comptes cree

            bankAccountRepository.findAll().forEach(bankAccount -> {
                for (int i=0;i<10;i++) {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random() * 12000);
                    accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
                    accountOperation.setBankAccount(bankAccount);
                    accountOperationRepository.save(accountOperation);

            };   });
/*

*/

        };
    }
}
