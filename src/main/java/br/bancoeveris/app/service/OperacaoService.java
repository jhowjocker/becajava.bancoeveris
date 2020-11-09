package br.bancoeveris.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.bancoeveris.app.model.BaseResponse;
import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.model.Operacao;
import br.bancoeveris.app.repository.ContaRepository;
import br.bancoeveris.app.repository.OperacaoRepository;
import br.bancoeveris.app.spec.OperacaoSpec;
import br.bancoeveris.app.spec.TransferenciaSpec;

@Service
public class OperacaoService {

	@Autowired
	private OperacaoRepository repository;

	@Autowired
	private ContaRepository repositoryConta;

	public BaseResponse depositar(OperacaoSpec operacaoSpec) {
		Optional<Conta> conta = repositoryConta.findByHash(operacaoSpec.getHash());

		Operacao op = new Operacao();
		BaseResponse base = new BaseResponse();
		base.StatusCode = 400;

		if (!conta.isPresent()) {
			base.Message = "Conta não encontrada";
			return base;
		}

		if (operacaoSpec.getTipo() == "") {
			base.Message = "Insira o tipo D (deposito)";
			return base;
		}

		if (operacaoSpec.getValor() <= 0) {
			base.Message = "O Valor do cliente não foi preenchido.";
			return base;
		}

		op.setTipo(operacaoSpec.getTipo());
		op.setValor(operacaoSpec.getValor());
		op.setContaOrigem(conta.get());

		conta.get().setSaldo(conta.get().getSaldo() + operacaoSpec.getValor());

		repositoryConta.save(conta.get());
		repository.save(op);
		base.StatusCode = 200;
		base.Message = "Deposito realizado com sucesso.";
		return base;
	}

	public BaseResponse sacar(OperacaoSpec operacaoSpecSacar) {
		Optional<Conta> conta = repositoryConta.findByHash(operacaoSpecSacar.getHash());

		Operacao op = new Operacao();
		BaseResponse base = new BaseResponse();
		if (!conta.isPresent()) {
			base.Message = "Conta não encontrada";
			return base;
		}

		op.setValor(operacaoSpecSacar.getValor());
		op.setTipo(operacaoSpecSacar.getTipo());
		op.setContaOrigem(conta.get());

		conta.get().setSaldo(conta.get().getSaldo() - operacaoSpecSacar.getValor());

		repositoryConta.save(conta.get());
		repository.save(op);

		base.StatusCode = 200;
		base.Message = "Saque realizado com sucesso.";
		return base;
	}

	public BaseResponse transferir(TransferenciaSpec operacaoSpec) {
		Optional<Conta> conta1 = repositoryConta.findByHash(operacaoSpec.getHashOrigem());
		Optional<Conta> conta2 = repositoryConta.findByHash(operacaoSpec.getHashDestino());

		BaseResponse base = new BaseResponse();
		Operacao operacao = new Operacao();

		if (!conta1.isPresent()) {
			base.Message = "Conta não confere";
			return base;
		}

		if (!conta2.isPresent()) {
			base.Message = "Conta não confere";
			return base;
		}

		conta1.get().setSaldo(conta1.get().getSaldo() - operacaoSpec.getValor());
		conta2.get().setSaldo(conta2.get().getSaldo() + operacaoSpec.getValor());

		operacao.setContaOrigem(conta1.get());
		operacao.setContaDestino(conta2.get());
		operacao.setValor(operacaoSpec.getValor());

		repositoryConta.save(conta1.get());
		repositoryConta.save(conta2.get());

		repository.save(operacao);

		base.StatusCode = 200;
		base.Message = "Transferencia realizada com sucesso.";
		return base;
	}

}
