package ma.enset.ebankingbackend.web;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.ebankingbackend.dtos.BankAccountDTO;
import ma.enset.ebankingbackend.dtos.CustomerDTO;
import ma.enset.ebankingbackend.exceptions.CustomerNotFoundException;
import ma.enset.ebankingbackend.services.BankAccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j // les logs
@RequestMapping("/customers")
/*@SecurityRequirement(name = "digitalBankApi")*/
@CrossOrigin("*")
public class CustomerRestController {
    private BankAccountService bankAccountService;
    @GetMapping("")
    public List<CustomerDTO> customerDTOS(){
        return bankAccountService.listCustomer();
    }
    @GetMapping("/find/{id}")
    public CustomerDTO getCustomer(@PathVariable(name= "id") Long customerId) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerId);
    }
    @GetMapping("/search")
    public  List<CustomerDTO> searchCustomer(@RequestParam(name = "keyword",defaultValue = "")String keyword)
    {
        return  bankAccountService.searchCustomer(keyword);
    }

    @PostMapping("/add")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return bankAccountService.saveCustomer(customerDTO);
    }

    @PutMapping("/update/{id}")
    public CustomerDTO updateCustomer(@PathVariable("id") Long customerId,@RequestBody CustomerDTO customerDTO) throws CustomerNotFoundException{
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCustomer(@PathVariable("id") Long customerId){
        bankAccountService.deleteCustomer(customerId);
    }

    @GetMapping("/{id}/bankAccounts")
    public List<BankAccountDTO> getBanckAcounts(@PathVariable("id") Long id) throws CustomerNotFoundException{
        return  bankAccountService.getCustomerBankAccount(id);
    }
}
