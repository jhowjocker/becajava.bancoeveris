package br.bancoeveris.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.bancoeveris.app.model.BaseResponse;
import br.bancoeveris.app.service.OperacaoService;
import br.bancoeveris.app.spec.OperacaoSpec;

@RestController
@RequestMapping("/operacao")
public class OperacaoController{
			@Autowired
			private OperacaoService service;
			
			@PostMapping(path = "/depositos")
			public ResponseEntity depositar(@RequestBody OperacaoSpec operacaoSpec) {
				try {
						BaseResponse response = service.depositar(operacaoSpec);
						return ResponseEntity.status(response.StatusCode).body("error");
				}catch (Exception e) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
				}
				
			}
			
			@PostMapping(path = "/saques")
			public ResponseEntity sacar(@RequestBody OperacaoSpec operacaoSpec) {
				try {
						BaseResponse response = service.depositar(operacaoSpec);
						return ResponseEntity.status(response.StatusCode).body("error");
				}catch (Exception e) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
				}
				
			}
			
			@PostMapping(path = "/transferencias")
			public ResponseEntity transferir(@RequestBody OperacaoSpec operacaoSpec) {
				try {
						BaseResponse response = service.depositar(operacaoSpec);
						return ResponseEntity.status(response.StatusCode).body("error");
				}catch (Exception e) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
				}
				
			}
			
	
}












