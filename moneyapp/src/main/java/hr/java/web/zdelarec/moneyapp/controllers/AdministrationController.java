package hr.java.web.zdelarec.moneyapp.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hr.java.web.zdelarec.moneyapp.database.ExpenseRepository;
import hr.java.web.zdelarec.moneyapp.database.WalletRepository;
import hr.java.web.zdelarec.moneyapp.entities.Wallet;

@Controller
public class AdministrationController {

    @Autowired
    ExpenseRepository eRep;

    @Autowired
    WalletRepository wRep;

    @GetMapping(value="/administration")
    public String showIndex(Model model) {

        Iterable<Wallet> iterator2 = wRep.findAll();
        ArrayList<Wallet> list2 = new ArrayList<>();
        for(Wallet wl : iterator2) {
            list2.add(wl);
        }
        model.addAttribute("wallets", list2);
        //model.addAttribute("expenses", list);

        return "administration";

    }

    @GetMapping(value="edit")
    public String editOrDelete(Model model, @RequestParam String id) {
        Optional<Wallet> wallet = wRep.findById(Long.parseLong(id));
        model.addAttribute("wallet", wallet);
        return "editWallet";

    }

    @PostMapping(value="saveEdited")
    public String savEdited(Wallet wallet, Model model){
        wallet.setCreateDate(new Date());
        // wRep.update(wallet);
        return("redirect:/administration");
    }

    @GetMapping(value="delete")
    public String deleteWallet(Model model, @RequestParam String id) {
        wRep.deleteById(Long.parseLong(id));
        return "redirect:/administration";
    }

}