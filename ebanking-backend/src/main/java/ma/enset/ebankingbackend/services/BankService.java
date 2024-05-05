package ma.enset.ebankingbackend.services;


import jakarta.transaction.Transactional;
import ma.enset.ebankingbackend.entities.BankAccount;
import ma.enset.ebankingbackend.entities.CurrentAccount;
import ma.enset.ebankingbackend.entities.SavingAccount;
import ma.enset.ebankingbackend.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BankService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    public void consulter(){
        BankAccount bankAccount = bankAccountRepository.findById("06f1d0ba-43f3-4300-8d4c-2b6524fc2e2a").orElse(null);
        System.out.println("*************************************");
        System.out.println(bankAccount.getId());
        System.out.println(bankAccount.getBalance());
        System.out.println(bankAccount.getStatus());
        System.out.println(bankAccount.getCreateAt());
        System.out.println(bankAccount.getCustomer().getName());
        System.out.println(bankAccount.getClass().getSimpleName());
        if(bankAccount instanceof CurrentAccount)
        {
            System.out.println(((CurrentAccount)bankAccount).getOverDrat());
        }
        else if(bankAccount instanceof SavingAccount){
            System.out.println(((SavingAccount)bankAccount).getInterestRate());
        }
        bankAccount.getAccountOperations().forEach(accountOperation -> {
            System.out.println("*************************************");
            System.out.println(accountOperation.getAmount());
            System.out.println(accountOperation.getType());
            System.out.println(accountOperation.getOperationDate());
        });
    };
}



