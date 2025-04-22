package calendario.agendamentos.util;

public enum Aprovado {
    APROVADO(1), NAO_APROVADO(2);

    private int aprovado;

    // Construtor do enum
    Aprovado(int aprovado) {
        this.aprovado = aprovado;
    }

    public int getAprovado() {
        return aprovado;
    }
    
    public void setAprovado(int aprovado) {
		this.aprovado = aprovado;
	}

	// Método que retorna o enum correspondente ao valor
    public static Aprovado fromValue(int value) {
        for (Aprovado p : Aprovado.values()) {
            if (p.getAprovado() == value) {
                return p;
            }
        }
        throw new IllegalArgumentException("Valor inválido para Aprovado: " + value);
    }
}