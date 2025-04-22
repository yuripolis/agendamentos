package calendario.agendamentos.util;

public enum Parentesco {
    PAI(1), FILHO(2);

    private int parentesco;

    // Construtor do enum
    Parentesco(int parentesco) {
        this.parentesco = parentesco;
    }

    public int getParentesco() {
        return parentesco;
    }
    
    public void setParentesco(int parentesco) {
		this.parentesco = parentesco;
	}

	// Método que retorna o enum correspondente ao valor
    public static Parentesco fromValue(int value) {
        for (Parentesco p : Parentesco.values()) {
            if (p.getParentesco() == value) {
                return p;
            }
        }
        throw new IllegalArgumentException("Valor inválido para Parentesco: " + value);
    }
}