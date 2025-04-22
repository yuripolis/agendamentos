package calendario.agendamentos.util;

public enum StatusLogin {
    SUCESSO(true),
    ERRO(false);

    private boolean status;

    // Construtor do enum
    StatusLogin(boolean status) {
        this.status = status;
    }

}