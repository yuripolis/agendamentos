package calendario.agendamentos.event;

public class Functions {
    public static String padLeft(String input, int length, char padChar) {
        if (input == null) {
            return null;
        }
        if (input.length() >= length) {
            return input;
        }
        StringBuilder sb = new StringBuilder(length);
        for (int i = input.length(); i < length; i++) {
            sb.append(padChar);
        }
        sb.append(input);
        return sb.toString();
    }

    public static String padLeft(int input, int length, char padChar) {
        return padLeft(String.valueOf(input), length, padChar);
    }
}
