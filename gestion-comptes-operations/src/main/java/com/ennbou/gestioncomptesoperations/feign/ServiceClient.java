package com.ennbou.gestioncomptesoperations.feign;


import com.ennbou.gestioncomptesoperations.entities.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="CLIENT-SERVICE")
public interface ServiceClient {
  @RequestMapping(method = RequestMethod.GET, value="/clients/{id}", produces = "application/json")
  Client getClientById(@PathVariable("id") Long id);
}
