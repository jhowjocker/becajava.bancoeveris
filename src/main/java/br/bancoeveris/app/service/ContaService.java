package br.bancoeveris.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.bancoeveris.app.model.BaseResponse;
import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.repository.ContaRepository;
import br.bancoeveris.app.spec.ContaList;
import br.bancoeveris.app.spec.ContaSpec;

@Service
public class ContaService {

	@Autowired
	private ContaRepository repository;

	public BaseResponse inserir(ContaSpec contaSpec) {

		Conta conta = new Conta();
		BaseResponse base = new BaseResponse();
		base.StatusCode = 400;

		if (contaSpec.getNome() == "") {
			base.Message = "Nome não preenchido!";
			return base;

		}

		if (contaSpec.getCpf() == "") {
			base.Message = "CPF não preenchido!";
			return base;
		}

		if (contaSpec.getHash() == "") {
			base.Message = "Hash não preenchido!";
			return base;
		}

		conta.setSaldo(0.0);

		conta.setNome(contaSpec.getNome());
		conta.setCpf(contaSpec.getCpf());
		conta.setHash(contaSpec.getHash());

		repository.save(conta);
		base.StatusCode = 201;
		base.Message = "Dados inserido com sucesso!";

		return base;

	}

	public Conta obter(Long Id) {
		Optional<Conta> conta = repository.findById(Id);

		Conta response = new Conta();

		if (conta.isEmpty()) {
			response.Message = "Conta não obtida!";
			response.StatusCode = 404;
			return response;

		}

		response.setHash(conta.get().getHash());
		response.setSaldo(conta.get().getSaldo());
		response.Message = "Conta obtida com sucesso!";
		response.StatusCode = 200;
		return response;
	}

	public ContaList listar() {

		List<Conta> lista = repository.findAll();

		ContaList response = new ContaList();
		response.setContas(lista);
		response.StatusCode = 200;
		response.Message = "Conta obtida com sucesso!";

		return response;
	}

	public BaseResponse atualizar(Long id, ContaSpec contaSpec) {
		Conta conta = new Conta();
		BaseResponse base = new BaseResponse();
		base.StatusCode = 400;

		if (contaSpec.getNome() == "") {
			base.Message = "Nome não preenchida!";
			return base;
		}

		if (contaSpec.getCpf() == "") {
			base.Message = "CPF não preenchido!";
			return base;
		}

		if (contaSpec.getHash() == "") {
			base.Message = "Hash não preenchido!";

		}

		conta.setId(id);
		conta.setNome(contaSpec.getNome());
		conta.setCpf(contaSpec.getCpf());
		conta.setHash(contaSpec.getHash());

		repository.save(conta);
		base.StatusCode = 200;
		base.Message = "Dados inseridos com sucesso!";
		return base;

	}

	public BaseResponse deletar(Long id) {
		BaseResponse response = new BaseResponse();

		if (id == null || id == 0) {
			response.StatusCode = 400;
			return response;
		}

		repository.deleteById(id);
		response.StatusCode = 200;
		return response;

	}

}
