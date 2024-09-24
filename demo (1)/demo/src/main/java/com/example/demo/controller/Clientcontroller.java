package com.example.demo.controller;
import com.example.demo.model.Client;
import java.util.Date;

import com.example.demo.model.ClientDto;
import com.example.demo.repository.ClientRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/clients")
public class Clientcontroller {
    @Autowired
    private ClientRepository repo;

    @GetMapping
    public String getClients(Model model){
        List<Client> clients = repo.getClients();
        model.addAttribute("clients",clients);
        return "clients/index";

    }
    
    @GetMapping("/create")
    public String showCreatePage(Model model){
        ClientDto clientDto = new ClientDto();
        model.addAttribute("clientDto",clientDto);
        return "clients/create";
    }
    
    @PostMapping("/create")
    public String createClient(
    		@Valid @ModelAttribute ClientDto clientDto,
    		BindingResult result
    		
    		){
    	
    	if(repo.getClient(clientDto.getEmail())!=null) {
    		result.addError(new FieldError("clientDto","email",clientDto.getEmail()
    				,false,null,null,"Email address is already used"));
    	}
    	if(result.hasErrors()) {
    		return "clients/create";
    	}
    	Client client = new Client();
         client.setFirstName(clientDto.getFirstName());
         client.setLastName(clientDto.getLastName());
         client.setEmail(clientDto.getEmail());
         client.setPhone(clientDto.getPhone());
         client.setAddress(clientDto.getAddress());
         client.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

         repo.createClient(client);

        return "redirect:/clients";
    }
    
    @GetMapping("/edit")
    public String showEditPage(
    		Model model,
    		@RequestParam int id
    		) {
    	Client client = repo.getClient(id);
		if (client == null) {
    		return "redirect:/clients";
    	}
		model.addAttribute("client",client);
		
		ClientDto clientDto = new ClientDto();
		clientDto.setFirstName(client.getFirstName());
		clientDto.setLastName(client.getLastName());
		clientDto.setEmail(client.getEmail());
		clientDto.setPhone(client.getPhone());
		clientDto.setAddress(client.getAddress());
		
		model.addAttribute("clientDto",clientDto);
    	
    	return "clients/edit";
    }
    
    
    @PostMapping("/edit")
    public String updateClient(
    		Model model,
    		@RequestParam int id,
    		@Valid @ModelAttribute ClientDto clientDto,
    		BindingResult result
    		) {
    	Client client = repo.getClient(id);
    	if(client == null) {
    		return "redirect:/clients";
    	}
    	
    	model.addAttribute("client",client);
    	if(result.hasErrors()) {
    		return "clients/edit";
    	}
    	
    	client.setFirstName(clientDto.getFirstName());
    	client.setLastName(clientDto.getLastName());
    	client.setEmail(clientDto.getEmail());
    	client.setPhone(clientDto.getPhone());
    	client.setAddress(clientDto.getAddress());
    	
    	repo.updateClient(client);
    	return "redirect:/clients";
    }
    
    
    @GetMapping("/delete")
    public String deleteClient(
    		@RequestParam int id) {
    	repo.deleteClient(id);
    	
    	return "redirect:/clients";
    }
    
    
    
    


}
