import java.util.List;

public class ReuniaoPresencial extends Evento {
    private List<String> itensLocacao;

    public ReuniaoPresencial(String descricao, String data, List<String> itensLocacao) {
        super(descricao, data);
        this.itensLocacao = itensLocacao;
    }

    @Override
    public double calcularCusto() {
        // Implementação do cálculo de custo para Reunião Presencial
        double custoTotal = 0.0;

        for (String item : itensLocacao) {
            if (item.equals("Sala de Reuniao")) {
                custoTotal += 100.0;
            } else if (item.equals("Cafe e Agua")) {
                custoTotal += 20.0;
            } else if (item.equals("Projetor de Video")) {
                custoTotal += 90.0;
            }
        }
        return custoTotal;
    }
}