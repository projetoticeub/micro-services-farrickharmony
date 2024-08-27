package br.com.ferrickharm.agenda.infra.exceptions;

public class ValidacaoException extends RuntimeException {

    public ValidacaoException(String mensagem) {
        super(mensagem);
    }
}
