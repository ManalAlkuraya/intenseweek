package com.example.spring101.controller;


import com.example.spring101.model.User;
import com.example.spring101.model.UserActions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(path="users")
public class UserController {

    ArrayList<User> username = new ArrayList<>();
    int bankbalance = 1000;

    @GetMapping(path= "/user")
            public ArrayList<User>  myfun() {
        return username;
    }


//اضافه مبلغ
    @PostMapping("/add/{id}")
    public ResponseEntity add(@PathVariable String id,@RequestBody String balance){
        for (int i = 0; i < username.size(); i++) {
            if(id.equals(username.get(i).getId())){
                username.get(i).setBalance(Double.parseDouble(balance) + username.get(i).getBalance());
                return ResponseEntity.status(200).body("add balance");
            }
        }
        return ResponseEntity.status(400).body("user not found");
    }

//سحب مبلغ
    @PostMapping("/Withdrawal/{id}")
    public ResponseEntity Withdrawal(@PathVariable String id,@RequestBody String balance){
        for (int i = 0; i < username.size(); i++) {
            if(username.get(i).getBalance() - Double.parseDouble(balance) < 0){
                return ResponseEntity.status(400).body("not enfu");
            }
            if(id.equals(username.get(i).getId() )){
                username.get(i).setBalance( username.get(i).getBalance() - Double.parseDouble(balance) );
                return ResponseEntity.status(200).body("Withdrawal balance");
            }
        }
        return ResponseEntity.status(400).body("user not found");
    }

////balance bank
//    @PostMapping("/balance/{id}")
//    public ResponseEntity balancebank(@PathVariable String id,@RequestBody String balance){
//        for (int i = 0; i < username.size(); i++) {
//            if(username.get(i).getBalance() - Double.parseDouble(balance) < 0){
//                return ResponseEntity.status(400).body("not enfu");
//            }
//            if(id.equals(username.get(i).getId() )){
//                username.get(i).setBalance( username.get(i).getBalance() - Double.parseDouble(balance) );
//                return ResponseEntity.status(200).body("Withdrawal balance");
//            }
//        }
//        return ResponseEntity.status(400).body("user not found");
//    }

    @PostMapping("/takeloan")
    public ResponseEntity<String> takeLoan(@RequestBody UserActions actions) {
        if (actions.getId() == null || actions.getPassword() == null || actions.getAmount() == 0) {
            return ResponseEntity.status(400).body("send all");
        }
        for (int i = 0; i < username.size(); i++) {
            User u =username.get(i);
            if (u.getId().equals(actions.getId())) {
                if (!(u.getPassword().equals(actions.getPassword()))) {
                    return ResponseEntity.status(400).body("password dose not mach the user id");
                }
                if(bankbalance - actions.getAmount() < 0){
                    return ResponseEntity.status(400).body("the bank dose not have enough money ");
                }
                bankbalance = bankbalance - actions.getAmount();
                u.setLaonAmount(actions.getAmount()+ u.getLaonAmount());
                u.setBalance(actions.getAmount()+u.getBalance());
                return ResponseEntity.status(200).body("take loan confirmed");

            }
        }
        return ResponseEntity.status(400).body("invailid id ");
    }

    @PostMapping("/repayloan")
    public ResponseEntity<String> repayloan(@RequestBody UserActions actions) {
        if (actions.getId() == null || actions.getPassword() == null || actions.getAmount() == 0) {
            return ResponseEntity.status(400).body("send all");
        }
        for (int i = 0; i < username.size(); i++) {
            User u =username.get(i);
            if (u.getId().equals(actions.getId())) {
                if (!(u.getPassword().equals(actions.getPassword()))) {
                    return ResponseEntity.status(400).body("password dose not mach the user id");
                }
                if(u.getLaonAmount() - actions.getAmount() < 0){
                    return ResponseEntity.status(400).body("you have  "+u.getLaonAmount());
                }
                if (u.getBalance()-actions.getAmount()<0){
                    return ResponseEntity.status(400).body("you do not have enough balance to repay loan");
                }

                u.setLaonAmount( u.getLaonAmount()- actions.getAmount());
                u.setBalance(actions.getAmount()-u.getBalance());
                bankbalance = bankbalance+actions.getAmount();
                return ResponseEntity.status(200).body("take loan confirmed");

            }
        }
        return ResponseEntity.status(400).body("invalid id ");
    }



    @PostMapping(path= "/user")
public ResponseEntity <String>  adduser(@RequestBody User  user ){
        if(!check(user)){
        return ResponseEntity.status(400).body("not");
        }

        if(user.getPassword().length() <=5){
            return ResponseEntity.status(400).body("Change your password");
        }

        for (int i = 0; i < username.size(); i++) {
            if(username.get(i).getEmail().equals(user.getEmail())){
                return ResponseEntity.status(400).body("change your email");
            }
        }
        username.add(user);
        return ResponseEntity.status(200).body("user Created");
    }




    @PutMapping(path="/username/{id}")
public ResponseEntity <String> updateUser(@RequestBody User user,@PathVariable("id")String id){

        boolean isFound = false;
        for (int i = 0; i < username.size(); i++) {
            if(username.get(i).getId().equals(id)){
                username.set(i,user);
                isFound = true;
                break;
            }
        }
        if(!isFound){
            return ResponseEntity.status(400).body(" ... ");
        }
            return ResponseEntity.status(200).body("user Created");
    }


    
    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity <String> deleteUser(@PathVariable ("id") String id ){
      boolean isFound = false;
        for (int i = 0; i < username.size(); i++) {
            if(username.get(i).getId().equals(id)){
                if(username.get(i).getLaonAmount()>0){
                    return ResponseEntity.status(400).body("repay lean before removing user");
                }
                username.remove(i);
                break;
            }
            if(!isFound){
                return ResponseEntity.status(400).body("send valid id");
            }
        }
        return ResponseEntity.status(200).body("delete");

    }



public boolean check(User user){
        if(user.getId()==null || user.getName()==null || user.getEmail()==null){
            return false;
        }
        return true;
}
}