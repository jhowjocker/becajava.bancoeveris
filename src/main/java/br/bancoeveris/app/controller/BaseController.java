package br.bancoeveris.app.controller;

import br.bancoeveris.app.model.BaseResponse;

public class BaseController {
		
		public BaseResponse errorBase = new BaseResponse();
		public BaseController () {
			errorBase.StatusCode = 500;
			errorBase.Message = "ERRO inesperado. Contate o ADM";
		
	}
}