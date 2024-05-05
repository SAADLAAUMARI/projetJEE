package ma.enset.ebankingbackend.web;



import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import ma.enset.ebankingbackend.dtos.*;
import ma.enset.ebankingbackend.exceptions.BalanceNotSufficientException;
import ma.enset.ebankingbackend.exceptions.BankAccountNotFoundException;
import ma.enset.ebankingbackend.services.BankAccountService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/bankAccounts")
@CrossOrigin("*")
/*@SecurityRequirement(name = "digitalBankApi")*/
public class BankAccountRestAPI {
    private BankAccountService bankAccountService;

    public BankAccountRestAPI(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }


    @GetMapping("/find/{accountId}")
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }


    @GetMapping("/findAll")
    public List<BankAccountDTO> getAllBankAccount(){
        return bankAccountService.bankAccountList();
    }


    @GetMapping("/{id}/operation")
    public List<AccountOperationDTO> getHistory(@PathVariable("id") String accountId){
        return bankAccountService.accountHistory(accountId);
    }

    @GetMapping("/{id}/operationPage")
    public AccountHistoryDTO getAccountHistory(@PathVariable("id") String accountId,
                                                     @RequestParam(name="page",defaultValue = "0") int page,
                                                     @RequestParam(name="size",defaultValue = "5") int size) throws BankAccountNotFoundException{

            return  bankAccountService.getAccountHistory(accountId,page,size);
    }


    @PostMapping("/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BalanceNotSufficientException, BankAccountNotFoundException {
        bankAccountService.debit(debitDTO.getAccountID(),debitDTO.getAmount(), debitDTO.getDescription());
        return  debitDTO;
    }


    @PostMapping("/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException{
        bankAccountService.credit(creditDTO.getAccountID(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }


    @PostMapping("/transfer")
    public TransferDTO transefer(@RequestBody TransferDTO transferDTO) throws BankAccountNotFoundException, BalanceNotSufficientException
    {
        bankAccountService.transfer(transferDTO.getAccountSourceID(),transferDTO.getAccountDestinationID(), transferDTO.getAmount());
        return transferDTO;
    }

}
