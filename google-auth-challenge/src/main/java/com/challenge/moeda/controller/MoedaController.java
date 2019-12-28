package com.challenge.moeda.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.challenge.google.TOTPConfig;
import com.challenge.moeda.beans.Moeda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * API RESTful que retorna informações sobre moedas
 * @author filipe.garbim
 *
 */
@Controller
public class MoedaController {	
	
	private static final Logger LOGGER=LoggerFactory.getLogger(MoedaController.class);
	
	private Map<Integer, Moeda> moedas;
	
	public MoedaController() {
		moedas = new HashMap<Integer, Moeda>();
		
		Moeda m1 = new Moeda("USD", "Dólar comercial");
		Moeda m2 = new Moeda("EUR", "Euro");
		Moeda m3 = new Moeda("BRL", "Real brasileiro");
		
		moedas.put(1, m1);
		moedas.put(2, m2);
		moedas.put(3, m3);
	}

	@RequestMapping(method = RequestMethod.GET, value="/moedas")
	
	@ResponseBody
	  public ResponseEntity<List<Moeda>> getMoedas(@RequestHeader("code") String code,
			  									   @RequestHeader("secretKey") String secretKey) {
		LOGGER.info(code);
		if (!TOTPConfig.validateTOTPCode(code, secretKey)) {
			return new ResponseEntity<List<Moeda>>(HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<List<Moeda>>(new ArrayList<Moeda>(moedas.values()), HttpStatus.OK);
		}
	  }
}